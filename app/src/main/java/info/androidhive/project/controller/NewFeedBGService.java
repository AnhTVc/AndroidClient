package info.androidhive.project.controller;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import info.androidhive.project.WebService.*;
import info.androidhive.project.model.*;
import info.androidhive.project.ReturnCode.*;
import info.androidhive.project.Util.*;



public class NewFeedBGService extends Service {
    private static Rest restAPI = new Rest();
    private Elements elements;

    Config config = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("==================>", "onStartCommand");
        config = new Config(getApplicationContext());
        Toast.makeText(getApplicationContext(), "Check New Feed", Toast.LENGTH_SHORT).show();
        checkNewFeed();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        //Xoa file config
        config.remove();
        Log.d(Default.CHECK_NEW_FEED, " destroy");

        super.onDestroy();
    }

    /********************************************************/
    /*          Service Check New Feed                      */
    /*******************************************************/
    private void checkNewFeed(){
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            /****Hien dang fix la GET  */
            return restAPI.asyncResponse(Default.WSURL + "element");
        }

        @Override
        protected void onPostExecute(String result) {
            processValue(result);
        }

    }

    private void processValue(String data){
        if(data != null){
            try {
                elements = GSONUtil.convertJSONToElements(data);
                if(elements.getElements().size() > 0){
                    //Luu file config da co new feed
                    config.setIsHaveNewFeed(true);
                }
            }catch (Exception e){
                e.getStackTrace();
            }

            //Luu file config chua co data

        }
    }

    /****************************************/
    /*      Auto Check new feed             */
    /****************************************/

}

