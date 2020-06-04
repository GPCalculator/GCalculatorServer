package com.mccspace.hs.service;

import com.mccspace.hs.service.clientThread.Connect;
import com.mccspace.hs.tools.Parameter;
import com.mccspace.hs.tools.Print;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Start类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 16:04
 * @AUTHOR 韩硕~
 */

public class Start {

    public static void StartService(){

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(Parameter.par.getProperty("port")));
            Print.standOutput("服务启动成功，开始监听于："+Parameter.par.getProperty("port"));
        } catch (IOException e) {
            Print.standOutput("服务启动失败："+Parameter.par.getProperty("port")+"端口已被占用");
        }
        Socket socket = null;
        while(true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Connect(socket);
        }
    }

}
