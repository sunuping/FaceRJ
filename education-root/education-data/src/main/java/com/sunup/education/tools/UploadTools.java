package com.sunup.education.tools;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lime
 */
@Slf4j
public class UploadTools {
    private static Logger logger = LoggerFactory.getLogger(UploadTools.class);

    private static final String IMG_PNG = "image/png";
    private static final String IMG_JPG = "image/jpg";

    /**
     * 获取项目地址
     *
     * @return
     */
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    public static String getProjectClassesPath() {
        String url = UploadTools.class.getResource("/").getPath().substring(6).replaceAll("!", "");
        logger.info(url);
        return url;
    }

    /**
     * 限制文件 图片的格式
     *
     * @return
     */
    public static boolean isImg(String contentType) {
        switch (contentType) {
            case IMG_JPG:
                return true;

            case IMG_PNG:
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * 上传个文件
     * 最大文件大小 10m
     *
     * @param multipartFile 文件
     * @param path          路径
     * @return 键值对的文件信息
     */
    public static ConcurrentMap<String, Object> singleUpload(MultipartFile multipartFile, String path) {
        ConcurrentMap<String, Object> data = null;
        String filename = "";
        String filepath = "";
        File temp = null;
        if (multipartFile != null) {
            try {
                filename = StringTools.bufferAppend(RandomTools.uuid(), ".", multipartFile.getContentType().split("/")[1]).toString();
                filepath = path;
                temp = new File(filepath);
                write(multipartFile, filename, filepath, temp);
                data = new ConcurrentHashMap<>(1);
                data.put("filename", filename);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return data;
    }

    private static void write(MultipartFile multipartFile, String filename, String filepath, File temp) throws IOException {
        @Cleanup BufferedOutputStream out = null;
        if (!temp.isDirectory()) {
            //如果目录不存在 则新建一个
            temp.mkdirs();
            logger.info("创建路径:" + filepath);
        }
        out = new BufferedOutputStream(new FileOutputStream(new File(StringTools.bufferAppend(filepath, filename).toString())));
        out.write(multipartFile.getBytes());
        out.flush();
    }

    /**
     * 上传多个文件
     * 最大文件大小 10m
     *
     * @param multipartFiles 文件数组
     * @param path           路径
     * @return 键值对的文件信息
     */
    public static ConcurrentMap<String, Object> multipartUpload(MultipartFile[] multipartFiles, String path) {
        ConcurrentMap<String, Object> data = null;
        List<String> list = new ArrayList<>();
        String filename = "";
        String filepath = "";
        File temp = null;
        if (multipartFiles.length > 0) {
            try {
                for (MultipartFile file : multipartFiles) {
                    filename = StringTools.bufferAppend(RandomTools.uuid(), ".", file.getContentType().split("/")[1]).toString();
                    //获取绝对文件路径
                    filepath = path;
                    logger.info(filepath);
                    temp = new File(filepath);
                    logger.info(temp.isDirectory() + "");
                    write(file, filename, filepath, temp);
                    list.add(filename);
                }
                data = new ConcurrentHashMap<>(1);
                data.put("filenames", list);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return data;
    }


    public static String signleUpload(String imgbase64, String path) {
        return Base64AndImgConverTools.converImg(imgbase64, path) ? path : "";
    }

    public static void main(String[] args) throws Exception {

        System.out.println(getProjectPath());

    }


    public static String download(String filename, String filepath, HttpServletResponse response) {
        //强制下载
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        try {
            File file = new File(filepath);
            @Cleanup FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            @Cleanup BufferedInputStream bis = new BufferedInputStream(fis);
            log.info(bis + "");
            @Cleanup OutputStream os = response.getOutputStream();
            int i = bis.read(bytes);
            while (i != -1) {
                os.write(bytes, 0, i);
                i = bis.read(bytes);
            }
            return "下载成功";
        } catch (IOException ioe) {
            log.error("下载文件异常 ", ioe);
        }
        return "下载失败";


    }
}
