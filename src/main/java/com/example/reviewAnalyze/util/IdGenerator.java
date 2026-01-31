package com.example.reviewAnalyze.util;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class IdGenerator {
    public static String getPlaceDisplayId(){
        return NanoIdUtils.randomNanoId();
    }
}
