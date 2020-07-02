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


/**
 * CommonMethod��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/5 1:11
 * @AUTHOR ��˶~
 */

public class CommonMethod {

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
