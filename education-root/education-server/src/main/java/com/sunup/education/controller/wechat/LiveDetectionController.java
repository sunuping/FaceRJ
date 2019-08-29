package com.sunup.education.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.sunup.education.tools.Base64AndImgConverTools;
import com.sunup.education.tools.StringTools;
import com.sunup.education.tools.UploadTools;
import com.sunup.education.tools.VideoTools;
import com.sunup.education.tools.api.BaiduApiTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author janly
 * 活体检测
 * 人脸识别
 */
@Controller
@Slf4j
public class LiveDetectionController {

    @Value("${upload-path}")
    private String path;

    /**
     * 活体检测页面
     *
     * @return
     */
    @GetMapping("faceRecognition")
    public String faceRecognition() {
        return "face-recognition";
    }

    /**
     * 活体检测页面
     *
     * @return
     */
    @GetMapping("onlineVideoFaceDetection")
    public String onlineVideoFaceDetection() {
        return "online-video-face-detection";
    }

    @PostMapping("onlineVideoFaceDetectionData")
    @ResponseBody
    public String onlineVideoFaceDetectionData(MultipartFile file) {
        String filename = UploadTools.singleUpload(file, path).get("filename").toString();
//        String filename = "";
        JSONObject res = null;
        try {
            String videpath = StringTools.bufferAppendToString(path, filename);
            List<String> filenames = VideoTools.getVideoPic(new File(videpath), path);
            if (filenames != null && filenames.size() > 0) {
                int i = 0, len = filenames.size();
                res = getJsonObject(path, filenames, i, len);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return res.toJSONString();
    }

    private JSONObject getJsonObject(String dedirectoryPath, List<String> filenames, int i, int len) {
        String image = Base64AndImgConverTools.converBase64(new File(StringTools.bufferAppendToString(dedirectoryPath, filenames.get(i++))));
        JSONObject res = BaiduApiTools.onlineLivingDetection(image);
        if (i < len) {
            res = getJsonObject(dedirectoryPath, filenames, i, len);
        }
        return res;
    }


    @PostMapping("faceRecognitionData")
    @ResponseBody
    public JSONObject faceRecognitionData(String img_base64) {
        return BaiduApiTools.onlineLivingDetection(img_base64);
    }

}
