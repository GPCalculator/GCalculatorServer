package com.mccspace.hs.service.listen;

import com.mccspace.hs.service.clientThread.Connect;
import com.mccspace.hs.service.manager.Player;
import com.mccspace.hs.tools.Base64Crypto;
import com.mccspace.hs.tools.Email;
import com.mccspace.hs.tools.Parameter;
import com.mccspace.hs.tools.Print;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Random;

import static com.mccspace.hs.tools.Parameter.cc;

/**
 * ListeningConstant��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 19:18
 * @AUTHOR ��˶~
 */

public class ListeningConstant {

    public static class getInform extends ConnectListen{

        private Connect connect;

        public getInform(Connect connect, boolean always) {
            super("getInform", always);
            this.connect = connect;
        }

        @Override
        public void run(JSONObject data) {
            JSONObject inform = new JSONObject();
            inform.put("player", Player.loginPlayer.size()+"/"+ Parameter.par.get("maxPlayer"));
            inform.put("inform",Parameter.par.get("motd"));
            connect.printPacket("getInform",inform);
        }
    }

    public static class matchCAPTCHA extends ConnectListen{

        private Connect connect;
        private String code;

        public matchCAPTCHA(Connect connect, String code ,boolean always) {
            super("matchCAPTCHA", always);
            this.connect = connect;
            this.code = code;
        }

        @Override
        public void run(JSONObject data) {
            if(data.getString("CAPTCHA").equals(code)){
                JSONObject inform = new JSONObject();
                inform.put("result",true);
                connect.printPacket("matchCAPTCHA",inform);
                connect.addListen(new initUser(connect,true) );
            } else {
                JSONObject inform = new JSONObject();
                inform.put("result",false);
                connect.printPacket("matchCAPTCHA",inform);
            }
            Print.standOutput(connect,"����ƥ�䣺"+data.getString("CAPTCHA"));
        }
    }

    public static class register extends ConnectListen{

        private Connect connect;

        public register(Connect connect, boolean always) {
            super("register", always);
            this.connect = connect;
        }

        @Override
        public void run(JSONObject data) {
            if(data.getString("type").equals("email")){
                connect.removeListen("matchCAPTCHA");
                var r = new Random();
                var code = ""+(r.nextInt(800000)+100000);
                Email.sendEmail(data.getString("num"),"HSChecker��֤��","������ע����֤��Ϊ:"+code);
                //��ӳ�����������֤��ƥ������
                connect.addListen(new ListeningConstant.matchCAPTCHA(connect,code,true));
                Print.standOutput(connect,"������֤��");
            }
        }
    }

    public static class initUser extends ConnectListen{

        private Connect connect;

        public initUser(Connect connect, boolean always) {
            super("initUser", true);
            this.connect = connect;
        }

        @Override
        public void run(JSONObject data){
            try {
                try (PreparedStatement ps = cc.prepareStatement("SELECT COUNT(*) num FROM user WHERE user = ?")) {
                    ps.setObject(1, data.getString("user"));
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        if(rs.getInt("num") != 0){
                            var turn = new JSONObject();
                            turn.put("result",false);
                            turn.put("reason","�û����ѱ�ʹ��");
                            connect.printPacket("initUser",turn);
                            return;
                        }
                    }
                }

                try (PreparedStatement ps = cc.prepareStatement("SELECT COUNT(*) num FROM user_inform WHERE email = ? OR phone = ?")) {
                    ps.setObject(1, data.getString("email"));
                    ps.setObject(2, data.getString("phone"));
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        if(rs.getInt("num") != 0){
                            var turn = new JSONObject();
                            turn.put("result",false);
                            turn.put("reason","������ֻ����ѱ�ע��");
                            connect.printPacket("initUser",turn);
                            return;
                        }
                    }
                }

                try (PreparedStatement ps = cc.prepareStatement("INSERT INTO user (user, password) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                    ps.setObject(1, data.getString("user"));
                    ps.setObject(2, data.getString("password"));
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        rs.next();
                        long id = rs.getLong(1);
                        try (PreparedStatement ps2 = cc.prepareStatement("INSERT INTO user_inform (email, phone, name, id, sex, birth, money, gamenum, winnum, drawnum, failnum) VALUES (?,?,?,?,?,?,0,0,0,0,0)")) {
                            ps2.setObject(1, data.getString("email"));
                            ps2.setObject(2, data.getString("phone"));
                            ps2.setObject(3, data.getString("name"));
                            ps2.setObject(4, id);
                            ps2.setObject(5, data.getString("sex"));
                            ps2.setObject(6, data.getString("birth"));
                            ps2.executeUpdate();
                            var turn = new JSONObject();
                            turn.put("result",true);
                            connect.printPacket("initUser",turn);
                            connect.login(data.getString("user"));
                            Print.standOutput(connect,"�ɹ�ע���˺ţ�"+data.getString("user"));
                        }
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static class login extends ConnectListen{

        private Connect connect;

        public login(Connect connect, boolean always) {
            super("login", true);
            this.connect = connect;
        }

        @Override
        public void run(JSONObject data){
            try {
                if(data.getString("type").equals("userPass")) {
                    try (PreparedStatement ps = cc.prepareStatement("SELECT * FROM user WHERE user = ?;")) {
                        ps.setObject(1, data.getString("num"));
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next() && rs.getString("password").equals(data.getString("password"))) {
                                var result = new JSONObject();
                                result.put("result",true);
                                connect.printPacket("login",result);
                                connect.login(data.getString("num"));
                                Print.standOutput(connect,"�ɹ���¼�˺ţ�"+data.getString("num"));
                            } else {
                                var result = new JSONObject();
                                result.put("result",false);
                                connect.printPacket("login",result);
                                Print.standOutput(connect,"��ͼ��¼�˺ţ�"+data.getString("num")+"������ʧ����");
                            }
                        }
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static class getPlayerInform extends ConnectListen{

        private Connect connect;

        public getPlayerInform(Connect connect, boolean always) {
            super("getPlayerInform", always);
            this.connect = connect;
        }

        @Override
        public void run(JSONObject data) {
            try (PreparedStatement ps = cc.prepareStatement("SELECT `user`.id, `user`.`user`, user_inform.`name`, user_inform.sex, user_inform.gamenum, user_inform.winnum, user_inform.drawnum, user_inform.failnum FROM user INNER JOIN user_inform ON user.id = user_inform.id WHERE `user`.`user` = ?;")) {
                ps.setObject(1, data.getString("player"));
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    var turn = new JSONObject();
                    String head;
                    File png;
                    if((png = new File("bin/head/"+data.getString("player")+".png")).exists())
                        head = Base64Crypto.encodeBase64File(png);
                    else
                        head = Base64Crypto.encodeBase64File(new File("bin/head/Ĭ��.png"));
                    turn.put("head",head);
                    turn.put("user",rs.getString("user"));
                    turn.put("name",rs.getString("name"));
                    turn.put("sex",rs.getString("sex"));
                    turn.put("gamenum",rs.getInt("gamenum"));
                    turn.put("winnum",rs.getInt("winnum"));
                    turn.put("drawnum",rs.getInt("drawnum"));
                    turn.put("failnum",rs.getInt("failnum"));
                    connect.printPacket("getPlayerInform",turn);
                    Print.standOutput(connect,"��ȡ�ˣ�"+turn.getString("user")+" ����ϸ��Ϣ");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

}
