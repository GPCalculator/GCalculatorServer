package com.mccspace.hs.AI;

/**
 * Step��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/20 21:54
 * @AUTHOR ��˶~
 */

public class Step {

    public static Step ls1 = new Step();

    private int step = 0;

    public synchronized void add(){
        step++;
    }

    public int getStep(){
        return step;
    }

    public synchronized void clear(){
        step = 0;
    }
}
