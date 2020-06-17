package com.mccspace.hs.service.clientThread;

import com.mccspace.hs.service.game.CheckerBoard;
import com.mccspace.hs.service.game.gameplayer.GamePlayer;
import com.mccspace.hs.service.listen.ConnectIntyerface;
import com.mccspace.hs.service.listen.ConnectListen;
import com.mccspace.hs.service.listen.*;
import com.mccspace.hs.service.manager.Player;
import com.mccspace.hs.service.roomThread.Room;
import com.mccspace.hs.tools.Base64Crypto;
import com.mccspace.hs.tools.CommonMethod;
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

import static com.mccspace.hs.tools.Parameter.cc;

/**
 * Connect类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 17:02
 * @AUTHOR 韩硕~
 */

public class Connect implements Runnable, GamePlayer {

    private Socket socket;

    private PrintWriter bw;

    private String user;

    private Room inRoom;

    private List<ConnectIntyerface> Listen = new CopyOnWriteArrayList<>();

    public Connect(Socket socket) {
        this.socket = socket;
        var thread = new Thread(this);
        thread.start();
        Print.standOutput(this,"成功连接到服务器，已于"+thread.getName()+"处理请求");
    }

    @Override
    public void run() {
        try{

            var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new PrintWriter(socket.getOutputStream());

            //添加常监听器：服务器信息请求
            addListen(new ListeningConstant.getInform(this,true));

            //添加常监听器：注册验证码发送请求
            addListen(new ListeningConstant.register(this,true));

            //添加常监听器：登录请求
            addListen(new ListeningConstant.login(this,true));

            //线程开始消息处理请求
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
            Player.loginPlayer.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Print.standOutput(getAddress(),"已断开链接");
        }
    }

    public void login(String user){
        removeAllListen();
        this.user = user;
        Player.loginPlayer.add(this);
        addListen(new ListeningConstant.getPlayerInform(this,true));
        addListen(new ListeningConstant.createRoom(this,true));
        addListen(new ListeningConstant.joinRoom(this,true));
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

    public String getUser() {
        return user;
    }

    public Room getInRoom() {
        return inRoom;
    }

    public void setInRoom(Room inRoom) {
        this.inRoom = inRoom;
    }

    @Override
    public List<Integer> waitPlay() {
        final List<Integer>[] play = new List[1];
        play[0] = new ArrayList<>();
        Object lock = new Object();
        addListen(new ConnectListen("play") {
            @Override
            public void run(JSONObject data) {
                for(var a : data.getJSONArray("play").toList())
                    play[0].add((int)a);
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        });
        synchronized (lock){
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return play[0];
        }
    }

    @Override
    public void updataChecker(CheckerBoard checkerBoard,List<Integer> upChess) {
        var board = new JSONObject();
        board.put("notEmote",checkerBoard.notEmote());
        board.put("isBlack",checkerBoard.isBlack());
        board.put("isKing",checkerBoard.isKing());
        var data = new JSONObject();
        data.put("board",board);
        data.put("upChess",upChess);
        data.put("nowPro",checkerBoard.getProbably());
        if(checkerBoard.blackPlay()){
            if (inRoom.isBlack(this))
                data.put("wait",false);
            else
                data.put("wait",true);
        } else {
            if (inRoom.isBlack(this))
                data.put("wait",true);
            else
                data.put("wait",false);
        }
        printPacket("updataChecker", data);
    }

    @Override
    public JSONObject getInform() {
        return CommonMethod.getInform(user);
    }
}
