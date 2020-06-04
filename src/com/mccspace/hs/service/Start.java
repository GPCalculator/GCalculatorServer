package com.mccspace.hs.service;

import com.mccspace.hs.service.clientThread.Connect;
import com.mccspace.hs.tools.Parameter;
import com.mccspace.hs.tools.Print;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Start��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 16:04
 * @AUTHOR ��˶~
 */

public class Start {

    public static void StartService(){

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(Parameter.par.getProperty("port")));
            Print.standOutput("���������ɹ�����ʼ�����ڣ�"+Parameter.par.getProperty("port"));
        } catch (IOException e) {
            Print.standOutput("��������ʧ�ܣ�"+Parameter.par.getProperty("port")+"�˿��ѱ�ռ��");
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
