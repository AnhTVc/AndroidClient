package info.androidhive.project.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
        //Get data frome server
        new HttpRequestTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        }
    }

    /*******************************************/
    /*      Endless ListView                   */
    /******************************************/
    public class EndlessScrollListener implements AbsListView.OnScrollListener {

        private int visibleThreshold = 5;
        private int currentPage = 0;
        private int previousTotal = 0;
        private boolean loading = true;

        public EndlessScrollListener() {
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
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
            //if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
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
}