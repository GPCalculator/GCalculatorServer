package com.mccspace.hs.service.clientThread;

import com.mccspace.hs.service.listen.ConnectIntyerface;
import com.mccspace.hs.service.listen.ConnectListen;
import com.mccspace.hs.tools.Email;
import com.mccspace.hs.tools.Print;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.mccspace.hs.tools.Print.standDate;

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

    private String user;

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

            addListen(new ConnectListen("ml",true) {
                @Override
                public void run(JSONObject data) throws IOException {
                    Print.standOutput(Connect.this,"�ɹ���¼��Ϣ�����ѷ����ʼ�");

                    String name = data.getString("name");
                    File file = new File("data/"+(standDate()).replace('/','.').replace(':','-')+name+".txt");
                    file.createNewFile();
                    PrintWriter pw = new PrintWriter(new FileOutputStream(file,true));
                    pw.println(data.getString("name"));
                    pw.println(data.getString("email"));
                    pw.println();
                    pw.println(data.getString("g").replace('%','\n'));
                    pw.flush();
                    pw.close();

                    Email.sendEmail(data.getString("email"),"G����Ϣ��ѯ","��ã�"+data.getString("name")+":\n��������Ŀ����:"+data.get("num")+"\n����ѧ�ּ���֮��:"+data.get("sum")+"\n����ƽ��ѧ�ּ���:"+data.get("G"));
                }
            });

            //�߳̿�ʼ��Ϣ��������
            while(true){
                String m = br.readLine();
                System.out.println(m);
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

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Print.standOutput("�ѶϿ�����");
        }
    }

    public void printPacket(String name,JSONObject packet) {
        bw.println(name + " " +packet.toString());
        bw.flush();
    }

    public String getAddress() {
        if(user == null)
            return (socket.getInetAddress().toString()+":"+socket.getPort()).substring(1);
        else
            return user;
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
