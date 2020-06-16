package com.resources.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class Rule { // SNL的语法规则

    String First;
    List<String> Second = new ArrayList<String>();
}

public class analyzeGrammar {

    public int[][] analysis = new int[68][37]; // LL(1)分析表,67个非终极符,36个终极符
    public List<Rule> rule = new ArrayList<Rule>(); // 存放SNL的104条规则
    public List<String> terminator = new ArrayList<String>(); // 终极符
    public List<String> nonterminator = new ArrayList<String>(); // 非终极符

    public Stack<String> stack = new Stack<String>();//符号栈
    public Stack<Integer> stack2 = new Stack<Integer>();
    public StringBuffer grammarDisplay = new StringBuffer();//输出语法分析后的信息
    public StringBuffer grammarDisplayTree = new StringBuffer();//输出语法树

    public String iStack = null; // 弹出栈顶符号
    public String iStack2 = null;//查看栈顶符号
    public int pnum = 0; // 选取第pnum条规则进行规约
    public int treeDepth = 0;//保存当前语法树的层数
    public String match = null;//保存语法分析中匹配后的终极符
    public boolean StackEmpty = true;//符号栈空标识置为真
    public boolean nextLoop = false;//读取下一个Token
    public boolean stop = false;
    public boolean isTokenEmpty = false;
    public int iLine; // token中的行号
    public String iToken = null; // Token序列首位
    public String iLex = null;//token中的词法信息
    public String iSem = null;//token中的语义信息
    public boolean isSuccess=false;//语法分析是否成功

    public String analyzeGrammar() {
        CreateLL1Table();//创建LL(1)分析表
        stack.push("Program");//文法开始符Program压入符号栈
        stack2.push(1);
        int tokenLength = createToken.token.size();
        int curPosition = 0;
        if (tokenLength == 0) {
            isTokenEmpty = true;
            grammarDisplay.append("Token list is empty!\n");
            return grammarDisplay.toString();
        }
        while (!isTokenEmpty && !stop) {
            if (curPosition <= tokenLength - 1) {
                if (!nextLoop) {
                    analyze(curPosition);
                } else {
                    curPosition++;
                    analyze(curPosition);
                }
            }
            if (iToken.equals(".")) {
                isTokenEmpty = true;
                stop = true;
            }
        }
        if (stack.isEmpty() && isTokenEmpty) {
            //grammarDisplay.append("Grammar analysis successful!\n");
            isSuccess=true;
        }
        if (stack.isEmpty() && !isTokenEmpty) {
            grammarDisplay.append("Error in line " + iLine + ": the stack is empty but the token sequence isn't!\n");
        }
        return grammarDisplay.toString();
    }

    public String printGrammarTree() {
        return grammarDisplayTree.toString();
    }

