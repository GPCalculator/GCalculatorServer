package com.mccspace.hs.tools;

import javax.mail.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * Parameter类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 15:38
 * @AUTHOR 韩硕~
 */

public class Parameter {

    //配置文件集
    public static Properties par = new Properties();

    //邮件发送对象
    public static Session session;

}
