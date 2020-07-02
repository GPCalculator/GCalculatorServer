package com.mccspace.hs;

import com.mccspace.hs.initialization.Initialization;
import com.mccspace.hs.service.Start;
import com.mccspace.hs.tools.Parameter;
import com.mccspace.hs.tools.Print;

import java.sql.SQLException;

/**
 * RUNÀà
 * Git to£º http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/25 11:22
 * @AUTHOR º«Ë¶~
 */

public class RUN {

    public static void main(String[] ar) {

        try {

            Initialization.emailInit();

            Start.StartService();

        } finally {

        }

    }

}