    public int analyze(int i) {
        iLine = createToken.line.get(i);
        iLex = createToken.token.get(i).LEX;
        iSem = createToken.token.get(i).SEM;
        // 还原token中的单词信息至iToken
        if (iSem.equals("letter")) {
            iToken = iLex;
            //System.out.println(iToken);
        }
        if (iSem.equals("reserved word")) {
            iToken = iLex;
            //System.out.println(iToken);
        }
        if (iLex.equals("ID")) {
            iToken = iLex;
            //System.out.println(iToken);
        }
        if (iLex.equals("INTC")) {
            iToken = iLex;
            //System.out.println(iToken);
        }
        if (iSem.equals("separator")) {
            iToken = createToken.token.get(i).getKeyByValue(createToken.separatorLEX, iLex);
            //System.out.println(iToken);
        }
        if (stack.isEmpty() && !isTokenEmpty) { // 如果token序列还未遍历完而栈已空，则出错
            stop = true;
            return 0;
        }
        iStack2 = stack.peek();//查询栈顶元素
        if (nonterminator.contains(iStack2)) { // 栈顶元素为非终极符，则要根据LL(1)分析表进行规约
            //System.out.print(nonterminator.indexOf(iStack2) + " " + terminator.indexOf(iToken));
            if ((pnum = analysis[nonterminator.indexOf(iStack2)][terminator.indexOf(iToken)]) == 0) { // 出错！因没有可用的规则进行规约
                grammarDisplay.append("Error in line " + iLine + ": No Rules Available!\n");
                stop = true;
                return 0;
            } else {
                pnum = analysis[nonterminator.indexOf(iStack2)][terminator.indexOf(iToken)];
                int tempDepth = stack2.peek() + 1;
                iStack = stack.pop(); // 弹出栈顶元素
                treeDepth = stack2.pop();
                match = treeDepth + ": " + iStack + "\n";
                //System.out.println(treeDepth + ": " + iStack + "\n");
                grammarDisplay.append(match);
                for (int k = 0; k < treeDepth - 1; k++) {
                    grammarDisplayTree.append("  ");
                }
                grammarDisplayTree.append(iStack + "\n");
                for (int j = rule.get(pnum).Second.size() - 1; j >= 0; j--) { // 将产生式右部逆序压栈
                    if (rule.get(pnum).Second.get(j).equals("null")) {
                        break;
                    }
                    stack.push(rule.get(pnum).Second.get(j));
                    stack2.push(tempDepth);
                }
                nextLoop = false;
                return 0;
            }
        } else { // 此时栈顶元素为终极符，则要跟token序列首位进行匹配
            if (iStack2.equals(iToken)) { // 匹配成功
                iStack = stack.pop(); // 弹出栈顶元素
                treeDepth = stack2.pop();
                match = treeDepth + ": " + iToken + "\n";
                for (int k = 0; k < treeDepth - 1; k++) {
                    grammarDisplayTree.append("  ");
                }
                grammarDisplayTree.append(iStack + "\n");
                grammarDisplay.append(match);
                nextLoop = true;
                if (iToken.equals(".")) {
                    nextLoop = false;
                    stop = true;
                    isTokenEmpty = true;
                }
                return 0;
            } else { // 不匹配则出错
                grammarDisplay.append("Error in line " + iLine + ": Unexpected words!\n");
                stop = true;
                return 0;
            }
        }
    }

