package cpm.web.utils.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.restfb.json.JsonObject;
import cpm.web.service.facebook.FbServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ConvertUtils {

    private static final Logger log = LoggerFactory.getLogger(FbServiceImpl.class);

    public static <T> T convertJsonToObject(List<JsonObject> jsonObjectList, Class<T> classList) {
        return handle(jsonObjectList, classList);
    }

    public static <T> T convertJsonToObject(JsonObject jsonObject, Class<T> classList) {
        List<JsonObject> jsonObjectList = new ArrayList<JsonObject>() {{
            add(jsonObject);
        }};
        return handle(jsonObjectList, classList);
    }

    public static String mergeJson(List<?> obj) {
        StringBuilder data = new StringBuilder();
        try {
            obj.forEach(item -> {
                Map<String, ArrayList<String>> map = (Map<String, ArrayList<String>>) item;
                Iterator<Map.Entry<String, ArrayList<String>>> itr = map.entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, ArrayList<String>> entry = itr.next();

                    if (entry.getKey().equals("data")) {
                        String dataJson = "";
                        if (entry.getValue().size() > 0) {
                            entry.getValue().forEach(item1 -> {
                                data.append(",");
//                                dataJson = new Gson().toJson(item1);
                            });

                        }
                        if (data.length() != 0) {
                            data.append(",");
                        }
                        data.append(dataJson);
                    }
                }
            });

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return new Gson().toJson(data);
    }

    private static <T> T handle(List<JsonObject> jsonObjectList, Class<T> classList) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> lists = new ArrayList<>();
        jsonObjectList.forEach(item -> {
            try {
                lists.add(
                        objectMapper.readValue(item.toString(), classList)
                );
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.error("Mapper object error ", e);
                throw new RuntimeException(e);
            }
        });
        if (lists.size() > 1) {
            return (T) lists;
        } else {
            return lists.get(0);
        }
    }
}

