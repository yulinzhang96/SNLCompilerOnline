package com.resources.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class analyzeGrammar2 {

    public List<String> terminal = new ArrayList<String>(); // 终极符
    public List<String> nonTerminal = new ArrayList<String>(); // 非终极符
    public String[][] predict = new String[105][20]; //104条规则的predict集

    public Stack<String> stack = new Stack<String>();//符号栈
    public Stack<Integer> stack2 = new Stack<Integer>();
    public StringBuffer grammarDisplay = new StringBuffer();//输出语法分析后的信息

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

    public String analyzeGrammar2() {
        int tokenLength = createToken.token.size();
        int curPosition = 0;
        if (tokenLength == 0) {
            isTokenEmpty = true;
            grammarDisplay.append("Token list is empty!");
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
        return grammarDisplay.toString();
    }

    public void analyze(int i) {
        iLine = createToken.line.get(i);
        iLex = createToken.token.get(i).LEX;
        iSem = createToken.token.get(i).SEM;
        // 还原token中的单词信息至iToken
        if (iSem.equals("letter")) {
            iToken = iLex;
            System.out.println(iToken);
        }
        if (iSem.equals("reserved word")) {
            iToken = iLex;
            System.out.println(iToken);
        }
        if (iLex.equals("ID")) {
            iToken = iLex;
            System.out.println(iToken);
        }
        if (iLex.equals("INTC")) {
            iToken = iLex;
            System.out.println(iToken);
        }
        if (iSem.equals("separator")) {
            iToken = createToken.token.get(i).getKeyByValue(createToken.separatorLEX, iLex);
            System.out.println(iToken);
        }
    }

    public void CreateData() {
        //终极符
        terminal.add("null");//0
        terminal.add("program");//1
        terminal.add("type");//2
        terminal.add("integer");//3
        terminal.add("char");//4
        terminal.add("array");//5
        terminal.add("INTC");//6
        terminal.add("record");//7
        terminal.add("end");//8
        terminal.add("var");//9
        terminal.add("procedure");//10
        terminal.add("begin");//11
        terminal.add("if");//12
        terminal.add("then");//13
        terminal.add("else");//14
        terminal.add("fi");//15
        terminal.add("while");//16
        terminal.add("do");//17
        terminal.add("endwh");//18
        terminal.add("read");//19
        terminal.add("write");//20
        terminal.add("return");//21
        terminal.add("ID");//22
        terminal.add(".");//23
        terminal.add(";");//24
        terminal.add(",");//25
        terminal.add("(");//26
        terminal.add(")");//27
        terminal.add("[");//28
        terminal.add("]");//29
        terminal.add("<");//30
        terminal.add("=");//31
        terminal.add("+");//32
        terminal.add("-");//33
        terminal.add("*");//34
        terminal.add("/");//35
        terminal.add(":=");//36

        //非终极符
        nonTerminal.add("null");//0
        //总程序
        nonTerminal.add("Program");//1
        //程序头
        nonTerminal.add("ProgramHead");//2
        nonTerminal.add("ProgramName");//3
        //程序声明
        nonTerminal.add("DeclarePart");//4
        //类型声明
        nonTerminal.add("TypeDecpart");//5
        nonTerminal.add("TypeDec");//6
        nonTerminal.add("TypeDecList");//7
        nonTerminal.add("TypeDecMore");//8
        nonTerminal.add("TypeId");//9
        //类型
        nonTerminal.add("TypeDef");//10
        nonTerminal.add("BaseType");//11
        nonTerminal.add("StructureType");//12
        nonTerminal.add("ArrayType");//13
        nonTerminal.add("Low");//14
        nonTerminal.add("Top");//15
        nonTerminal.add("RecType");//16
        nonTerminal.add("FieldDecList");//17
        nonTerminal.add("FieldDecMore");//18
        nonTerminal.add("IdList");//19
        nonTerminal.add("IdMore");//20
        //变量声明
        nonTerminal.add("VarDecpart");//21
        nonTerminal.add("VarDec");//22
        nonTerminal.add("VarDecList");//23
        nonTerminal.add("VarDecMore");//24
        nonTerminal.add("VarIdList");//25
        nonTerminal.add("VarIdMore");//26
        //过程声明
        nonTerminal.add("ProcDecpart");//27
        nonTerminal.add("ProcDec");//28
        nonTerminal.add("ProcDecMore");//29
        nonTerminal.add("ProcName");//30
        //参数声明
        nonTerminal.add("ParamList");//31
        nonTerminal.add("ParamDecList");//32
        nonTerminal.add("ParamMore");//33
        nonTerminal.add("Param");//34
        nonTerminal.add("FormList");//35
        nonTerminal.add("FidMore");//36
        //过程中的声明部分
        nonTerminal.add("ProcDecPart");//37
        //过程体
        nonTerminal.add("ProcBody");//38
        //主程序体
        nonTerminal.add("ProgramBody");//39
        //语句序列
        nonTerminal.add("StmList");//40
        nonTerminal.add("StmMore");//41
        //语句
        nonTerminal.add("Stm");//42
        nonTerminal.add("AssCall");//43
        //赋值语句
        nonTerminal.add("AssignmentRest");//44
        //条件语句
        nonTerminal.add("ConditionalStm");//45
        //循环语句
        nonTerminal.add("LoopStm");//46
        //输入语句
        nonTerminal.add("InputStm");//47
        nonTerminal.add("Invar");//48
        //输出语句
        nonTerminal.add("OutputStm");//49
        //返回语句
        nonTerminal.add("ReturnStm");//50
        //过程调用语句
        nonTerminal.add("CallStmRest");//51
        nonTerminal.add("ActParamList");//52
        nonTerminal.add("ActParamMore");//53
        //条件表达式
        nonTerminal.add("RelExp");//54
        nonTerminal.add("OtherRelE");//55
        //算术表达式
        nonTerminal.add("Exp");//56
        nonTerminal.add("OtherTerm");//57
        //项
        nonTerminal.add("Term");//58
        nonTerminal.add("OtherFactor");//59
        //因子
        nonTerminal.add("Factor");//60
        nonTerminal.add("Variable");//61
        nonTerminal.add("VariMore");//62
        nonTerminal.add("FieldVar");//63
        nonTerminal.add("FieldVarMore");//64
        nonTerminal.add("CmpOp");//65
        nonTerminal.add("AddOp");//66
        nonTerminal.add("MulOp");//67

        //生成104条规则的Predict集
        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 5; j++) {
                predict[i][j] = null;
            }
        }
        predict[0][0] = null;

        predict[1][0] = "program";//1

        predict[2][0] = "program";//2

        predict[3][0] = "ID";//3

        predict[4][0] = "type";//4
        predict[4][1] = "var";
        predict[4][2] = "precedure";
        predict[4][3] = "begin";

        predict[5][0] = "type";//5
        predict[5][1] = "var";
        predict[5][2] = "precedure";
        predict[5][3] = "begin";

        predict[6][0] = "type";//6

        predict[7][0] = "ID";//7

        predict[8][1] = "var";//8
        predict[8][2] = "precedure";
        predict[8][3] = "begin";
        predict[8][4] = "ID";

        predict[9][0] = "ID";//9

        predict[10][0] = "integer";//10
        predict[10][1] = "char";
        predict[10][2] = "array";
        predict[10][3]="record";
        predict[10][4]="ID";
        
        predict[11][0]="integer";//11
        predict[11][1]="char";
    }
}
