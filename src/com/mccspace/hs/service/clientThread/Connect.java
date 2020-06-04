package com.mccspace.hs.service.clientThread;

import com.mccspace.hs.service.listen.ConnectIntyerface;
import com.mccspace.hs.service.listen.ConnectListen;
import com.mccspace.hs.service.listen.*;
import com.mccspace.hs.tools.Email;
import com.mccspace.hs.tools.Print;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Connect��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 17:02
 * @AUTHOR ��˶~
 */

public class Connect implements Runnable{

    private Socket socket;

    private PrintWriter bw;

    private String name;

    private List<ConnectIntyerface> Listen = new CopyOnWriteArrayList<>();

    public Connect(Socket socket) {
        this.socket = socket;
        var thread = new Thread(this);
        thread.start();
        Print.standOutput(this,"�ɹ����ӵ�������������"+thread.getName()+"��������");
    }

    @Override
    public void run() {
        try{

            var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new PrintWriter(socket.getOutputStream());

            //��ӳ�����������������Ϣ����
            addListen(new ListeningConstant.getInform(this,true));

            //��ӳ���������ע����֤�뷢������
            addListen(new ListeningConstant.register(this,true));

            //��ӳ�����������¼����
            addListen(new ListeningConstant.login(this,true));

            //�߳̿�ʼ��Ϣ��������
            while(true){
                String m = br.readLine();
                if(m!=null) {
                    String[] packet = m.split(" ");
                    String json = "";
                    for(int i=1;i<packet.length;i++)
                        json += packet[i];
                    for (ConnectIntyerface li : Listen) {
                        if (li.whetherRun(packet[0])) {
                            if(json.equals(""))
                                li.run(new JSONObject());
                            else
                                li.run(new JSONObject(json));
                            if (!li.isPermanent())
                                Listen.remove(li);
                        }
                    }
                } else {
                    return;
                }
            }

        } catch (SocketException e){
            //�����Ͽ�����
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Print.standOutput(getAddress(),"�ѶϿ�����");
        }
    }

    public void login(String user){
        removeAllListen();
        name = user;
        addListen(new ListeningConstant.getPlayerInform(this,true));
    }

    public void printPacket(String name,JSONObject packet) {
        bw.println(name + " " +packet.toString());
        bw.flush();
    }

    public String getAddress() {
        if(name == null)
            return (socket.getInetAddress().toString()+":"+socket.getPort()).substring(1);
        else
            return name;
    }

    public void addListen(ConnectIntyerface ct){
        Listen.add(ct);
    }

    public void removeListen(String li){
        for(var a:Listen){
            if(a.getType().equals(li)){
                Listen.remove(a);
            }
        }
    }

    public void removeAllListen(){
        Listen = new CopyOnWriteArrayList<>();
    }
}
