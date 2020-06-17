package com.mccspace.hs.service.game.gameplayer;

import com.mccspace.hs.service.game.CheckerBoard;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * GamePlayerÀà
 *
 * @TIME 2020/5/25
 * @AUTHOR º«Ë¶~
 */

public interface GamePlayer {

    List<Integer> waitPlay();

    void updataChecker(CheckerBoard checkerBoard,List<Integer> upChess);

    JSONObject getInform();

}
