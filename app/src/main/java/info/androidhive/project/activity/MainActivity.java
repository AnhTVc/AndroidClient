package info.androidhive.project.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import info.androidhive.project.R;
import info.androidhive.project.WebService.Rest;

import info.androidhive.project.adapter.ElementAdapter;
import info.androidhive.project.model.*;
import info.androidhive.project.ReturnCode.*;
import info.androidhive.project.Util.*;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    public static int position = 0;
    private Elements elements, elementsNew;
    Rest restAPI = new Rest();

    ArrayList<Element> arrayOfElement = new ArrayList<Element>();
    ElementAdapter adapter = null;
    ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        adapter = new ElementAdapter(this, arrayOfElement);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container_body);
        //Get data frome server
        new HttpRequestTask().execute();
        /*****************************/
        /*      Check new feed      */
        /***************************/
        scheduleTaskExecutor.scheduleWithFixedDelay(new Runnable() {
                                                        public void run() {
                                                            Log.i(Default.CHECK_NEW_FEED, "Have check New feed");
                                                            runOnUiThread(new Runnable() {
                                                                public void run() {
                                                                    new HttpNewFeedTask().execute();

                                                                }
                                                            });
                                                        }
                                                    }, Default.TIME_TO_CALL_SERVICE_NEW_FEED,
                Default.TIME_TO_CALL_SERVICE_NEW_FEED, TimeUnit.MINUTES);//10 phút

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }

    @Override
    protected void onStop(){
        scheduleTaskExecutor.shutdown();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        scheduleTaskExecutor.shutdown();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Get the Camera instance as the activity achieves full user focus
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        //TODO something
        //displayView(position);
    }

    //Service gầm gọi đến webservice để lấy dữ liệu,
    // Mỗi lần load 10 phần tử
    //
    private class HttpRequestTask extends AsyncTask<Void, Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            /****Hien dang fix la GET  */
            position = position + 1;
            return restAPI.asyncResponse(Default.WSURL + "element");
        }

        @Override
        protected void onPostExecute(String result) {
            processValue(result);
        }

    }

    private void processValue(String data){
        if(data != null){
            ListView listView = (ListView) findViewById(R.id.listView);
            // Attach the adapter to a ListView

            if(listView.getAdapter() == null){
                elements = GSONUtil.convertJSONToElements(data);
                for(int i =0; i< elements.getElements().size(); i++){
                    adapter.add(elements.getElements().get(i));
                }
                listView.setAdapter(adapter);
            }else {
                elements = GSONUtil.convertJSONToElements(data);
                for(int i =0; i< elements.getElements().size(); i++){
                    adapter.add(elements.getElements().get(i));
                    adapter.notifyDataSetChanged();
                }
            }

            listView.setOnScrollListener(new EndlessScrollListener());
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //Refresh content
                    refreshContent();
                }
            });
        }
    }

    private void refreshContent() {
        Log.d("Have refresh Content", "");
        swipeRefreshLayout.setRefreshing(false);
    }

    /*******************************************/
    /*      Endless ListView                   */

    /*******************************************/
    public class EndlessScrollListener implements AbsListView.OnScrollListener {

        private int currentPage = 0;
        private int previousTotal = 0;
        private boolean loading = true;

        public EndlessScrollListener() {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {


            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                    currentPage++;
                }
            }
            if (!loading && (visibleItemCount + firstVisibleItem) == totalItemCount ) {
                // I load the next page of gigs using a background task,
                // but you can call any function here.
                new HttpRequestTask().execute();
                loading = true;
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

    }
    /************************************************/
    /*      Call Service thread check new Feed      */
    /************************************************/
    private class HttpNewFeedTask extends AsyncTask<Void, Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            /****Hien dang fix la GET  */
            Log.d("=======>", "CHECK NEW FEED");
            return restAPI.asyncResponse(Default.WSURL + "element");
        }

        @Override
        protected void onPostExecute(String result) {
            processValueNew(result);
        }

    }

    private void processValueNew(String data){
        if(data != null){
            Toast.makeText(this,"New Feed",Toast.LENGTH_SHORT).show();
        }
    }

}