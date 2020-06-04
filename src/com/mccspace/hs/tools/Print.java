package com.mccspace.hs.tools;

import com.mccspace.hs.service.clientThread.Connect;

import java.io.*;
import java.util.Date;

/**
 * Print类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 16:12
 * @AUTHOR 韩硕~
 */

public class Print {

    private static Print pr;

    private File file;

    private Print(){
        pr = this;
        file = new File("log/"+(standDate()).replace('/','.').replace(':','-')+"RunLog.txt");
        try {
            if (file.createNewFile()) {
                standOutput("日志文件创建成功，本次运行日志记录于 "+file.getAbsolutePath());
            } else {
                System.out.println(standPrint("日志文件创建失败！即将退出系统"));
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String standDate(){
        var date = new Date();
        return "["+(date.getYear()+1900)+"/"+date.getMonth()+"/"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"]";
    }

    private static String standPrint(String inform){
        return standDate()+"\"Server\""+":  "+inform;
    }

    private static String standPrint(String user,String inform){
        return standDate()+"\""+user+"\":  "+inform;
    }

    private static void outputInform(String inform){
        PrintWriter pw = null;
        try {
            if(pr == null){
                pw = new PrintWriter(new FileOutputStream(new Print().file,true));
            } else {
                pw = new PrintWriter(new FileOutputStream(pr.file,true));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.println(inform);
        pw.close();
        System.out.println(inform);
    }

    public static void standOutput(String inform){
        outputInform(standPrint(inform));
    }

    public static void standOutput(String user,String inform){
        outputInform(standPrint(user, inform));
    }

    public static void standOutput(Connect c, String inform){
        outputInform(standPrint(c.getAddress(), inform));
    }

}
