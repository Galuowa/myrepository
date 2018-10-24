package com.itcast.junit.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.random;
import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class Calculator extends JFrame{
    double result;
    String num = "0";
    int trueNum = 0;
    int falseNum = 0;
    long startTime;
    long endTime;
    int Enum;
    int Summary = 0;
    JFrame frame;
    public void CreateJFrame() {
        this.setSize(500, 350);        // 大小
        setBackground(Color.white);
        this.setTitle("四则运算“软件”之升级版");        // 标题
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setLocationRelativeTo(null);    //界面居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        // 关闭方式
        JLabel jl = new JLabel("四则运算题目的个数(不超过5个）:",JLabel.LEFT);        // 创建一个JLabel标签
        JTextField t1=new JTextField(10);
        JPanel p1 = new JPanel(new GridLayout(1,2));
        JLabel j2 = new JLabel("题目:",JLabel.LEFT);
        JTextField t2=new JTextField(15);
        JPanel p2 = new JPanel(new GridLayout(1,2));
        JLabel j3 = new JLabel("请输入答案:",JLabel.LEFT);
        JTextField t3=new JTextField(15);
        JPanel p3 = new JPanel(new GridLayout(1,2));
        JLabel j4 = new JLabel("答题情况:",JLabel.LEFT);
        JTextField t4=new JTextField(15);
        JPanel p4 = new JPanel(new GridLayout(1,2));
        JButton b1 = new JButton("开始");
        JButton b2 = new JButton("下一题");
        JButton b3 = new JButton("改变界面的颜色");
        JPanel p5 = new JPanel(new GridLayout(1,3));
        p1.add(jl);
        p1.add(t1);
        p2.add(j2);
        p2.add(t2);
        p3.add(j3);
        p3.add(t3);
        p4.add(j4);
        p4.add(t4);
        p5.add(b1);
        p5.add(b2);
        p5.add(b3);
        Container container = this.getContentPane();        // 获取一个容器
        container.add(p1);        // 将标签添加至容器
        container.add(p2);
        container.add(p3);
        container.add(p4);
        container.add(p5);
        container.setBackground(Color.WHITE);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Enum =Integer.parseInt(t1.getText());
                while (Enum<0 || Enum>5){
                    JOptionPane.showMessageDialog(null,"你输入的个数有误，请重新输入！");
                };
                startTime=System.currentTimeMillis();
                do{
                    num = Create();
                    result = arithmetic(num);
                }while (result < 0);
                Summary++;
                t2.setText(num);
            }
        });
        t3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(result==Integer.parseInt(t3.getText()))
                {
                    t4.setText("恭喜你！答对了！");
                    trueNum++;
                }
                else
                {
                    t4.setText("答错了！正确答案是"+ result);
                    falseNum++;
                }
            }
        });
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(Summary==Enum && Summary!=0)
                {
                    endTime=System.currentTimeMillis();
                    long SpendTime = (endTime-startTime)/1000;
                    JOptionPane.showMessageDialog(null, "答题已完成。答题总题数为"+Enum+",答对题数为"+trueNum+",答错题数为"+falseNum+",答题使用时间:"+SpendTime+"秒。");
                    frame.setVisible(false);
                    new Calculator();
                }else if(Summary!=Enum) {
                    do{
                        num = Create();
                        result = arithmetic(num);
                    }while (result < 0);
                    Summary++;
                    t2.setText(num);
                    t3.setText(null);
                    t4.setText(null);
                }
            }
        });
        b3.addActionListener(new ActionListener() {
            int click = 0;
            @Override
            public void actionPerformed(ActionEvent cc) {
                if (click%2 == 0) {
                    container.setBackground(Color.BLUE);
                    click++;
                } else {
                    container.setBackground(Color.WHITE);
                    click++;
                }
            }
        });
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new Calculator().CreateJFrame();        // 调用CreateJFrame()方法
    }
    public static String Create() {
        int rand = (int) (random() * 2 + 2);
        String num[] = new String[rand + 1];
        num[0] = "" + (int) (random() * 99 + 1);
        String Totality="";
        String cha[] = new String[rand];
        for (int i = 0; i < rand; i++) {
            int Char = (int) (random() * 3 + 1);
            num[i+1] = "" + (int) (random() * 99 + 1);
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
        }
        int j=0;
        while (j<rand-1){
            int change=(int)((10*Math.random())%2);
            if(change!=0)
            {
                Totality = Totality + '('+ num[j] + cha[j] ;//'('+Totality+')';
                j++;
                Totality = Totality +  num[j]+ ')' + cha[j];
                j++;
            }else {
                Totality = Totality + num[j]  + cha[j] ;
                j++;
                Totality = Totality + num[j] + cha[j];
                j++;
            }
        };
        Totality = Totality + num[j] ;
        return Totality;
    }
    public static double arithmetic(String res){
        String result = parse(res).replaceAll("[\\[\\]]", "");
        Double Dresult=Double.parseDouble(result);
        String Result = String.format("%.2f", Dresult);
        return Double.parseDouble(Result);
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
            return parse(analysis);
        }
        //计算带括号的四则运算
        String minParentheses="\\([^\\(\\)]+\\)";
        Pattern patt=Pattern.compile(minParentheses);
        Matcher mat=patt.matcher(analysis);
        if(mat.find()){
            String tempMinExp=mat.group();
            analysis=analysis.replaceFirst(minParentheses, parse(tempMinExp));
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
            result=num1.divide(num2,3,ROUND_HALF_DOWN);
        }
        return result!=null?result.toString():null;
    }
}