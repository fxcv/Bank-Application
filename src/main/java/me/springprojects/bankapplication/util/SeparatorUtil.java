package me.springprojects.bankapplication.util;

import org.springframework.stereotype.Component;

@Component
public class SeparatorUtil {

    public String separateTheNumberPerFour(long num){
        String input = String.valueOf(num);
        if(input.length()%4!=0) return null; // length must be divisible by 4

        StringBuilder out = new StringBuilder();
        int length = input.length();
        for(int i = 0; i<length; i++){ // add whitespaces every fourth char
            out.append(input.charAt(i));
            if(i>0 && i<length-1 && (i+1)%4==0) out.append(" ");
        }
        return out.toString();
    }

    public long joinTheNumber(String num){

        StringBuilder formatted = new StringBuilder();
        for(char c : num.toCharArray()){ // get int compatible string
            if(!Character.isDigit(c)) continue;
            formatted.append(c);
        }
        long out = 0;
        for(String s : formatted.toString().split("")){ // get an int from string
            int n = Integer.parseInt(s);
            out = out * 10 + n;
        }
        return out;
    }
}
