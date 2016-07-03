package info.androidhive.project.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.ReturnCode.Default;
import info.androidhive.project.Util.GSONUtil;
import info.androidhive.project.WebService.Rest;
import info.androidhive.project.adapter.ElementAdapter;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Elements;

public class TagActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    public static int position = 0;
    private Elements elements;
    Rest restAPI = new Rest();
    ArrayList<Element> arrayOfElement = new ArrayList<Element>();
    ElementAdapter adapter = null;
    ScrollView mainScroll = null;
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_second);
        listView = (ListView) findViewById(R.id.listView_IN_TAGACTIVITY);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ElementAdapter(this, arrayOfElement);

        Intent intent = getIntent();
        String id_tag = intent.getStringExtra("id_tag");
        String id_user = intent.getStringExtra("name_iduser");

        mainScroll = (ScrollView) findViewById(R.id.main_scroll);
        mainScroll.fullScroll(ScrollView.FOCUS_UP);
        mainScroll.setEnabled(false);

        /*********************************/
        /*      Load data               */
        /*******************************/
        /*
            Gọi lên server lấy data 10post /lân
            load vào Element Adapter

         */
        new HttpRequestTask().execute();


        /**********Check new feed******/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            /****Hien dang fix la GET  */
            position = position + 1;
            //return restAPI.asyncResponse(Default.WSURL + "?id_user=1&counter=0");
            return restAPI.asyncResponse(Default.WSURL + "element");
        }

        @Override
        protected void onPostExecute(String result) {
            processValue(result);
        }

    }

    private void processValue(String data) {
        if (data != null) {

            // Attach the adapter to a ListView

            if (listView.getAdapter() == null) {
                elements = GSONUtil.convertJSONToElements(data);
                for (int i = 0; i < elements.getElements().size(); i++) {
                    adapter.add(elements.getElements().get(i));
                }
                listView.setAdapter(adapter);
            } else {
                elements = GSONUtil.convertJSONToElements(data);
                for (int i = 0; i < elements.getElements().size(); i++) {
                    adapter.add(elements.getElements().get(i));
                    adapter.notifyDataSetChanged();
                }
            }

            listView.setItemsCanFocus(false);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Element element = (Element) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), OnPostActivity.class);
                    intent.putExtra("info.androidhive.project.model.Element", element);
                    startActivity(intent);
                }
            };

            listView.setOnItemClickListener(itemClickListener);
            listView.setOnScrollListener(new EndlessScrollListener());

        }
    }

    private class EndlessScrollListener implements AbsListView.OnScrollListener {

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
            if (!loading && (visibleItemCount + firstVisibleItem) == totalItemCount) {
                // I load the next page of gigs using a background task,
                // but you can call any function here.
                // new HttpRequestTask().execute();
                Log.i("========>", "10 post/lan");
                Log.i("========> visibleItemCount", String.valueOf(visibleItemCount));
                Log.i("========> firstVisibleItem", String.valueOf(firstVisibleItem));
                Log.i("========> totalItemCount", String.valueOf(totalItemCount));
                //new HttpRequestTask().execute();
                loading = true;
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

    }

}
