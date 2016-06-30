package info.androidhive.project.activity.Task;

import android.os.AsyncTask;

import info.androidhive.project.ReturnCode.Default;

/**
 * Created by VietAnh on 6/28/2016.
 */
public class AsyncTaskImlement extends AsyncTask<String, Void, String> {
    private Exception exception;

    @Override
    protected String doInBackground(String... urls) {
        try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
