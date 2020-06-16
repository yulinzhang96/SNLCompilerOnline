/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resources.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.*;

class Token { // Token类

    public int line; // 行号
    public String LEX;//词法信息
    public String SEM;//语义信息

    public Token(int i, String LEX, String SEM) {
        this.line = line;
        this.LEX = LEX;
        this.SEM = SEM;
    }
    
     //根据Value取Key  
    public static String getKeyByValue(Map separatorLEX, Object value) {
        String key = null;
        Iterator it = separatorLEX.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Entry) it.next();
            Object obj = entry.getValue();
            if (obj != null && obj.equals(value)) {
                key = (String) entry.getKey();
            }
        }
        return key;
    }
}

public class createToken {

    public static StringBuffer tokenDisplay; // 测试token用StringBuffer
    public static List<Token> token; // token列表
    public static List<Integer> line;//token的行号

    public static List<String> separator; // 分界符列表
    public static Map<String, String> separatorLEX; //分界符词法信息列表
    public static List<String> reservedWord; // 保留字列表
    public static List<Character> Letter; // 大小写字母表
    public static List<Character> Digit; // 0-9
    public static List<Character> Digit1; // 1-9

    public static void initialize() { // 初始化各个列表
        separator = new ArrayList<String>();
        separatorLEX = new HashMap<String, String>();

        separator.add(",");
        separatorLEX.put(",", "COMMA");
        separator.add(";");
        separatorLEX.put(";", "SEMI");
        separator.add("+");
        separatorLEX.put("+", "PLUS");
        separator.add("-");
        separatorLEX.put("-", "MINUS");
        separator.add("*");
        separatorLEX.put("*", "MULTIPLICATION");
        separator.add("/");
        separatorLEX.put("/", "DIVISION");
        separator.add("<");
        separatorLEX.put("<", "LESS");
        separator.add("=");
        separatorLEX.put("=", "EQUAL");
        separator.add("(");
        separatorLEX.put("(", "LEFTPAR");
        separator.add(")");
        separatorLEX.put(")", "RIGHTPAR");
        separator.add("[");
        separatorLEX.put("[", "LEFTBRACKET");
        separator.add("]");
        separatorLEX.put("]", "RIGHTBRACKET");
        separator.add(":=");
        separatorLEX.put(":=", "ASSIGN");
        separator.add(".");
        separatorLEX.put(".", "DOT");
        separator.add("..");
        separatorLEX.put("..", "UNDERANGE");
        separator.add(":");
        separatorLEX.put(":", "COLON");

        reservedWord = new ArrayList<String>();
        reservedWord.add("program");
        reservedWord.add("type");
        reservedWord.add("integer");
        reservedWord.add("char");
        reservedWord.add("array");
        reservedWord.add("INTC");
        reservedWord.add("record");
        reservedWord.add("end");
        reservedWord.add("var");
        reservedWord.add("of");
        reservedWord.add("procedure");
        reservedWord.add("begin");
        reservedWord.add("if");
        reservedWord.add("then");
        reservedWord.add("else");
        reservedWord.add("fi");
        reservedWord.add("while");
        reservedWord.add("do");
        reservedWord.add("endwh");
        reservedWord.add("read");
        reservedWord.add("write");
        reservedWord.add("return");
        reservedWord.add("repeat");

        Letter = new ArrayList<Character>();
        Digit = new ArrayList<Character>();
        Digit1 = new ArrayList<Character>();
        Letter.add('A');
        Letter.add('B');
        Letter.add('C');
        Letter.add('D');
        Letter.add('E');
        Letter.add('F');
        Letter.add('G');
        Letter.add('H');
        Letter.add('I');
        Letter.add('J');
        Letter.add('K');
        Letter.add('L');
        Letter.add('M');
        Letter.add('N');
        Letter.add('O');
        Letter.add('P');
        Letter.add('Q');
        Letter.add('R');
        Letter.add('S');
        Letter.add('T');
        Letter.add('U');
        Letter.add('V');
        Letter.add('W');
        Letter.add('X');
        Letter.add('Y');
        Letter.add('Z');
        Letter.add('a');
        Letter.add('b');
        Letter.add('c');
        Letter.add('d');
        Letter.add('e');
        Letter.add('f');
        Letter.add('g');
        Letter.add('h');
        Letter.add('i');
        Letter.add('j');
        Letter.add('k');
        Letter.add('l');
        Letter.add('m');
        Letter.add('n');
        Letter.add('o');
        Letter.add('p');
        Letter.add('q');
        Letter.add('r');
        Letter.add('s');
        Letter.add('t');
        Letter.add('u');
        Letter.add('v');
        Letter.add('w');
        Letter.add('x');
        Letter.add('y');
        Letter.add('z');
        Digit.add('0');
        Digit.add('1');
        Digit.add('2');
        Digit.add('3');
        Digit.add('4');
        Digit.add('5');
        Digit.add('6');
        Digit.add('7');
        Digit.add('8');
        Digit.add('9');
        Digit1.add('1');
        Digit1.add('2');
        Digit1.add('3');
        Digit1.add('4');
        Digit1.add('5');
        Digit1.add('6');
        Digit1.add('7');
        Digit1.add('8');
        Digit1.add('9');
    }
}
