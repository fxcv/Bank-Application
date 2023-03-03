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
        StringBuilder out = new StringBuilder();
        for(char c : num.toCharArray()){
            if(!Character.isDigit(c)) continue;
            out.append(c);
        }
        return Long.parseLong(out.toString());
    }
}
