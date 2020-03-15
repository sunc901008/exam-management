package com.exam.exception.msg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunc
 * @date 2020/3/8 13:52
 */
@Getter
@Setter
public class DependentMsg {
    private JSONArray names;

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("names", names);
        return json;
    }
}
