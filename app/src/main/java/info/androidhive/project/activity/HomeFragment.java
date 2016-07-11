package info.androidhive.project.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import info.androidhive.project.R;
import info.androidhive.project.ReturnCode.Default;
import info.androidhive.project.Util.GSONUtil;
import info.androidhive.project.WebService.Rest;
import info.androidhive.project.adapter.ElementAdapter;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Elements;
import info.androidhive.project.model.Info;
import info.androidhive.project.model.TagPage;


public class HomeFragment extends Fragment {
    android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout = null;
    public static int position = 0;
    private Elements elements;
    Rest restAPI = new Rest();

    ArrayList<Element> arrayOfElement = new ArrayList<Element>();
    ElementAdapter adapter = null;
    ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

    @Override
    public void onPause() {
        super.onPause();
        scheduleTaskExecutor.shutdown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduleTaskExecutor.shutdown();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        adapter = new ElementAdapter(getContext(), arrayOfElement);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.container_body);
        // Inflate the layout for this fragment

        new HttpRequestTask().execute();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            ListView listView = (ListView) getView().findViewById(R.id.listView);
            // Attach the adapter to a ListView

            if (listView.getAdapter() == null) {
                elements = GSONUtil.convertJSONToElements(data);
                TagPage tagpage = new TagPage();
                Info info = new Info();
                info.setIdTag("123456");

                tagpage.setElements(elements);
                tagpage.setInfo(info);

                Log.d("=========>", tagpage.toString());


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
                    Log.i("========================>", element.getPost().getIdPost());
                    Intent intent = new Intent(getContext(), OnPostActivity.class);
                    intent.putExtra("info.androidhive.project.model.Element", element);
                    startActivity(intent);
                }
            };

            listView.setOnItemClickListener(itemClickListener);
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
    private class HttpNewFeedTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            /****Hien dang fix la GET  */
            Log.d("=======>", "CHECK NEW FEED");
            //return restAPI.asyncResponse(Default.WSURL + "?id_user=1&counter=0");
            return restAPI.asyncResponse(Default.WSURL + "element");
        }

        @Override
        protected void onPostExecute(String result) {
            processValueNew(result);
        }

    }

    private void processValueNew(String data) {
        if (data != null) {
            Toast.makeText(getContext(), "New Feed", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshContent() {
        Log.d("Have refresh Content", "");
        swipeRefreshLayout.setRefreshing(false);
    }
}
