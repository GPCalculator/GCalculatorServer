package com.mccspace.hs;

import com.mccspace.hs.AI.monteCarlo.runSeacherThread;
import com.mccspace.hs.AI.monteCarlo.tree.Tree;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * testÀà
 * Git to£º http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 16:18
 * @AUTHOR º«Ë¶~
 */

public class test {

    public static void main(String[] ar) throws InterruptedException {
        long startTime=System.currentTimeMillis();

        var a = CheckerBoard.newBoard();
        var node = Tree.getTree().getNode(a);

        ExecutorService es = Executors.newFixedThreadPool(12);
        for(int i=0;i<12;i++)
            es.submit(new runSeacherThread(a));

        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("ºÄÊ±£º"+(System.currentTimeMillis()-startTime));

        var c = Tree.getTree().getAllTree();
        System.out.println("666");
    }

}
