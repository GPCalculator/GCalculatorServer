package com.mccspace.hs.service.roomThread;

import com.mccspace.hs.service.clientThread.Connect;
import com.mccspace.hs.service.game.CheckerBoard;
import com.mccspace.hs.service.game.gameplayer.GamePlayer;
import com.mccspace.hs.service.listen.ConnectListen;
import com.mccspace.hs.service.manager.RoomManager;
import com.mccspace.hs.tools.Print;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Room类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/5 0:32
 * @AUTHOR 韩硕~
 */

public class Room implements Runnable {

    private GamePlayer white;
    private GamePlayer black;
    private boolean whiteReady;
    private boolean blackReady;

    private static int roomIDList = 10001;

    private int roomID;
    private boolean usePassword;
    private String password;

    private CheckerBoard checkerBoard;
    private List<Integer> upChess;

    public Room() {
        roomID = roomIDList++;
        usePassword = false;
        RoomManager.room.put(roomID,this);
    }
    public Room(String password) {
        roomID = roomIDList++;
        usePassword = true;
        this.password = password;
        RoomManager.room.put(roomID,this);
    }

    @Override
    public void run() {
        checkerBoard = CheckerBoard.newBoard();
        updataChecker();
        while(true){
            checkerBoard = checkerBoard.play(black.waitPlay());
            updataChecker();
            //胜利检测
            checkerBoard = checkerBoard.play(white.waitPlay());
            updataChecker();
            //胜利检测
        }
    }

    public void updateRoomInform() {
        var data = new JSONObject();
        data.put("white", white != null ? white.getInform() : null);
        data.put("black", black != null ? black.getInform() : null);
        data.put("blackReady", blackReady);
        data.put("whiteReady", whiteReady);
        data.put("roomID", roomID);
        if (white instanceof Connect) {
            ((Connect) white).printPacket("updateRoomInform", data);
        }
        if (black instanceof Connect) {
            ((Connect) black).printPacket("updateRoomInform", data);
        }
    }

    public void updataChecker(){
        black.updataChecker(checkerBoard,upChess);
        white.updataChecker(checkerBoard,upChess);
    }



    public void leftWhite() {
        white = null;
        whiteReady = false;
        updateRoomInform();
    }

    public void leftBlack() {
        black = null;
        blackReady = false;
        updateRoomInform();
    }

    public void join(Connect connect){
        var data = new JSONObject();
        if(usePassword){
            data.put("result",false);
            connect.printPacket("joinRoom",data);
            connect.removeListen("joinRoomPws");
            connect.addListen(new ConnectListen("joinRoomPws") {
                @Override
                public void run(JSONObject data) {
                    if(data.getString("pws").equals(password)) {
                        joinBuffer(connect);
                        var rs = new JSONObject();
                        rs.put("result",true);
                        connect.printPacket("joinRoomPws",rs);
                    } else {
                        var rs = new JSONObject();
                        rs.put("result",false);
                        connect.printPacket("joinRoomPws",rs);
                    }
                }
            });
        } else {
            data.put("result",true);
            connect.printPacket("joinRoom",data);
            joinBuffer(connect);
        }
    }

    public void joinBuffer(Connect connect){
        connect.removeListen("updateRoomInform");
        connect.addListen(new ConnectListen("updateRoomInform",true) {
            @Override
            public void run(JSONObject data) {
                updateRoomInform();
            }
        });
        connect.removeListen("updataChecker");
        connect.addListen(new ConnectListen("updataChecker",true) {
            @Override
            public void run(JSONObject data) {
                updataChecker();
            }
        });
        if(black == null) {
            Print.standOutput(connect,"已加入房间:"+roomID);
            joinBlack(connect);
            connect.setInRoom(this);
            connect.addListen(new ConnectListen("ready") {
                @Override
                public void run(JSONObject data) {
                    Print.standOutput(connect,"已准备");
                    readyBlack();
                }
            });
        } else if(white == null) {
            Print.standOutput(connect,"已加入房间:"+roomID);
            joinWhite(connect);
            connect.setInRoom(this);
            connect.addListen(new ConnectListen("ready") {
                @Override
                public void run(JSONObject data) {
                    Print.standOutput(connect,"已准备");
                    readyWhite();
                }
            });
        } else {
            //添加旁观者加入
        }
    }

    public void joinBlack(GamePlayer gamePlayer) {
        black = gamePlayer;
        updateRoomInform();
    }

    public void joinWhite(GamePlayer gamePlayer) {
        white = gamePlayer;
        updateRoomInform();
    }

    public void readyWhite() {
        whiteReady = true;
        updateRoomInform();
        if(blackReady)
            new Thread(this).start();
    }

    public void readyBlack() {
        blackReady = true;
        updateRoomInform();
        if(whiteReady)
            new Thread(this).start();
    }

    public int getRoomID() {
        return roomID;
    }

    public boolean isBlack(Connect connect){
        if(connect == black)
            return true;
        return false;
    }
}
