package info.androidhive.project.WebService;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;*/

/**
 * Created by VietAnh on 5/16/2016.
 */
public class Rest {
    private String result = "";


    /*****************************************************/
    /*                    Function asyncResponse        */
    /****************************************************/
    /**
     * GET DATA from url
     * @param url
     * @return
     */
    public String asyncResponse(String url){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);
            return getStringFromHttpResponse(response);
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getMessage());
        }
        return null;
    }

    /*****************************************************/
    /*                    Function receiveResponse       */
    /****************************************************/
    /**
     * get Response
     * @param url
     * @param params
     * @return
     */
    public static String receiveResponse(String url, Map params)
    {
        try {
            //instantiates httpclient to make request
            DefaultHttpClient httpclient = new DefaultHttpClient();

            //url with the post data
            HttpPost httpost = new HttpPost(url);

            //convert parameters into JSON object
            JSONObject holder = getJsonObjectFromMap(params);

            //passes the results to a string builder/entity
            StringEntity se = new StringEntity(holder.toString());

            //sets the post request as the resulting string
            httpost.setEntity(se);
            //sets a request header so the page receving the request
            //will know what to do with it
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");

            return getStringFromHttpResponse(httpclient.execute(httpost));
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    /*****************************************************/
    /*         Function  getJsonObjectFromMap            */
    /****************************************************/
    /**
     * Convert HashMap to JsonObject
     * @param params
     * @return
     * @throws JSONException
     */
    private static JSONObject getJsonObjectFromMap(Map params) throws JSONException {

        //all the passed parameters from the post request
        //iterator used to loop through all the parameters
        //passed in the post request
        Iterator iter = params.entrySet().iterator();

        //Stores JSON
        JSONObject holder = new JSONObject();

        //using the earlier example your first entry would get email
        //and the inner while would get the value which would be 'foo@bar.com'
        //{ fan: { email : 'foo@bar.com' } }

        //While there is another entry
        while (iter.hasNext())
        {
            //gets an entry in the params
            Map.Entry pairs = (Map.Entry)iter.next();

            //creates a key for Map
            String key = (String)pairs.getKey();

            //Create a new map
            Map m = (Map)pairs.getValue();

            //object for storing Json
            JSONObject data = new JSONObject();

            //gets the value
            Iterator iter2 = m.entrySet().iterator();
            while (iter2.hasNext())
            {
                Map.Entry pairs2 = (Map.Entry)iter2.next();
                data.put((String)pairs2.getKey(), (String)pairs2.getValue());
            }

            //puts email and 'foo@bar.com'  together in map
            holder.put(key, data);
        }
        return holder;
    }


    private static String getStringFromHttpResponse(HttpResponse response){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                Log.d("=====>", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.d("JSON", "Failed to read file with stutus code " + statusCode);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }
}
