package com.sunup.education.service.i;

import com.alibaba.fastjson.JSONObject;
import com.sunup.education.model.Msg;

public interface IFaceService {
    JSONObject initFaceSet();

    JSONObject addface(String faceset_token, String face_tokens);

    JSONObject removeface(String faceset_token, String face_tokens);

    JSONObject create();

    JSONObject getfacesets();

    JSONObject detect(String url);

    Msg compare(String url);

    Msg search(String image_base64, String faceset_token);
}
