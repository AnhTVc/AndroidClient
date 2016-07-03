package info.androidhive.project.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.adapter.OnImageAdapter;
import info.androidhive.project.adapter.TagAdapter;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Image;
import info.androidhive.project.model.Tag;

public class OnPostActivity extends AppCompatActivity {
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private Toolbar mToolbar;
    private Element element = null;
    private TagAdapter tagAdapter = null;
    private OnImageAdapter onImageAdapter = null;
    private ArrayList<Tag> tags = new ArrayList<Tag>();
    private ArrayList<Image> images = new ArrayList<>();

    private ListView listviewTag;
    private ListView listviewImage;
    private TextView textviewContent;

    /**
     * Hàm onCreate thực hiện các bước sau
     * 1. Get element
     * 2. Get Array Tag gán và adapter cho ItemTag
     * 3. Set text tới content Post
     * 4. Get Array Image gán cho adapter cho Image
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_post);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listviewImage = (ListView) findViewById(R.id.listviewImage_IN_ON_POST_ACTIVITY);
        listviewTag = (ListView) findViewById(R.id.listviewTag_IN_ON_POST_ACTIVITY);
        textviewContent = (TextView) findViewById(R.id.textviewContent_IN_ON_POST_ACTIVITY);

        tagAdapter = new TagAdapter(this, tags);
        onImageAdapter = new OnImageAdapter(this, images);
        //1. Get element
        Bundle b = getIntent().getExtras();
        element = b.getParcelable("info.androidhive.project.model.Element");

        //Check null
        if (element != null) {
            //2.
            tags = element.getTag();
            int sizeTag = tags.size();
            if (sizeTag > 0) {
                for (int i = 0; i < sizeTag; i++) {
                    tagAdapter.add(tags.get(i));
                }
                listviewTag.setAdapter(tagAdapter);
            }

            //3.
            textviewContent.setText(element.getPost().getContentPost());

            //4.
            images = element.getPost().getImages();
            int sizeImage = images.size();
            if (sizeImage > 0) {
                for (int i = 0; i < sizeImage; i++) {
                    onImageAdapter.add(images.get(i));
                }
                listviewImage.setAdapter(onImageAdapter);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
/*        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
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
}
