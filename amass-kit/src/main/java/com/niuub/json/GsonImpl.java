package com.niuub.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.niuub.utils.date.DateStyle;
import com.niuub.utils.date.DateUtils;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * gson 实例
 * <p>
 * Created by mapsh on 15/11/5.
 */
class GsonImpl extends Json {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            //序列化
            .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                    //设置发送到后台的日期为字符串，格式：yyyy-MM-dd HH:mm:ss
                    return new JsonPrimitive(DateUtils.dateToString(src, "yyyy-MM-dd HH:mm:ss"));
                }
            })
            //反序列化
            .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return json == null ? null : DateUtils.stringToDate(json.getAsString(),DateStyle.YYYY_MM_DD_HH_MM_SS);
                }
            })
            //解决Integer默认转换成Double的问题
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue()) {
                        return new JsonPrimitive(aDouble.longValue());
                    }
                    return new JsonPrimitive(aDouble);
                }
            })
            .create();

    @Override
    public Gson gsonImpl() {
        return gson;
    }

    @Override
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    @Override
    public <T> T toObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @Override
    public <T> T toObject(String json, Type type) {
        return gson.fromJson(json, type);
    }

}
