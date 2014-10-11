/*
 * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;


/**
 * @author VanNT 11-09-2013
 */
public final class LogTracker {

    private static final String logfileName = "greyhound_log.txt";
    private static final File logfilePath = Environment.getExternalStorageDirectory();

//    public static void deleteLogFile() {
//        try {
//            File logFile = new File(logfilePath, logfileName);
//            FileUtils.delete(logFile);
//        } catch (Exception e) {}
//    }

    @SuppressLint("NewApi")
	public static void startUsingApp() {
        writeLogToFile("\n\n========================================\n");
        writeLogToFile("Start use Greyhound App at :" + new Date().toString());
        writeLogToFile("OS Version   :" + System.getProperty("os.version"));
        writeLogToFile("SDK Version  : " + Build.VERSION.SDK_INT);
        writeLogToFile("Version Code :" + Build.VERSION.CODENAME);
        writeLogToFile("Device Name  :" + Build.DEVICE);
        writeLogToFile("Model        :" + Build.MODEL);
        writeLogToFile("Product      :" + Build.PRODUCT);
        writeLogToFile("ManufacTurer :" + Build.MANUFACTURER);
        if (Build.VERSION.SDK_INT >= 14) {
            writeLogToFile("Radio Code :" + Build.getRadioVersion());
        } else {
            writeLogToFile("Radio Code :" + Build.RADIO);
        }
        writeLogToFile("Brand Code :" + Build.BRAND);
        writeLogToFile("java.vm.version :" + System.getProperty("java.vm.version"));
        writeLogToFile("os.arch :" + System.getProperty("os.arch"));
        writeLogToFile("========================================");
    }


    public static void stopUsingApp() {
        writeLogToFile("========================================");
        writeLogToFile("Stop use Greyhound App at :" + new Date().toString());
        writeLogToFile("========================================");
    }

    public static synchronized void writeLogToFile(final String log) {
        File logFile = new File(logfilePath, logfileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {}
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
            pw.println(log);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }
    public static synchronized InputStream getInputStreamForFileLog(){
        File logFile = new File(logfilePath, logfileName);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(logFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
    
    public static synchronized void writeExceptionLogToFile(final Exception pException) {
        File logFile = new File(logfilePath, logfileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {}
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
            if (pException == null) {
                pw.println("Exception is null , can't printStackTrace");
            } else {
                pException.printStackTrace(pw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
    
}
