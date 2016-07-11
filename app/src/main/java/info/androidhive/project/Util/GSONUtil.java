package info.androidhive.project.Util;

import android.util.Log;

import info.androidhive.project.model.*;
import info.androidhive.project.ReturnCode.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by VietAnh on 5/17/2016.
 */
public class GSONUtil
{
    /**
     * Convert Object to JSON user GSON
     * @param object
     * @return
     */
    public static String convertElementToJSON(Elements object){
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Elements>(){}.getType();


            String json = gson.toJson(object, type);
            return json;
        }catch (Exception e){
            Log.d(Default.LOG_TAG, e.getMessage());
        }

        return null;
    }

    /**
     * Convert JSON to Elements <Danh sach Post hien thi tren trang home></Danh>
     * @param json
     * @return
     */
    public static Elements convertJSONToElements(String json){
        try {
            Gson gson1 = new Gson();
            Elements user1 = gson1.fromJson(json, Elements.class);

            return user1;
        }catch (Exception e){
            Log.d(Default.LOG_TAG, e.getMessage());
        }

        return null;
    }

    public static Info convertJSONToInfoTag(String json) {
        try {
            Gson gson1 = new Gson();
            Info info = gson1.fromJson(json, Info.class);
            return info;
        } catch (Exception e) {
            Log.d(Default.LOG_TAG, e.getMessage());
        }

        return null;
    }

    /**
     * Convert JSON to Elements <Danh sach Post hien thi tren trang home></Danh>
     *
     * @param json
     * @return
     */
    public static TagPage convertJSONToTagPage(String json) {
        try {
            int positionElements = ProcessUtil.getPositionSubString(json, "\"elements\"");
            int positonInfoTag = ProcessUtil.getPositionSubString(json, "\"infoTag\"");

            String strElements = "{" + json.substring(positionElements, positonInfoTag - 1) + "}";
            String strInfoTag = "{" + json.substring(positonInfoTag + 11, json.length() - 3);

            TagPage tagPage = new TagPage();
            Elements elements = convertJSONToElements(strElements);

            Info info = convertJSONToInfoTag(strInfoTag);
            Log.d("============>", strInfoTag);
            tagPage.setElements(elements);
            tagPage.setInfo(info);

            return tagPage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] abc) {
        String abs = "{\"idTag\":\"123456\",\"tag\":\"null\",\"srcImg\":\"null\",\"desc\":\"null\",\"uptime\":\"null\",\"place\":\"null\"}";
        Gson gson = new Gson();

        Info test = new Info();
        test.setIdTag("123132");
        test.setTag("123");
        test.setSrcImg("12");
        test.setDesc("12");
        test.setUptime("1231");
        test.setPlace("131");

        Info info = convertJSONToInfoTag(abs);
        System.out.print(gson.toJson(test));
        System.out.print(info.getIdTag());
    }
}
