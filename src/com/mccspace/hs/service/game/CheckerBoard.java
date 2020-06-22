package com.mccspace.hs.service.game;

import com.mccspace.hs.tools.Email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CheckerBoardÀà
 * Git to£º http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/7 13:29
 * @AUTHOR º«Ë¶~
 */

public record CheckerBoard(long notEmote, long isBlack, long isKing, boolean blackPlay) {

    public static CheckerBoard newBoard() {
        return new CheckerBoard(0b11111111111111111111000000000011111111111111111111L, 0b11111111111111111111L, /*0b100000000000000000L*/0L, true);
    }

    public ChessType getChess(int n) {
        if (n < 0 || n > 49)
            return ChessType.Bar;
        if ((notEmote >> n) % 2 != 0) {
            if ((isBlack >> n) % 2 == 0) {
                if ((isKing >> n) % 2 == 0)
                    return ChessType.White;
                else
                    return ChessType.WhiteKing;
            } else {
                if ((isKing >> n) % 2 == 0)
                    return ChessType.Black;
                else
                    return ChessType.BlackKing;
            }
        } else {
            if ((isBlack >> n) % 2 == 0) {
                return ChessType.Emote;
            } else {
                return ChessType.Bar;
            }
        }
    }

    public ChessType getChess(int x,int y){
        return getChess(turnBoardCode(x,y));
    }

    public CheckerBoard turnType(int from, ChessType to) {
        long ne = notEmote;
        long ib = isBlack;
        long ik = isKing;
        if (to == ChessType.Emote || to == ChessType.Bar)
            ne &= ~(1L << from);
        else
            ne |= 1L << from;
        if (to == ChessType.BlackKing || to == ChessType.Black || to == ChessType.Bar)
            ib |= 1L << from;
        else
            ib &= ~(1L << from);
        if (to == ChessType.BlackKing || to == ChessType.WhiteKing)
            ik |= 1L << from;
        else
            ik &= ~(1L << from);
        return new CheckerBoard(ne, ib, ik, blackPlay);
    }

    @Override
    public CheckerBoard clone() {
        return new CheckerBoard(notEmote, isBlack, isKing, blackPlay);
    }

