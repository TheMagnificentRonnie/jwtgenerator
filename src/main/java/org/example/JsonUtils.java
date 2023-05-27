//package org.example;
//
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class JsonUtils {
//  public static Map<String, Object> jsonToMap(String json) {
//    Map<String, Object> map = new HashMap<>();
//    JSONObject jsonObject = new JSONObject(json);
//
//    for (String key : jsonObject.keySet()) {
//      Object value = jsonObject.get(key);
//      map.put(key, value);
//    }
//
//    return map;
//  }
//}
