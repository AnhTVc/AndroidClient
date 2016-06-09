package info.androidhive.project.model;

import android.content.Context;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import info.androidhive.project.ReturnCode.Default;

/**
 * Created by VietAnh on 6/8/2016.
 */
public class Config {
    private Boolean isHaveNewFeed = false;
    Properties properties = new Properties();
    String pathConfig = "";

    public Config(Context context){
        //Check file config
        try {
            String path = context.getFilesDir().getAbsolutePath() + File.separator + "config.properties";
            pathConfig = path;
            File file = new File(path);
            if(!file.exists()){
                //Khong co file


                //Set data
                properties.setProperty("NewFeed", "false");
                file.createNewFile();
                properties.store(new FileOutputStream(file), "This is option for new feed");
            }else {
                //File da ton tai
                properties.load(new FileInputStream(file));
                if(properties.getProperty("NewFeed").equals("false")){
                    //Chua co data
                    isHaveNewFeed = false;
                }else {
                    //Da co new feed
                    isHaveNewFeed = true;
                }

            }
        }catch (Exception e)
        {
            Log.d(Default.LOG_TAG, e.getMessage());
        }
    }

    public Boolean getIsHaveNewFeed(){
        return isHaveNewFeed;
    }

    //Update file config
    public void setIsHaveNewFeed(Boolean isHaveNewFeed){
        if(isHaveNewFeed){
            properties.getProperty("NewFeed").equals("true");
            try {
                properties.store(new FileOutputStream(pathConfig), "This have new feed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.isHaveNewFeed = isHaveNewFeed;
    }

    public boolean remove(){
        try {
            File file = new File(pathConfig);
            if(file.exists()){
                file.delete();
                return true;
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return false;


    }
}
