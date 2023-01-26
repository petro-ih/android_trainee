package com.petro.scope103;

import android.graphics.Color;

import java.util.Random;

public class ColorHelper {
    public static int generateColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