    public void CreateLL1Table() {
        //终极符
        terminator.add("null");//0
        terminator.add("program");//1
        terminator.add("type");//2
        terminator.add("integer");//3
        terminator.add("char");//4
        terminator.add("array");//5
        terminator.add("INTC");//6
        terminator.add("record");//7
        terminator.add("end");//8
        terminator.add("var");//9
        terminator.add("procedure");//10
        terminator.add("begin");//11
        terminator.add("if");//12
        terminator.add("then");//13
        terminator.add("else");//14
        terminator.add("fi");//15
        terminator.add("while");//16
        terminator.add("do");//17
        terminator.add("endwh");//18
        terminator.add("read");//19
        terminator.add("write");//20
        terminator.add("return");//21
        terminator.add("ID");//22
        terminator.add(".");//23
        terminator.add(";");//24
        terminator.add(",");//25
        terminator.add("(");//26
        terminator.add(")");//27
        terminator.add("[");//28
        terminator.add("]");//29
        terminator.add("<");//30
        terminator.add("=");//31
        terminator.add("+");//32
        terminator.add("-");//33
        terminator.add("*");//34
        terminator.add("/");//35
        terminator.add(":=");//36

        //非终极符
        nonterminator.add("null");//0
        //总程序
        nonterminator.add("Program");//1
        //程序头
        nonterminator.add("ProgramHead");//2
        nonterminator.add("ProgramName");//3
        //程序声明
        nonterminator.add("DeclarePart");//4
        //类型声明
        nonterminator.add("TypeDecpart");//5
        nonterminator.add("TypeDec");//6
        nonterminator.add("TypeDecList");//7
        nonterminator.add("TypeDecMore");//8
        nonterminator.add("TypeId");//9
        //类型
        nonterminator.add("TypeDef");//10
        nonterminator.add("BaseType");//11
        nonterminator.add("StructureType");//12
        nonterminator.add("ArrayType");//13
        nonterminator.add("Low");//14
        nonterminator.add("Top");//15
        nonterminator.add("RecType");//16
        nonterminator.add("FieldDecList");//17
        nonterminator.add("FieldDecMore");//18
        nonterminator.add("IdList");//19
        nonterminator.add("IdMore");//20
        //变量声明
        nonterminator.add("VarDecpart");//21
        nonterminator.add("VarDec");//22
        nonterminator.add("VarDecList");//23
        nonterminator.add("VarDecMore");//24
        nonterminator.add("VarIdList");//25
        nonterminator.add("VarIdMore");//26
        //过程声明
        nonterminator.add("ProcDecpart");//27
        nonterminator.add("ProcDec");//28
        nonterminator.add("ProcDecMore");//29
        nonterminator.add("ProcName");//30
        //参数声明
        nonterminator.add("ParamList");//31
        nonterminator.add("ParamDecList");//32
        nonterminator.add("ParamMore");//33
        nonterminator.add("Param");//34
        nonterminator.add("FormList");//35
        nonterminator.add("FidMore");//36
        //过程中的声明部分
        nonterminator.add("ProcDecPart");//37
        //过程体
        nonterminator.add("ProcBody");//38
        //主程序体
        nonterminator.add("ProgramBody");//39
        //语句序列
        nonterminator.add("StmList");//40
        nonterminator.add("StmMore");//41
        //语句
        nonterminator.add("Stm");//42
        nonterminator.add("AssCall");//43
        //赋值语句
        nonterminator.add("AssignmentRest");//44
        //条件语句
        nonterminator.add("ConditionalStm");//45
        //循环语句
        nonterminator.add("LoopStm");//46
        //输入语句
        nonterminator.add("InputStm");//47
        nonterminator.add("Invar");//48
        //输出语句
        nonterminator.add("OutputStm");//49
        //返回语句
        nonterminator.add("ReturnStm");//50
        //过程调用语句
        nonterminator.add("CallStmRest");//51
        nonterminator.add("ActParamList");//52
        nonterminator.add("ActParamMore");//53
        //条件表达式
        nonterminator.add("RelExp");//54
        nonterminator.add("OtherRelE");//55
        //算术表达式
        nonterminator.add("Exp");//56
        nonterminator.add("OtherTerm");//57
        //项
        nonterminator.add("Term");//58
        nonterminator.add("OtherFactor");//59
        //因子
        nonterminator.add("Factor");//60
        nonterminator.add("Variable");//61
        nonterminator.add("VariMore");//62
        nonterminator.add("FieldVar");//63
        nonterminator.add("FieldVarMore");//64
        nonterminator.add("CmpOp");//65
        nonterminator.add("AddOp");//66
        nonterminator.add("MulOp");//67

        //LL(1)分析表
        for (int i = 0; i < 68; i++) {
            for (int j = 0; j < 37; j++) {
                analysis[i][j] = 0;
            }
        }
        analysis[1][1] = 1;
        analysis[2][1] = 2;
        analysis[3][22] = 3;

        analysis[4][2] = 4;
        analysis[4][9] = 4;
        analysis[4][10] = 4;
        analysis[4][11] = 4;

        analysis[5][2] = 6;
        analysis[5][9] = 5;
        analysis[5][10] = 5;
        analysis[5][11] = 5;

        analysis[6][2] = 7;
        analysis[7][22] = 8;

        analysis[8][9] = 9;
        analysis[8][10] = 9;
        analysis[8][11] = 9;
        analysis[8][22] = 10;

        analysis[9][22] = 11;

        analysis[10][3] = 12;
        analysis[10][4] = 12;
        analysis[10][5] = 13;
        analysis[10][7] = 13;
        analysis[10][22] = 14;

        analysis[11][3] = 15;////////
        analysis[11][4] = 16;

        analysis[12][5] = 17;
        analysis[12][7] = 18;

        analysis[13][5] = 19;
        analysis[14][6] = 20;
        analysis[15][6] = 21;
        analysis[16][7] = 22;

        analysis[17][3] = 23;
        analysis[17][4] = 23;
        analysis[17][5] = 24;

        analysis[18][3] = 26;
        analysis[18][4] = 26;
        analysis[18][5] = 26;
        analysis[18][8] = 25;

        analysis[19][22] = 27;

        analysis[20][24] = 28;
        analysis[20][25] = 29;

        analysis[21][9] = 31;
        analysis[21][10] = 30;
        analysis[21][11] = 30;

        analysis[22][9] = 32;

        analysis[23][3] = 33;
        analysis[23][4] = 33;
        analysis[23][5] = 33;
        analysis[23][7] = 33;
        analysis[23][22] = 33;

        analysis[24][3] = 35;
        analysis[24][4] = 35;
        analysis[24][5] = 35;
        analysis[24][7] = 35;
        analysis[24][10] = 34;
        analysis[24][11] = 34;
        analysis[24][22] = 35;

        analysis[25][22] = 36;

        analysis[26][24] = 37;
        analysis[26][25] = 38;

        analysis[27][10] = 40;
        analysis[27][11] = 39;

        analysis[28][10] = 41;

        analysis[29][10] = 43;
        analysis[29][11] = 42;

        analysis[30][22] = 44;

        analysis[31][3] = 46;
        analysis[31][4] = 46;
        analysis[31][5] = 46;
        analysis[31][7] = 46;
        analysis[31][9] = 46;
        analysis[31][22] = 46;
        analysis[31][27] = 45;

        analysis[32][3] = 47;
        analysis[32][4] = 47;
        analysis[32][5] = 47;
        analysis[32][7] = 47;
        analysis[32][9] = 47;
        analysis[32][22] = 47;

        analysis[33][24] = 49;
        analysis[33][27] = 48;

        analysis[34][3] = 50;
        analysis[34][4] = 50;
        analysis[34][5] = 50;
        analysis[34][7] = 50;
        analysis[34][9] = 51;
        analysis[34][22] = 50;

        analysis[35][22] = 52;

        analysis[36][24] = 53;
        analysis[36][25] = 54;
        analysis[36][27] = 53;

        analysis[37][2] = 55;
        analysis[37][9] = 55;
        analysis[37][10] = 55;
        analysis[37][11] = 55;

        analysis[38][11] = 56;
        analysis[39][11] = 57;

        analysis[40][12] = 58;
        analysis[40][16] = 58;
        analysis[40][19] = 58;
        analysis[40][20] = 58;
        analysis[40][21] = 58;
        analysis[40][22] = 58;

        analysis[41][8] = 59;
        analysis[41][14] = 59;
        analysis[41][15] = 59;
        analysis[41][18] = 59;
        analysis[41][24] = 60;

        analysis[42][12] = 61;
        analysis[42][16] = 62;
        analysis[42][19] = 63;
        analysis[42][20] = 64;
        analysis[42][21] = 65;
        analysis[42][22] = 66;

        analysis[43][23] = 67;
        analysis[43][26] = 68;
        analysis[43][28] = 67;
        analysis[43][36] = 67;

        analysis[44][23] = 69;
        analysis[44][28] = 69;
        analysis[44][36] = 69;

        analysis[45][12] = 70;
        analysis[46][16] = 71;
        analysis[47][19] = 72;
        analysis[48][22] = 73;
        analysis[49][20] = 74;
        analysis[50][21] = 75;
        analysis[51][26] = 76;

        analysis[52][6] = 78;
        analysis[52][22] = 78;
        analysis[52][26] = 78;
        analysis[52][27] = 77;

        analysis[53][25] = 80;
        analysis[53][27] = 79;

        analysis[54][6] = 81;
        analysis[54][22] = 81;
        analysis[54][26] = 81;

        analysis[55][30] = 82;
        analysis[55][31] = 82;

        analysis[56][6] = 83;
        analysis[56][22] = 83;
        analysis[56][26] = 83;

        analysis[57][8] = 84;
        analysis[57][13] = 84;
        analysis[57][14] = 84;
        analysis[57][15] = 84;
        analysis[57][17] = 84;
        analysis[57][18] = 84;
        analysis[57][24] = 84;
        analysis[57][25] = 84;
        analysis[57][27] = 84;
        analysis[57][29] = 84;
        analysis[57][30] = 84;
        analysis[57][31] = 84;
        analysis[57][32] = 85;
        analysis[57][33] = 85;

        analysis[58][6] = 86;
        analysis[58][22] = 86;
        analysis[58][26] = 86;

        analysis[59][8] = 87;
        analysis[59][13] = 87;
        analysis[59][14] = 87;
        analysis[59][15] = 87;
        analysis[59][17] = 87;
        analysis[59][18] = 87;
        analysis[59][24] = 87;
        analysis[59][25] = 87;
        analysis[59][27] = 87;
        analysis[59][29] = 87;
        analysis[59][30] = 87;
        analysis[59][31] = 87;
        analysis[59][32] = 87;
        analysis[59][33] = 87;
        analysis[59][34] = 88;
        analysis[59][35] = 88;

        analysis[60][6] = 90;
        analysis[60][22] = 91;
        analysis[60][26] = 89;

        analysis[61][22] = 92;

        analysis[62][8] = 93;
        analysis[62][13] = 93;
        analysis[62][14] = 93;
        analysis[62][15] = 93;
        analysis[62][17] = 93;
        analysis[62][18] = 93;
        analysis[62][23] = 95;
        analysis[62][24] = 93;
        analysis[62][25] = 93;
        analysis[62][27] = 93;
        analysis[62][28] = 94;
        analysis[62][29] = 93;
        analysis[62][30] = 93;
        analysis[62][31] = 93;
        analysis[62][32] = 93;
        analysis[62][33] = 93;
        analysis[62][34] = 93;
        analysis[62][35] = 93;
        analysis[62][36] = 93;

        analysis[63][22] = 96;

        analysis[64][8] = 97;
        analysis[64][13] = 97;
        analysis[64][14] = 97;
        analysis[64][15] = 97;
        analysis[64][17] = 97;
        analysis[64][18] = 97;
        analysis[64][24] = 97;
        analysis[64][25] = 97;
        analysis[64][27] = 97;
        analysis[64][28] = 98;
        analysis[64][29] = 97;
        analysis[64][30] = 97;
        analysis[64][31] = 97;
        analysis[64][32] = 97;
        analysis[64][33] = 97;
        analysis[64][34] = 97;
        analysis[64][35] = 97;
        analysis[64][36] = 97;

        analysis[65][30] = 99;
        analysis[65][31] = 100;

        analysis[66][32] = 101;
        analysis[66][33] = 102;

        analysis[67][34] = 103;
        analysis[67][35] = 104;

        Rule r = new Rule(); // 填充下标为零的规则
        r.First = "SNL";
        r.Second.add(".");
        rule.add(r);

        r = new Rule();//1
        r.First = "Program";
        r.Second.add("ProgramHead");
        r.Second.add("DeclarePart");
        r.Second.add("ProgramBody");
        r.Second.add(".");
        rule.add(r);

        r = new Rule();//2
        r.First = "ProgramHead";
        r.Second.add("program");
        r.Second.add("ProgramName");
        rule.add(r);

        r = new Rule();//3
        r.First = "ProgramName";
        r.Second.add("ID");
        rule.add(r);

        r = new Rule();//4
        r.First = "DeclarePart";
        r.Second.add("TypeDecpart");
        r.Second.add("VarDecpart");
        r.Second.add("ProcDecpart");
        rule.add(r);

        r = new Rule();//5
        r.First = "TypeDecpart";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();//6
        r.First = "TypeDecpart";
        r.Second.add("TypeDec");
        rule.add(r);

        r = new Rule();//7
        r.First = "TypeDec";
        r.Second.add("type");
        r.Second.add("TypeDecList");
        rule.add(r);

        r = new Rule();//8
        r.First = "TypeDecList";
        r.Second.add("TypeId");
        r.Second.add("=");
        r.Second.add("TypeDef");
        r.Second.add(";");
        r.Second.add("TypeDecMore");
        rule.add(r);

        r = new Rule();//9
        r.First = "TypeDecMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();//10
        r.First = "TypeDecMore";
        r.Second.add("TypeDecList");
        rule.add(r);

        r = new Rule();//11
        r.First = "TypeId";
        r.Second.add("ID");
        rule.add(r);

        r = new Rule();//12
        r.First = "TypeDef";
        r.Second.add("BaseType");
        rule.add(r);

        r = new Rule();//13
        r.First = "TypeDef";
        r.Second.add("StructureType");
        rule.add(r);

        r = new Rule();//14
        r.First = "TypeDef";
        r.Second.add("ID");
        rule.add(r);

        r = new Rule();//15
        r.First = "BaseType";
        r.Second.add("integer");
        rule.add(r);

        r = new Rule();//16
        r.First = "BaseType";
        r.Second.add("char");
        rule.add(r);

        r = new Rule();//17
        r.First = "StructureType";
        r.Second.add("ArrayType");
        rule.add(r);

        r = new Rule();//18
        r.First = "StructureType";
        r.Second.add("RecType");
        rule.add(r);

        r = new Rule();//19
        r.First = "ArrayType";
        r.Second.add("array");
        r.Second.add("[");
        r.Second.add("Low");
        r.Second.add("..");
        r.Second.add("Top");
        r.Second.add("]");
        r.Second.add("of");
        r.Second.add("BaseType");
        rule.add(r);

        r = new Rule();//20
        r.First = "Low";
        r.Second.add("INTC");
        rule.add(r);

        r = new Rule();//21
        r.First = "Top";
        r.Second.add("INTC");
        rule.add(r);

        r = new Rule();//22
        r.First = "RecType";
        r.Second.add("record");
        r.Second.add("FieldDecList");
        r.Second.add("end");
        rule.add(r);

        r = new Rule();//23
        r.First = "FieldDecList";
        r.Second.add("BaseType");
        r.Second.add("IdList");
        r.Second.add(";");
        r.Second.add("FieldDecMore");
        rule.add(r);

        r = new Rule();//24
        r.First = "FieldDecList";
        r.Second.add("ArrayType");
        r.Second.add("IdList");
        r.Second.add(";");
        r.Second.add("FieldDecMore");
        rule.add(r);

        r = new Rule();//25
        r.First = "FieldDecMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();//26
        r.First = "FieldDecMore";
        r.Second.add("FieldDecList");
        rule.add(r);

        r = new Rule();//27
        r.First = "IdList";
        r.Second.add("ID");
        r.Second.add("IdMore");
        rule.add(r);

        r = new Rule();
        r.First = "IdMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "IdMore";
        r.Second.add(",");
        r.Second.add("IdList");
        rule.add(r);

        r = new Rule();
        r.First = "VarDecpart";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "VarDecpart";
        r.Second.add("VarDec");
        rule.add(r);

        r = new Rule();
        r.First = "VarDec";
        r.Second.add("var");
        r.Second.add("VarDecList");
        rule.add(r);

        r = new Rule();
        r.First = "VarDecList";
        r.Second.add("TypeDef");
        r.Second.add("VarIdList");
        r.Second.add(";");
        r.Second.add("VarDecMore");
        rule.add(r);

        r = new Rule();
        r.First = "VarDecMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "VarDecMore";
        r.Second.add("VarDecList");
        rule.add(r);

        r = new Rule();
        r.First = "VarIdList";
        r.Second.add("ID");
        r.Second.add("VarIdMore");
        rule.add(r);

        r = new Rule();
        r.First = "VarIdMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "VarIdMore";
        r.Second.add(",");
        r.Second.add("VarIdList");
        rule.add(r);

        r = new Rule();
        r.First = "ProcDecpart";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "ProcDecpart";
        r.Second.add("ProcDec");
        rule.add(r);

        r = new Rule();
        r.First = "ProcDec";
        r.Second.add("procedure");
        r.Second.add("ProcName");
        r.Second.add("(");
        r.Second.add("ParamList");
        r.Second.add(")");
        r.Second.add(";");
        r.Second.add("ProcDecPart");
        r.Second.add("ProcBody");
        r.Second.add("ProcDecMore");
        rule.add(r);

        r = new Rule();
        r.First = "ProcDecMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "ProcDecMore";
        r.Second.add("ProcDec");
        rule.add(r);

        r = new Rule();
        r.First = "ProcName";
        r.Second.add("ID");
        rule.add(r);

        r = new Rule();
        r.First = "ParamList";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "ParamList";
        r.Second.add("ParamDecList");
        rule.add(r);

        r = new Rule();
        r.First = "ParamDecList";
        r.Second.add("Param");
        r.Second.add("ParamMore");
        rule.add(r);

        r = new Rule();
        r.First = "ParamMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "ParamMore";
        r.Second.add(";");
        r.Second.add("ParamDecList");
        rule.add(r);

        r = new Rule();
        r.First = "Param";
        r.Second.add("TypeDef");
        r.Second.add("FormList");
        rule.add(r);

        r = new Rule();
        r.First = "Param";
        r.Second.add("var");
        r.Second.add("TypeDef");
        r.Second.add("FormList");
        rule.add(r);

        r = new Rule();
        r.First = "FormList";
        r.Second.add("ID");
        r.Second.add("FidMore");
        rule.add(r);

        r = new Rule();
        r.First = "FidMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "FidMore";
        r.Second.add(",");
        r.Second.add("FormList");
        rule.add(r);

        r = new Rule();
        r.First = "ProcDecPart";
        r.Second.add("DeclarePart");
        rule.add(r);

        r = new Rule();
        r.First = "ProcBody";
        r.Second.add("ProgramBody");
        rule.add(r);

        r = new Rule();
        r.First = "ProgramBody";
        r.Second.add("begin");
        r.Second.add("StmList");
        r.Second.add("end");
        rule.add(r);

        r = new Rule();
        r.First = "StmList";
        r.Second.add("Stm");
        r.Second.add("StmMore");
        rule.add(r);

        r = new Rule();
        r.First = "StmMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "StmMore";
        r.Second.add(";");
        r.Second.add("StmList");
        rule.add(r);

        r = new Rule();
        r.First = "Stm";
        r.Second.add("ConditionalStm");
        rule.add(r);

        r = new Rule();
        r.First = "Stm";
        r.Second.add("LoopStm");
        rule.add(r);

        r = new Rule();
        r.First = "Stm";
        r.Second.add("InputStm");
        rule.add(r);

        r = new Rule();
        r.First = "Stm";
        r.Second.add("OutputStm");
        rule.add(r);

        r = new Rule();
        r.First = "Stm";
        r.Second.add("ReturnStm");
        rule.add(r);

        r = new Rule();
        r.First = "Stm";
        r.Second.add("ID");
        r.Second.add("AssCall");
        rule.add(r);

        r = new Rule();
        r.First = "AssCall";
        r.Second.add("AssignmentRest");
        rule.add(r);

        r = new Rule();
        r.First = "AssCall";
        r.Second.add("CallStmRest");
        rule.add(r);

        r = new Rule();
        r.First = "AssignmentRest";
        r.Second.add("VariMore");
        r.Second.add(":=");
        r.Second.add("Exp");
        rule.add(r);

        r = new Rule();
        r.First = "ConditionalStm";
        r.Second.add("if");
        r.Second.add("RelExp");
        r.Second.add("then");
        r.Second.add("StmList");
        r.Second.add("else");
        r.Second.add("StmList");
        r.Second.add("fi");
        rule.add(r);

        r = new Rule();
        r.First = "LoopStm";
        r.Second.add("while");
        r.Second.add("RelExp");
        r.Second.add("do");
        r.Second.add("StmList");
        r.Second.add("endwh");
        rule.add(r);

        r = new Rule();
        r.First = "InputStm";
        r.Second.add("read");
        r.Second.add("(");
        r.Second.add("Invar");
        r.Second.add(")");
        rule.add(r);

        r = new Rule();
        r.First = "Invar";
        r.Second.add("ID");
        rule.add(r);

        r = new Rule();
        r.First = "OutputStm";
        r.Second.add("write");
        r.Second.add("(");
        r.Second.add("Exp");
        r.Second.add(")");
        rule.add(r);

        r = new Rule();
        r.First = "ReturnStm";
        r.Second.add("return");
        r.Second.add("(");
        r.Second.add("Exp");
        r.Second.add(")");
        rule.add(r);

        r = new Rule();
        r.First = "CallStmRest";
        r.Second.add("(");
        r.Second.add("ActParamList");
        r.Second.add(")");
        rule.add(r);

        r = new Rule();
        r.First = "ActParamList";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "ActParamList";
        r.Second.add("Exp");
        r.Second.add("ActParamMore");
        rule.add(r);

        r = new Rule();
        r.First = "ActParamMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "ActParamMore";
        r.Second.add(",");
        r.Second.add("ActParamList");
        rule.add(r);

        r = new Rule();
        r.First = "RelExp";
        r.Second.add("Exp");
        r.Second.add("OtherRelE");
        rule.add(r);

        r = new Rule();
        r.First = "OtherRelE";
        r.Second.add("CmpOp");
        r.Second.add("Exp");
        rule.add(r);

        r = new Rule();
        r.First = "Exp";
        r.Second.add("Term");
        r.Second.add("OtherTerm");
        rule.add(r);

        r = new Rule();
        r.First = "OtherTerm";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "OtherTerm";
        r.Second.add("AddOp");
        r.Second.add("Exp");
        rule.add(r);

        r = new Rule();
        r.First = "Term";
        r.Second.add("Factor");
        r.Second.add("OtherFactor");
        rule.add(r);

        r = new Rule();
        r.First = "OtherFactor";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "OtherFactor";
        r.Second.add("MulOp");
        r.Second.add("Term");
        rule.add(r);

        r = new Rule();
        r.First = "Factor";
        r.Second.add("(");
        r.Second.add("Exp");
        r.Second.add(")");
        rule.add(r);

        r = new Rule();
        r.First = "Factor";
        r.Second.add("INTC");
        rule.add(r);

        r = new Rule();
        r.First = "Factor";
        r.Second.add("Variable");
        rule.add(r);

        r = new Rule();
        r.First = "Variable";
        r.Second.add("ID");
        r.Second.add("VariMore");
        rule.add(r);

        r = new Rule();
        r.First = "VariMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "VariMore";
        r.Second.add("[");
        r.Second.add("Exp");
        r.Second.add("]");
        rule.add(r);

        r = new Rule();
        r.First = "VariMore";
        r.Second.add(".");
        r.Second.add("FieldVar");
        rule.add(r);

        r = new Rule();
        r.First = "FieldVar";
        r.Second.add("ID");
        r.Second.add("FieldVarMore");
        rule.add(r);

        r = new Rule();
        r.First = "FieldVarMore";
        r.Second.add("null");
        rule.add(r);

        r = new Rule();
        r.First = "FieldVarMore";
        r.Second.add("[");
        r.Second.add("Exp");
        r.Second.add("]");
        rule.add(r);

        r = new Rule();
        r.First = "CmpOp";
        r.Second.add("<");
        rule.add(r);

        r = new Rule();
        r.First = "CmpOp";
        r.Second.add("=");
        rule.add(r);

        r = new Rule();
        r.First = "AddOp";
        r.Second.add("+");
        rule.add(r);

        r = new Rule();
        r.First = "AddOp";
        r.Second.add("-");
        rule.add(r);

        r = new Rule();
        r.First = "MulOp";
        r.Second.add("*");
        rule.add(r);

        r = new Rule();
        r.First = "MulOp";
        r.Second.add("/");
        rule.add(r);
    }
}
