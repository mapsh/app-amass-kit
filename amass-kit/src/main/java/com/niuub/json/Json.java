package com.niuub.json;

import com.google.gson.Gson;

/**
 * 封装gson单例模式
 * <p>
 * Created by mapsh on 15/11/5.
 */
public abstract class Json {

    private static volatile Json json;

    Json() {
    }

    /**
     * get default json handler
     *
     * @return Json
     */
    public static Json get() {
        if (null == json) {
            synchronized (Json.class) {
                if (null == json) {
                    json = new GsonImpl();
                }
            }
        }
        return json;
    }

    public abstract Gson gsonImpl();

    public abstract String toJson(Object src);

    public abstract <T> T toObject(String json, Class<T> clazz);

    public abstract <T> T toObject(String json, java.lang.reflect.Type type);

}
