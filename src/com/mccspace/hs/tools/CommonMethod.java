package com.mccspace.hs.tools;

import org.json.JSONObject;

import javax.mail.internet.ParseException;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.mccspace.hs.tools.Parameter.cc;

/**
 * CommonMethod��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/5 1:11
 * @AUTHOR ��˶~
 */

public class CommonMethod {

    public static JSONObject getInform(String user){
        try (PreparedStatement ps = cc.prepareStatement("SELECT `user`.id, `user`.`user`, user_inform.`name`, user_inform.sex, user_inform.gamenum, user_inform.winnum, user_inform.drawnum, user_inform.failnum, user_inform.birth FROM user INNER JOIN user_inform ON user.id = user_inform.id WHERE `user`.`user` = ?;")) {
            ps.setObject(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                var turn = new JSONObject();
                String head;
                File png;
                if ((png = new File("bin/head/" + user + ".png")).exists())
                    head = Base64Crypto.encodeBase64File(png);
                else
                    head = Base64Crypto.encodeBase64File(new File("bin/head/Ĭ��.png"));
                String str = rs.getString("birth");
                Date date = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = sdf.parse(str);
                } catch (java.text.ParseException e) {
                    System.out.println(e.getMessage());
                }

                turn.put("head", head);
                turn.put("age", getAgeByBirth(date));
                turn.put("user", rs.getString("user"));
                turn.put("name", rs.getString("name"));
                turn.put("sex", rs.getString("sex"));
                turn.put("gamenum", rs.getInt("gamenum"));
                turn.put("winnum", rs.getInt("winnum"));
                turn.put("drawnum", rs.getInt("drawnum"));
                turn.put("failnum", rs.getInt("failnum"));
                return turn;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static int getAgeByBirth(Date birthDay) {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //�����������ڵ�ǰʱ�䣬�޷�����
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //��ǰ���
        int monthNow = cal.get(Calendar.MONTH);  //��ǰ�·�
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //��ǰ����
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //����������
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//��ǰ����������֮ǰ�������һ
            } else {
                age--;//��ǰ�·�������֮ǰ�������һ
            }
        }
        return age;
    }

}
