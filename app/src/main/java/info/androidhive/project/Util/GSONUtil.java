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
}
