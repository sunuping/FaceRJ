package com.sunup.education.tools;

import com.alibaba.fastjson.JSONObject;
import com.sunup.education.tools.api.BaiduApiTools;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.sunup.education.tools.PrintTools.print;

public class Base64 {
    /**
     * <p>将文件转成base64 字符串</p>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * <p>将base64字符解码保存文件</p>
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * <p>将base64字符保存文本文件</p>
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void toFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static void main(String[] args) {
        try {
            String base64Code = encodeBase64File("E:\\janly\\upload\\363d4a5c-121e-43a5-ac99-a9c53a62bdf1.mp4");
            JSONObject res = BaiduApiTools.onlineLivingVideoDetection(base64Code);
            print(res.toString());
//            decoderBase64File(base64Code, "/Users/Crazy/Desktop/zyb.png");
//            toFile(base64Code, "/Users/Crazy/Desktop/zyb.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
