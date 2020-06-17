package com.mccspace.hs;

import com.mccspace.hs.initialization.Initialization;
import com.mccspace.hs.service.game.CheckerBoard;
import com.mccspace.hs.tools.Email;
import com.mccspace.hs.tools.Parameter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * testÀà
 * Git to£º http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 16:18
 * @AUTHOR º«Ë¶~
 */

public class test {

    public static void main(String[] ar) {
        var a = CheckerBoard.newBoard();
        var b = new ArrayList<Integer>();
        b.add(17);
        b.add(22);
        a.play(b);
        b = new ArrayList<Integer>();
        b.add(32);
        b.add(27);
        a.play(b);
    }

}
