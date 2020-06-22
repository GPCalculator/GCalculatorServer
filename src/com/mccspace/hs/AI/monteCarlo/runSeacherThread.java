package com.mccspace.hs.AI.monteCarlo;

import com.mccspace.hs.AI.Continue;
import com.mccspace.hs.AI.Step;
import com.mccspace.hs.AI.monteCarlo.tree.Tree;
import com.mccspace.hs.service.game.CheckerBoard;

/**
 * runSeacherThreadÀà
 * Git to£º http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/19 8:38
 * @AUTHOR º«Ë¶~
 */

public class runSeacherThread implements Runnable{

    public static Object lock = new Object();

    private CheckerBoard checkerBoard;

    public runSeacherThread(CheckerBoard checkerBoard) {
        this.checkerBoard = checkerBoard;
    }

    @Override
    public void run() {
        while (true) {
            Tree.getTree().getNode(checkerBoard).startSimulationOnce();
            if(!Continue.ls1.isNext()){
                break;
            }
        }
    }
}
