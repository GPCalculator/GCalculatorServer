package com.mccspace.hs.service.game.gameplayer;

import java.io.File;

/**
 * GamePlayerÀà
 *
 * @TIME 2020/5/25
 * @AUTHOR º«Ë¶~
 */

public interface GamePlayer {

    String getName();
    File getHead();
    int getGameNum();
    int getWinNum();
    int getFailNum();
    int getDrawNum();

}
