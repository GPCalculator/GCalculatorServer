package com.mccspace.hs.service.listen;

import java.io.PrintWriter;

/**
 * ConnectListn??
 *
 * @author HanShuo
 * @Date 2020/5/1 17:50
 */
public abstract class ConnectListen implements ConnectIntyerface {

    protected String Type;
    protected boolean always = false;

    public ConnectListen(String Type) {
        this.Type = Type;
    }
    public ConnectListen(String Type,boolean always) {
        this.Type = Type;
        this.always = always;
    }

    @Override
    public boolean whetherRun(String connectName) {
        if(Type.equals(connectName))
            return true;
        return false;
    }

    @Override
    public boolean isPermanent(){
        return always;
    }

    @Override
    public String getType() {
        return Type;
    }

    public boolean isAlways() {
        return always;
    }
}
