package com.mccspace.hs.service.manager;

import com.mccspace.hs.service.game.gameplayer.GamePlayer;
import com.mccspace.hs.service.roomThread.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * RoomÀà
 * Git to£º http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/5 0:06
 * @AUTHOR º«Ë¶~
 */

public class RoomManager {

    public static Map<Integer,Room> room = new ConcurrentHashMap<>();

}
