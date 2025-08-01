package com.lacaba.tododroid.util;

import android.util.Log;

public class Logger {

    public enum LogLevel {
        INFO, DEBUG, WARNING, CRITICAL;
    }

    public static void i(String message){
        Log.d(LogLevel.INFO.toString(), message);
    }
    
    public static void d(String message){
        Log.d(LogLevel.DEBUG.toString(), message);
    }
    
    public static void w(String message){
        Log.d(LogLevel.WARNING.toString(), message);
    }
    
    public static void c(String message){
        Log.d(LogLevel.CRITICAL.toString(), message);
    }
    
}
