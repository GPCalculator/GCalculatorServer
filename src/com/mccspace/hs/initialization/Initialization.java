package com.mccspace.hs.initialization;

import com.mccspace.hs.tools.Parameter;

import javax.mail.Session;

/**
 * Initialization类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 15:54
 * @AUTHOR 韩硕~
 */

public class Initialization {

    public static void emailInit() {

        try {

            Parameter.par.put("port", "1313");
            Parameter.par.put("mail.transport.protocol","smtp");
            Parameter.par.put("mail.smtp.host","183.3.225.42");
            Parameter.par.put("mail.smtp.port","465");
            Parameter.par.put("mail.smtp.auth","true");
            Parameter.par.put("mail.smtp.ssl.enable","true");
            Parameter.par.put("mail.debug","false");
            Parameter.par.put("mail.admin.user","1750359613@qq.com");
            Parameter.par.put("mail.admin.password","no！");

            Parameter.session = Session.getInstance(Parameter.par);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
