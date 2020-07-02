package com.mccspace.hs.service.listen;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

/**
 * ConnectIntyerface???
 *
 * @author HanShuo
 * @Date 2020/5/1 17:16
 */
public interface ConnectIntyerface{

    boolean whetherRun(String connectName);
    void run(JSONObject data) throws IOException;
    boolean isPermanent();

    String getType();
}
