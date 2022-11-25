package com.petro.scope101.util;

import android.text.TextUtils;
import android.util.Patterns;

public class TextValidator {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static boolean isValidPassword(CharSequence target){
        return hasLowercase(target) && hasUppercase(target) && hasSymbol(target) && hasNumber(target) && hasEightCharacters(target);

    }
    public static boolean hasLowercase(CharSequence target){
        for(int i = 0; i< target.length(); i++){
            if(Character.isLowerCase(target.charAt(i))){
                return true;
            }
        }
        return false;
    }
    public static boolean hasUppercase(CharSequence target){
        for(int i = 0; i< target.length(); i++){
            if(Character.isUpperCase(target.charAt(i))){
                return true;
            }
        }
        return false;
    }
    public static boolean hasSymbol(CharSequence target){
        for(int i = 0; i< target.length(); i++){
            if(!Character.isLetterOrDigit(target.charAt(i))){
                return true;
            }
        }
        return false;
    }
    public static boolean hasNumber(CharSequence target){
        for(int i = 0; i< target.length(); i++){
            if(Character.isDigit(target.charAt(i))){
                return true;
            }
        }
        return false;
    }
    public static boolean hasEightCharacters(CharSequence target){
        return target.length()>=8;
    }
}
