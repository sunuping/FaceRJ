package com.sunup.education.tools;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

@Slf4j
public class Base64AndImgConverTools {

    /**
     * 图片转化成base64字符串
     *
     * @return
     */
    public static String converBase64(File imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream inputStream = null;
        byte[] data = null;
        try {
            //读取图片字节数组
            inputStream = new FileInputStream(imgFile);
            //available() 该方法返回可估算从这个输入流中可无阻塞读取剩余的字节数
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


    public static Boolean converImg(String imgBase64, String path) {
        if (imgBase64 == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(imgBase64);
            for (int i = 0, len = bytes.length; i < len; i++) {
                if (bytes[i] < 0) {
                    //调整异常数据
                    bytes[i] += 256;
                }
            }
            OutputStream outputStream = new FileOutputStream(path);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Base64AndImgConverTools.converBase64(new File(""));
    }

}
