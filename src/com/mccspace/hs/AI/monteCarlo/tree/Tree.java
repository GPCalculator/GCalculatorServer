package com.mccspace.hs.AI.monteCarlo.tree;

import com.mccspace.hs.service.game.CheckerBoard;
import com.mccspace.hs.tools.Base64Crypto;
import com.mccspace.hs.tools.Parameter;
import org.json.JSONObject;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mccspace.hs.tools.Parameter.cc;

/**
 * Tree类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/18 21:28
 * @AUTHOR 韩硕~
 */

public class Tree {

    private static Tree tree;

    private Map<Integer, Node> sonNodeMap = new ConcurrentHashMap<>();

    private Tree(){

    }

    public static Tree getTree() {
        if(tree == null)
            return tree = new Tree();
        else
            return tree;
    }

    public Map<Integer, Node> getAllTree(){
        return sonNodeMap;
    }

    public Node getNode(CheckerBoard checkerBoard){
        Node result = getTree().sonNodeMap.get(checkerBoard.hashCode());
        if(result == null){
            try (PreparedStatement ps = cc.prepareStatement("SELECT * FROM node WHERE hash = ?;")) {
                ps.setObject(1, checkerBoard.hashCode());
                try (ResultSet rs = ps.executeQuery()) {
                    if(rs.next()){
                        return new Node(checkerBoard,rs.getInt("win"),rs.getInt("gamenum"));
                    } else {
                        return new Node(checkerBoard,0,0);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else
            return result;
        return null;
    }

    public void updateSQLTree(){
        try (PreparedStatement ps = cc.prepareStatement("REPLACE INTO node (hash,win,gamenum) VALUES (?,?,?);")) {
            // 对同一个PreparedStatement反复设置参数并调用addBatch():
            Iterator<Node> iterator=sonNodeMap.values().iterator();
            while(iterator.hasNext()){
                Node a = iterator.next();
                ps.setInt(1,a.getCheckerBoard().hashCode());
                ps.setInt(2,a.getWin());
                ps.setInt(3,a.getGameNum());
                ps.addBatch(); // 添加到batch
                sonNodeMap.remove(a.getCheckerBoard().hashCode());
            }
            // 执行batch:
            ps.executeBatch();
            cc.commit();
            /*for (int n : ns) {
                System.out.println(n + " inserted."); // batch中每个SQL执行的结果数量
            }*/
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
