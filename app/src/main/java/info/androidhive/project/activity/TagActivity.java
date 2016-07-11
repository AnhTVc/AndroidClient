package info.androidhive.project.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.androidhive.project.Dialog.ViewMoreTagFragment;
import info.androidhive.project.R;
import info.androidhive.project.ReturnCode.Default;
import info.androidhive.project.Util.GSONUtil;
import info.androidhive.project.WebService.Rest;
import info.androidhive.project.adapter.ElementAdapter;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Elements;
import info.androidhive.project.model.Info;
import info.androidhive.project.model.TagPage;

public class TagActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    public static int position = 0;
    private Elements elements;
    Rest restAPI = new Rest();
    ArrayList<Element> arrayOfElement = new ArrayList<Element>();
    ElementAdapter adapter = null;
    ScrollView mainScroll = null;
    ListView listView = null;

    TextView userFollowStatus = null;
    TextView countUserFollow = null;
    TextView contentTag = null;
    TextView contactTag = null;
    ImageView viewMoreTag = null;
    RelativeLayout imageTag = null;

    Info info = null;
    TagPage tagPage = null;
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
        new HttpRequestTagTask().execute();

        userFollowStatus = (TextView) findViewById(R.id.status_user_follow_TAG_ACTIVITY);
        countUserFollow = (TextView) findViewById(R.id.user_follow_count_TAG_ACTIVITY);
        contentTag = (TextView) findViewById(R.id.desc_tag_TAG_ACTIVITY);
        contactTag = (TextView) findViewById(R.id.contact_TAG_ACTIVITY);
        imageTag = (RelativeLayout) findViewById(R.id.image_tag_TAG_ACTIVITY);
        viewMoreTag = (ImageView) findViewById(R.id.view_more_tag);

        if (info != null) {
            //Set data
        }
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

    private class HttpRequestTagTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            /****Hien dang fix la GET  */
            position = position + 1;
            //return restAPI.asyncResponse(Default.WSURL + "?id_user=1&counter=0");
            return restAPI.asyncResponse(Default.WSURL + "tag");
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
                tagPage = GSONUtil.convertJSONToTagPage(data);
                if (tagPage != null) {
                    info = tagPage.getInfo();
                    elements = tagPage.getElements();
                    if (elements != null) {
                        for (int i = 0; i < elements.getElements().size(); i++) {
                            adapter.add(elements.getElements().get(i));
                        }
                        listView.setAdapter(adapter);
                    }

                    //TODO đang bị thiếu thông tin

                    if (info != null) {
                        contactTag.setText(info.getPlace());
                        contentTag.setText(info.getDesc());
                    }
                    viewMoreTag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager fm = getSupportFragmentManager();
                            ViewMoreTagFragment viewMoreTagFragment = ViewMoreTagFragment.newInstance(info.getTag());
                            viewMoreTagFragment.show(fm, "fragment_view_more_tag");
                        }
                    });
                }

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
        }
    }


}
