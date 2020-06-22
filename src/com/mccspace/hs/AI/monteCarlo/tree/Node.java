package com.mccspace.hs.AI.monteCarlo.tree;

import com.mccspace.hs.service.game.CheckerBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NodeÀà
 * Git to£º http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/18 21:28
 * @AUTHOR º«Ë¶~
 */

public class Node implements Comparable{

    private CheckerBoard checkerBoard;

    private List<CheckerBoard> son = new ArrayList<>();

    private int win = 0;

    private int gameNum = 0;

    public Node(CheckerBoard checkerBoard, int win, int gameNum) {
        Tree.getTree().getAllTree().put(checkerBoard.hashCode(),this);
        this.checkerBoard = checkerBoard;
        for(var i:checkerBoard.getProbably())
            son.add(checkerBoard.play(i));
        Collections.shuffle(son);
        this.win = win;
        this.gameNum = gameNum;
    }

    public void win(){
        synchronized (this) {
            win++;
            gameNum++;
        }
    }

    public void lose(){
        synchronized (this) {
            gameNum++;
        }
    }

    public boolean startSimulationOnce () {
        CheckerBoard next = Tree.getTree().getNode(checkerBoard).nextSimulation();
        boolean win;
        if(next != null)
            win = Tree.getTree().getNode(next).startSimulationOnce();
        else
            return true;
        if(win)
            win();
        else
            lose();
        return !win;
    }

    public List<Integer> getPro(){
        Tree tree = Tree.getTree();
        List<Node> sonNode = new ArrayList<>();
        for(var i:son)
            sonNode.add(tree.getNode(i));
        Collections.sort(sonNode);
        return checkerBoard.getProbably().get(son.indexOf(((!checkerBoard.blackPlay())?sonNode.get(0):sonNode.get(sonNode.size()-1)).checkerBoard));
    }

    public synchronized CheckerBoard nextSimulation(){
        Tree tree = Tree.getTree();
        List<Node> sonNode = new ArrayList<>();
        for(var i:son){
            if(tree.getNode(i).getGameNum() == 0)
                return i;
            sonNode.add(tree.getNode(i));
        }
        Collections.sort(sonNode);
        if(sonNode.size() != 0)
            return sonNode.get(0).getCheckerBoard();
        else
            return null;
    }

    public CheckerBoard getCheckerBoard() {
        return checkerBoard;
    }

    public List<CheckerBoard> getSon() {
        return son;
    }

    public double winningProbability(){
        return (gameNum == 0)?0:win/gameNum;
    }

    public int getGameNum() {
        return gameNum;
    }

    public int getWin() {
        return win;
    }

    @Override
    public int compareTo(Object o) {
        if(((Node)o).winningProbability() < winningProbability())
            return 1;
        else
            return -1;
    }
}
