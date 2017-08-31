package mx.com.aries.utils;


import com.google.gson.Gson;

public class JSONUtil {
    private static final Gson gson = new Gson();
    public static <T> T fromJSON(String json,Class<T> classtype) {
        T parsedObject = gson.fromJson(json, classtype);
        return parsedObject;
    }
}
