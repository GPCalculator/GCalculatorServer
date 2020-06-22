package com.mccspace.hs.initialization;

import com.mccspace.hs.tools.Parameter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.mccspace.hs.tools.Parameter.cc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Initialization类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 15:54
 * @AUTHOR 韩硕~
 */

public class Initialization {

    public static void MySQLInit(){

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Properties pro = new Properties();
            FileInputStream in = new FileInputStream("bin/database.properties");
            pro.load(in);
            in.close();

            String url = "jdbc:mysql://"+pro.getProperty("address")+":"+pro.getProperty("port")+"/"+pro.getProperty("base")+"?serverTimezone=UTC";
            String user = pro.getProperty("user");
            String password = pro.getProperty("password");

            cc = DriverManager.getConnection(url, user, password);

            var st = cc.createStatement();
            ResultSet result = st.executeQuery("select * from Setting");
            while (result.next()) {
                String string = result.getString("Key");
                String string2 = result.getString("Value");
                Parameter.par.put(string,string2);
            }
            result.close();
            st.close();
            cc.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void emailInit() {

        try {

            Parameter.par.put("mail.smtp.port", Integer.parseInt(Parameter.par.getProperty("mail.smtp.port")));

            Parameter.session = Session.getInstance(Parameter.par);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
