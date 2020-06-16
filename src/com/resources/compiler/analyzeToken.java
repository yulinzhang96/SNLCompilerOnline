/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resources.compiler;

import java.util.ArrayList;
import java.util.List;

import com.resources.compiler.createToken;

public class analyzeToken {

    public static List<String> identifier; // 标识符列表
    public static List<String> Constant; // 常量列表

    public static boolean isIdentifier(String s) { // 标识符自动机
        if (!createToken.Letter.contains(s.charAt(0))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!createToken.Letter.contains(s.charAt(i)) && !createToken.Digit.contains(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isConstant(String s) { // 数字常量自动机
        if (s.charAt(0) == '0') {
            if (s.length() == 1) {
                return true;
            } else {
                return false;
            }
        }
        if (!createToken.Digit1.contains(s.charAt(0))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!createToken.Digit.contains(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean analyzeToken(String s) { // 词法分析主方法
        identifier = new ArrayList<String>(); // 每次调用词法分析方法时要先重新初始化各列表和缓冲字符串
        Constant = new ArrayList<String>();
        createToken.token = new ArrayList<Token>();
        createToken.line = new ArrayList<Integer>();
        createToken.tokenDisplay = new StringBuffer();
        int line = 1; // 行数初始化为1
        String SepString; // s是全部的字符串，SepString是分隔出的字符串
        StringBuffer SavedString = new StringBuffer(); // SavedString用来保存分隔中的字符串
        Token t; // 新建的token对象
        Boolean flag = false;//是否有注释
        Boolean success = true;//词法分析是否成功
        Boolean charID = false;//是否有字符标识符
        for (int i = 0; i < s.length(); i++) { // 从源程序中一个字符一个字符地进行读取，并逐个分离出单词，然后构造它们的机内表示Token
            //处理注释符
            if (s.charAt(i) == '{') {
                flag = true;
                continue;
            }
            if (flag == true && s.charAt(i) != '}') {
                continue;
            }
            if (flag == true && s.charAt(i) == '}') {
                flag = false;
                continue;
            }
            if (flag == false && s.charAt(i) == '}') {
                createToken.tokenDisplay.append("Error in line " + line + ": unable to recognize '" + '}' + "'.\n");
                success = false;
                SavedString = new StringBuffer();
                continue;
            }
            //处理字符标示符
            if (s.charAt(i) == '\'') {
                if (createToken.Letter.contains(s.charAt(i + 1)) && s.charAt(i + 2) == '\'') {
                    SepString = SavedString.toString();
                    t = new Token(line, String.valueOf(s.charAt(i + 1)), "letter");
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + "," + String.valueOf(s.charAt(i + 1)) + ",letter" + "\n");
                    i = i + 3;
                } else {
                    createToken.tokenDisplay.append("Error in line " + line + ": unable to recognize '" + '\'' + "'.\n");
                    success = false;
                    SavedString = new StringBuffer();
                    continue;
                }
            }
            //处理双引号
            if (s.charAt(i) == '\"') {
                createToken.tokenDisplay.append("Error in line " + line + ": unable to recognize '" + '\"' + "'.\n");
                success = false;
                SavedString = new StringBuffer();
                continue;
            }
            if (s.charAt(i) != ' ' && s.charAt(i) != '\n' && s.charAt(i) != '\t' && s.charAt(i) != '\"' && s.charAt(i) != '\'' && s.charAt(i) != '{' && s.charAt(i) != '}'
                    && !createToken.separator.contains(String.valueOf((s.charAt(i))))) { // 如果该字符不是分界符则直接追加到SavedString中
                SavedString.append(s.charAt(i));
            } else { // 如果该字符是分界符
                // 处理分离出的单词
                if (SavedString.length() != 0) { // 若分离出的单词长度为零，则跳过此部分而直接进入后面的分界符生成部分
                    SepString = SavedString.toString();
                    if (Character.isLetter(SepString.charAt(0))) {
                        if (createToken.reservedWord.contains((SepString))) { // 如果分隔出的字符串是reserved word则在token和tokenDisplay都要追加
                            t = new Token(line, SepString, "reserved word");
                            createToken.token.add(t);
                            createToken.line.add(line);
                            createToken.tokenDisplay.append(line + "," + SepString + ",reserved word" + "\n");
                        } else if (isIdentifier(SepString)) { // 如果分隔出的字符串是标识符则在token和tokenDisplay都要追加
                            if (!identifier.contains(SepString)) {
                                identifier.add(SepString); // 如果标识符列表中没有该标识符则添加
                            }
                            t = new Token(line, "ID", SepString);
                            createToken.token.add(t);
                            createToken.line.add(line);
                            createToken.tokenDisplay.append(line + ",ID," + SepString + "\n");
                        } else {
                            // 用*号行标识的代码行为方法的可能出口
                            // ************************************************************************************************
                            createToken.tokenDisplay.append("Error in line " + line + ": Unable to recognize \'" + SepString + "\'.\n");
                            success = false;
                            SavedString = new StringBuffer();
                            continue;
                        }
                    } else { // 如果是数字常量
                        if (isConstant(SepString)) {
                            if (!Constant.contains(SepString)) { // 如果数字常量列表中没有该数字常量则添加
                                Constant.add(SepString);
                            }
                            t = new Token(line, "INTC", SepString);
                            createToken.token.add(t);
                            createToken.line.add(line);
                            createToken.tokenDisplay.append(line + ",INTC," + SepString + "\n");
                        }
                    }
                    SavedString = new StringBuffer(); // 重新初始化用以分离单词的缓冲字符串
                }
                // 处理换行符
                if (s.charAt(i) == '\n') {
                    line++; // 将行号+1
                    continue;
                }
                if (s.charAt(i) == ' ') {
                    continue;
                }
                if (s.charAt(i) == '\t') {
                    continue;
                }
                if (s.charAt(i) == ':') {
                    if (s.charAt(++i) == '=') {
                        t = new Token(line, createToken.separatorLEX.get(":="), "separator"); // 如果分界符是赋值符则在token和tokenDisplay都要追加
                        createToken.token.add(t);
                        createToken.line.add(line);
                        createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get(":=") + "," + "separator" + "\n");
                        continue;
                    } else {
                        // ************************************************************************************************
                        createToken.tokenDisplay.append("Error in line " + line + ": " + "'=' should follow ':'.\n");
                        success = false;
                        SavedString = new StringBuffer();
                        continue;
                    }
                }
                if (s.charAt(i) == '.') {
                    if ((i + 1) != s.length() && s.charAt(i + 1) == '.') { // 如果是..则在token和tokenDisplay都要追加
                        t = new Token(line, createToken.separatorLEX.get(".."), "separator"); // 如果分界符是数组间符则在token和tokenDisplay都要追加
                        createToken.token.add(t);
                        createToken.line.add(line);
                        createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get("..") + "," + "separator" + "\n");
                        i++;
                        continue;
                    }
                    // 如果分界符是域作用符或程序结束符则在token和tokenDisplay都要追加
                    t = new Token(line, createToken.separatorLEX.get("."), "separator");
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get(".") + "," + "separator" + "\n");
                    if ((i + 1) == s.length() || s.charAt(i + 1) == ' ' || s.charAt(i + 1) == '\n' || s.charAt(i + 1) == '\t') { // 如果已到程序末尾或未达末尾但其后字符为空格、回车或制表符则结束词法分析
                        // *********************************************************************
                        line++;
                        createToken.tokenDisplay.append(line + ",EOF," + "END" + "\n");
                    }
                    continue;
                }
                t = new Token(line, createToken.separatorLEX.get(String.valueOf(s.charAt(i))), "separator"); // 分界符
                createToken.token.add(t);
                createToken.line.add(line);
                createToken.tokenDisplay.append(line + "," + createToken.separatorLEX.get(String.valueOf(s.charAt(i))) + "," + "separator" + "\n");
            }
        }
        // ************************************************************************************************
        if (SavedString.length() != 0) { // 若程序结束时分离出的单词长度不为零，则处理为相应的Token（尽管词法分析已失败，因为程序未能成功结束）
            SepString = SavedString.toString();
            if (Character.isLetter(SepString.charAt(0))) {
                if (createToken.reservedWord.contains((SepString))) { // 如果分隔出的字符串是reserved word则在token和tokenDisplay都要追加
                    t = new Token(line, SepString, "reserved word");
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + "," + SepString + ",reserved word" + "\n");
                } else if (isIdentifier(SepString)) { // 如果分隔出的字符串是标识符则在token和tokenDisplay都要追加
                    if (!identifier.contains(SepString)) {
                        identifier.add(SepString); // 如果标识符列表中没有该标识符则添加
                    }
                    t = new Token(line, "ID", SepString);
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + ",ID," + SepString + "\n");
                } else {
                    // 用*号行标识的代码行为方法的可能出口
                    // ************************************************************************************************
                    createToken.tokenDisplay.append("Error in line " + line + ": Unable to recognize \'" + SepString + "\'.\n");
                    success = false;
                    SavedString = new StringBuffer();
                }
            } else { // 如果是数字常量
                if (isConstant(SepString)) {
                    if (!Constant.contains(SepString)) { // 如果数字常量列表中没有该数字常量则添加
                        Constant.add(SepString);
                    }
                    t = new Token(line, "INTC", SepString);
                    createToken.token.add(t);
                    createToken.line.add(line);
                    createToken.tokenDisplay.append(line + ",INTC," + SepString + "\n");
                } else {
                    // ************************************************************************************************
                    createToken.tokenDisplay.append("Error in line " + line + ": Unable to recognize \'" + SepString + "\'.\n");
                    success = false;
                    SavedString = new StringBuffer();
                }
            }
            SavedString = new StringBuffer(); // 重新初始化用以分离单词的缓冲字符串
        }
        return success;
    }
}
