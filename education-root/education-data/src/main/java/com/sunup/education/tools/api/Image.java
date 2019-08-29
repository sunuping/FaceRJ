package com.sunup.education.tools.api;

import lombok.Builder;
import lombok.Data;

/**
 * @author lime
 */
@Data
@Builder
public class Image {
    public static final String FACE_TOKEN = "FACE_TOKEN";
    public static final String URL = "URL";
    public static final String BASE64 = "BASE64";

    /**
     * 图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断；
     * 可以上传同一个用户的1张、3张或8张图片来进行活体判断，注：后端会选择每组照片中的最高分数作为整体分数。
     */
    private String image;
    /**
     * 图片类型
     * BASE64:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；
     * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     * FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
     */
    private String image_type;
    /**
     *  包括age,beauty,expression,face_shape,gender,glasses,landmark,race,quality,face_type信息，逗号分隔，默认只返回face_token、活体数、人脸框、概率和旋转角度
     */
    private String face_field;
}