    public List<List<Integer>> getProbably() {
        List<List<Integer>> a = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if (getChess(i).isMe(blackPlay)) {
                var nowHistory = new ArrayList<Integer>(5);
                nowHistory.add(i);
                getNextJump(a, nowHistory, i);
            }
        }
        if (a.size() == 0) {
            for (int i = 0; i < 50; i++) {
                if (blackPlay && getChess(i).isBlack()) {
                    if(!getChess(i).isKing()) {
                        if (getChess(getFX(i, -1, 1)) == ChessType.Emote) {
                            List<Integer> z = new ArrayList<>();
                            z.add(i);
                            z.add(getFX(i, -1, 1));
                            a.add(z);
                        }
                        if (getChess(getFX(i, 1, 1)) == ChessType.Emote) {
                            List<Integer> z = new ArrayList<>();
                            z.add(i);
                            z.add(getFX(i, 1, 1));
                            a.add(z);
                        }
                    } else {
                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, -t, t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, -t, t));
                                a.add(z);
                            } else break;
                        }
                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, t, t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, t, t));
                                a.add(z);
                            } else break;
                        }

                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, -t, -t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, -t, -t));
                                a.add(z);
                            } else break;
                        }
                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, t, -t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, t, -t));
                                a.add(z);
                            } else break;
                        }
                    }
                } else if (!blackPlay && getChess(i).isWhite()) {
                    if(!getChess(i).isKing()) {
                        if (getChess(getFX(i, 1, -1)) == ChessType.Emote) {
                            List<Integer> z = new ArrayList<>();
                            z.add(i);
                            z.add(getFX(i, 1, -1));
                            a.add(z);
                        }
                        if (getChess(getFX(i, -1, -1)) == ChessType.Emote) {
                            List<Integer> z = new ArrayList<>();
                            z.add(i);
                            z.add(getFX(i, -1, -1));
                            a.add(z);
                        }
                    } else {
                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, -t, t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, -t, t));
                                a.add(z);
                            } else break;
                        }
                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, t, t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, t, t));
                                a.add(z);
                            } else break;
                        }

                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, -t, -t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, -t, -t));
                                a.add(z);
                            } else break;
                        }
                        for(int t=1;t<9;t++) {
                            if (getChess(getFX(i, t, -t)) == ChessType.Emote) {
                                List<Integer> z = new ArrayList<>();
                                z.add(i);
                                z.add(getFX(i, t, -t));
                                a.add(z);
                            } else break;
                        }
                    }
                }
            }
        }
        List<List<Integer>> toKingRoad = new ArrayList<>();
        int max = 1;
        for (var i : a) {
            if (i.size() > max)
                max = i.size();
            for (int j = 1; j < i.size(); j++) {
                if (((i.get(j) > 44 && blackPlay) || (i.get(j) < 5 && !blackPlay)) && !getChess(i.get(0)).isKing()) {
                    List<Integer> road = new ArrayList<>();
                    for (int k = 0; k <= j; k++) {
                        road.add(i.get(k));
                    }
                    toKingRoad.add(road);
                }
            }
        }
        Iterator<List<Integer>> iterator = a.iterator();
        while (iterator.hasNext()) {
            List<Integer> i = iterator.next();
            if (i.size() < max) {
                iterator.remove();
            }
        }
        a.addAll(toKingRoad);
        return a;
    }

    private void getNextJump(List<List<Integer>> road, List<Integer> nowHistory, int now) {
        boolean isKing = getChess(now).isKing();
        boolean isEnd = true;
        if (isKing) {
            if (!getNextJumpKingToNone(now, road, nowHistory, -1, -1))
                isEnd = false;
            if (!getNextJumpKingToNone(now, road, nowHistory, 1, -1))
                isEnd = false;
            if (!getNextJumpKingToNone(now, road, nowHistory, -1, 1))
                isEnd = false;
            if (!getNextJumpKingToNone(now, road, nowHistory, 1, 1))
                isEnd = false;
        } else {
            if (getChess(getFX(now, -1, -1)).differentTeam(getChess(now)) && getChess(getFX(now, -2, -2)) == ChessType.Emote) {
                List<Integer> n = (List<Integer>) ((ArrayList) nowHistory).clone();
                n.add(getFX(now, -2, -2));
                turnType(getFX(now, -2, -2), getChess(now)).turnType(getFX(now, -1, -1), ChessType.Bar).turnType(now, ChessType.Emote).getNextJump(road, n, getFX(now, -2, -2));
                isEnd = false;
            }
            if (getChess(getFX(now, 1, -1)).differentTeam(getChess(now)) && getChess(getFX(now, 2, -2)) == ChessType.Emote) {
                List<Integer> n = (List<Integer>) ((ArrayList) nowHistory).clone();
                n.add(getFX(now, 2, -2));
                turnType(getFX(now, 2, -2), getChess(now)).turnType(getFX(now, 1, -1), ChessType.Bar).turnType(now, ChessType.Emote).getNextJump(road, n, getFX(now, 2, -2));
                isEnd = false;
            }
            if (getChess(getFX(now, -1, 1)).differentTeam(getChess(now)) && getChess(getFX(now, -2, 2)) == ChessType.Emote) {
                List<Integer> n = (List<Integer>) ((ArrayList) nowHistory).clone();
                n.add(getFX(now, -2, 2));
                turnType(getFX(now, -2, 2), getChess(now)).turnType(getFX(now, -1, 1), ChessType.Bar).turnType(now, ChessType.Emote).getNextJump(road, n, getFX(now, -2, 2));
                isEnd = false;
            }
            if (getChess(getFX(now, 1, 1)).differentTeam(getChess(now)) && getChess(getFX(now, 2, 2)) == ChessType.Emote) {
                List<Integer> n = (List<Integer>) ((ArrayList) nowHistory).clone();
                n.add(getFX(now, 2, 2));
                turnType(getFX(now, 2, 2), getChess(now)).turnType(getFX(now, 1, 1), ChessType.Bar).turnType(now, ChessType.Emote).getNextJump(road, n, getFX(now, 2, 2));
                isEnd = false;
            }
        }
        if (isEnd && nowHistory.size() > 1)
            road.add(nowHistory);
    }

    private boolean getNextJumpKingToNone(int now, List<List<Integer>> road, List<Integer> nowHistory, int fx, int fy) {
        int t = now;
        boolean isEnd = true;
        while ((t = getFX(t, fx, fy)) > 0) {
            if (getChess(t).differentTeam(getChess(now))) {
                int en = t;
                while ((t = getFX(t, fx, fy)) > 0) {
                    if (getChess(t) == ChessType.Emote) {
                        List<Integer> n = (List<Integer>) ((ArrayList) nowHistory).clone();
                        n.add(t);
                        turnType(t, getChess(now)).turnType(en, ChessType.Bar).turnType(now, ChessType.Emote).getNextJump(road, n, t);
                        isEnd = false;
                    } else
                        break;
                }
                break;
            } else if (getChess(t) == ChessType.Emote)
                continue;
            else
                break;
        }
        return isEnd;
    }

    public CheckerBoard play(List<Integer> road) {
        CheckerBoard now = this;
        for (int i = 1; i < road.size(); i++) {
            int endY = getY(road.get(i));
            int startY = getY(road.get(i - 1));
            int endX = getX(road.get(i));
            int startX = getX(road.get(i - 1));
            for (int j = startX; (startX > endX) ? j > endX : j < endX; ) {
                if (startX > endX)
                    j--;
                else
                    j++;
                now = now.turnType(turnBoardCode(j, (startY > endY) ? --startY : ++startY), ChessType.Emote);
            }
        }
        now = now.turnType(road.get(road.size() - 1), getChess(road.get(0)));

        now = now.turnType(road.get(0), ChessType.Emote);
        now = now.turnTeam();
        if (!getChess(road.get(0)).isKing() && (((road.get(road.size() - 1)) < 5 && !blackPlay) || ((road.get(road.size() - 1)) > 44 && blackPlay)))
            now = now.turnType(road.get(road.size() - 1), (getChess(road.get(0)) == ChessType.Black) ? ChessType.BlackKing : ChessType.WhiteKing);
        return now;
    }

    public CheckerBoard turnTeam() {
        return new CheckerBoard(notEmote, isBlack, isKing, !blackPlay);
    }

    public static int getFX(int now, int x, int y) {
        int ny = (int) (now / 5);
        int nx = (now - ny * 5) * 2 + ((ny % 2) == 0 ? 1 : 0);
        if (ny + y > 9 || ny + y < 0 || nx + x > 9 || nx + x < 0)
            return -1;
        return (ny + y) * 5 + (int) ((nx + x) / 2);
    }

    public static int turnBoardCode(int x, int y) {
        return y * 5 + (int) (x / 2);
    }

    public static int getX(int now) {
        int ny = (int) (now / 5);
        return (now - ny * 5) * 2 + ((ny % 2) == 0 ? 1 : 0);
    }

    public static int getY(int now) {
        return (int) (now / 5);
    }

    @Override
    public int hashCode() {
        int result = (int) (notEmote ^ (notEmote >>> 32));
        result = 31 * result + (int) (isBlack ^ (isBlack >>> 32));
        result = 31 * result + (int) (isKing ^ (isKing >>> 32));
        result = 31 * result + (blackPlay ? 1 : 0);
        return result;
    }
}
