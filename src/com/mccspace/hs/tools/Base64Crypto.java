package com.mccspace.hs.tools;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

import java.io.*;

/**
 * Base64Crypto类
 * Git to： http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/6/4 14:50
 * @AUTHOR 韩硕~
 */

public class Base64Crypto {

    /**
     * <p>将文件转成base64 字符串</p>
     *
     * @param path 文件路径
     */
    public static String encodeBase64File(File file) {
        try {
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>将base64字符解码保存文件</p>
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * <p>将base64字符保存文本文件</p>
     */
    public static void toFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

}
