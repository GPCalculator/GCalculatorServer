package com.mccspace.hs.AI;

/**
 * Continue��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/21 1:00
 * @AUTHOR ��˶~
 */

public class Continue {

    public static Continue ls1 = new Continue();

    private boolean next = true;

    public void stop(){
        next = false;
    }

    public boolean isNext(){
        return next;
    }

    public void clear(){
        next = true;
    }

}
