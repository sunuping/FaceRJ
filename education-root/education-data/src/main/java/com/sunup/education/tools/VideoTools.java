package com.sunup.education.tools;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author janly
 */
@Slf4j
public class VideoTools {
    /**
     * 获取视频时长，单位为秒
     *
     * @param video 源视频文件
     * @return 时长（s）
     */
    public static long getVideoDuration(File video) {
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            duration = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            log.error(e.getMessage(),e);
        }
        return duration;
    }

    /**
     * 截取视频获得指定帧的图片
     *
     * @param video 源视频文件
     */
    public static List<String> getVideoPic(File video, String directoryPath) {
        List<String> filenames = new ArrayList<>(3);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            // 截取中间帧图片(具体依实际情况而定)撒
            int i = 0;
            int length = ff.getLengthInFrames();
            //分三次截图
            int len1 = (int) (length * 0.1);
            int len2 = (int) (length * 0.5);
            int len3 = (int) (length * 0.9);
            Frame frame = null;
            for (int n = 0; n < length; n++) {
                //第三次截完图就退出
                if (n > len3) {
                    break;
                }
                if (n == len1 || n == len2 || n == len3) {
                    while (i < length) {
                        frame = ff.grabFrame();
                        if ((i > 5) && (frame.image != null)) {
                            // 截取的帧图片
                            Java2DFrameConverter converter = new Java2DFrameConverter();
                            BufferedImage srcImage = converter.getBufferedImage(frame);
                            int srcImageWidth = srcImage.getWidth();
                            int srcImageHeight = srcImage.getHeight();
                            // 对截图进行等比例缩放(缩略图)
                            int width = 480;
                            int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
                            //缩略图
                            BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                            thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
                            String filename = StringTools.bufferAppendToString(System.currentTimeMillis(), n, ".jpg");
                            File file = new File(StringTools.bufferAppendToString(directoryPath,filename));
                            ImageIO.write(thumbnailImage, "jpg", file);
                            filenames.add(filename);
                            break;
                        }
                        i++;
                    }
                }
            }
            ff.stop();
            ff.close();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return filenames;
    }

    public static void main(String[] args) {
        String videoPath = "D:\\Janly\\upload\\5d0e5115-fbfb-4b28-b1fb-86ee5538c767.mp4";
        File video = new File(videoPath);
        getVideoPic(video, "D:\\Janly\\upload\\");
        long duration = getVideoDuration(video);
        System.out.println("videoDuration = " + duration);
    }
}