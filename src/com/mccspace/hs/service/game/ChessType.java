package com.mccspace.hs.service.game;

/**
 * ChessTypeÀà
 *
 * @TIME 2020/6/7
 * @AUTHOR º«Ë¶~
 */

public enum ChessType {

    Emote,Black,White,BlackKing,WhiteKing,Bar;

    public boolean differentTeam(ChessType c){
        return teamCheck(c, White, WhiteKing, BlackKing, Black);
    }

    public boolean sameTeam(ChessType c){
        return teamCheck(c, Black, BlackKing, White, WhiteKing);
    }

    private boolean teamCheck(ChessType c, ChessType black, ChessType blackKing, ChessType white, ChessType whiteKing) {
        if(this.equals(Black) || this.equals(BlackKing))
            if(c.equals(black) || c.equals(blackKing))
                return true;
            else
                return false;
        else if(this.equals(White) || this.equals(WhiteKing))
            if(c.equals(white) || c.equals(whiteKing))
                return true;
            else return false;
        else
            return false;
    }

    public boolean isKing(){
        if(this.equals(BlackKing) || this.equals(WhiteKing))
            return true;
        return false;
    }

    public boolean isWhite(){
        if(this.equals(White) || this.equals(WhiteKing))
            return true;
        return false;
    }

    public boolean isBlack(){
        if(this.equals(BlackKing) || this.equals(Black))
            return true;
        return false;
    }

    public boolean isMe(boolean blackPlay){
        if(blackPlay)
            return isBlack();
        else
            return isWhite();
    }

    public boolean notEmote(){
        if(this.equals(Emote) || this.equals(Bar))
            return false;
        return true;
    }

}
