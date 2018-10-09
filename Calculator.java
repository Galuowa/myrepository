package com.itcast.junit.test;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//四则运算表达式计算


public class Calculator {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入四则运算题目的个数（大于0）：");
        int Enum = scanner.nextInt();
        String num = "0";
        int trueNum = 0;
        Calculator cal = new Calculator();
        for (int i = 0; i < Enum; i++) {
            num = Create();;
            double result = arithmetic(num);
            if (result < 0) {
                i--;
                continue;
            }
            Scanner scannerMul = new Scanner(System.in);
            System.out.print(num+"=");
            float resultMul = scannerMul.nextFloat();
            if (resultMul != result) {
                System.out.println("错误！正确答案为" + result);
            } else {
                System.out.println("正确！");
                trueNum++;
            }
        }
        System.out.println("总共"+Enum+"题，答对"+trueNum+"题，一共获得"+(100*trueNum/Enum)+"分。（总共100分）");
    }
    public static String Create() {
        int rand = (int) (Math.random() * 2 + 2);
        String num[] = new String[rand + 1];
        num[0] = "" + (int) (Math.random() * 99 + 1);
        String Totality = num[0];
        String cha[] = new String[rand];
        for (int i = 0; i < rand; i++) {
            int Char = (int) (Math.random() * 3 + 1);
            num[i + 1] = "" + (int) (Math.random() * 99 + 1);
            switch (Char) {
                case 1:
                    cha[i] = "+";
                    break;
                case 2:
                    cha[i] = "-";
                    break;
                case 3:
                    cha[i] = "*";
                    break;
                case 4:
                    cha[i] = "/";
                    break;
                default:
                    break;
            }
            Totality = Totality + cha[i] + num[i + 1];
        }
        return Totality;
    }
    public static double arithmetic(String res){
        String result = parse(res).replaceAll("[\\[\\]]", "");
        return Double.parseDouble(result);
    }
    // 解析计算四则运算表达式
    public static String parse(String analysis){
        analysis=analysis.replaceAll("^\\((.+)\\)$", "$1");
        String min="^((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))[\\+\\-\\*\\/]((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))$";
        //最小表达式计算
        if(analysis.matches(min)){
            String result=calculate(analysis);
            return Double.parseDouble(result)>=0?result:"["+result+"]";
        }
        //计算不带括号的四则运算
        String noBrackets="^[^\\(\\)]+$";
        String priorOperatorExp="(((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))[\\*\\/]((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\])))";
        String operator="(((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))[\\+\\-]((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\])))";
        if(analysis.matches(noBrackets)){
            Pattern pat=Pattern.compile(priorOperatorExp);
            Matcher mat=pat.matcher(analysis);
            if(mat.find()){
                String tempMin=mat.group();
                analysis= analysis.replaceFirst(priorOperatorExp, parse(tempMin));
            }else{
                pat=Pattern.compile(operator);
                mat=pat.matcher(analysis);
                if(mat.find()){
                    String tempMin=mat.group();
                    analysis= analysis.replaceFirst(operator, parse(tempMin));
                }
            }
        }
        return parse(analysis);
    }
    // 计算最小单位四则运算表达式（两个数字）
    public static String calculate(String cal){
        cal=cal.replaceAll("[\\[\\]]", "");
        String num[]=cal.replaceFirst("(\\d)[\\+\\-\\*\\/]", "$1,").split(",");
        BigDecimal num1=new BigDecimal(num[0]);
        BigDecimal num2=new BigDecimal(num[1]);
        BigDecimal result=null;
        String operator=cal.replaceFirst("^.*\\d([\\+\\-\\*\\/]).+$", "$1");
        if("+".equals(operator)){
            result=num1.add(num2);
        }else if("-".equals(operator)){
            result=num1.subtract(num2);
        }else if("*".equals(operator)){
            result=num1.multiply(num2);
        }else if("/".equals(operator)){
            result=num1.divide(num2);
        }
        return result!=null?result.toString():null;
    }
}
