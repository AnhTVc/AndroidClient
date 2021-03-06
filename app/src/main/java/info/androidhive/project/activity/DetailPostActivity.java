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

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.adapter.ElementAdapter;
import info.androidhive.project.adapter.ImageAdapter;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Image;
import info.androidhive.project.model.ThreeImage;

public class DetailPostActivity extends AppCompatActivity {
    ImageAdapter adapter = null;
    private Toolbar mToolbar;
    ArrayList<ThreeImage> arrayOfElement = new ArrayList<ThreeImage>();
    private ArrayList<ThreeImage> tempAdapter = null;
    Element element = null;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tempAdapter = new ArrayList<>();
        adapter = new ImageAdapter(getApplicationContext(), tempAdapter);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        element = b.getParcelable("info.androidhive.project.model.Element");
       // adapter = new ImageAdapter(getApplicationContext(), arrayOfElement);
        ThreeImage threeImage;
        ArrayList<Image> images = null;
        int postion = 0;

        //TODO
        if(element != null){
            //TODO size =0
            if(element.getPost().getImages().size() < 3){
                threeImage = new ThreeImage();
                images = new ArrayList<>();
                for (int j = 0; j < element.getPost().getImages().size() ; j++) {
                    images.add(element.getPost().getImages().get(j));
                }
                threeImage.setThreeImage(images);
                adapter.add(threeImage);
            }
            else {
                for (int i = 1; i < (element.getPost().getImages().size() / 3) + 1; i++) {
                    threeImage = new ThreeImage();
                    images = new ArrayList<>();
                    for (int j = 1; j <= 3; j++) {
                        postion = j * i - 1;
                        images.add(element.getPost().getImages().get(postion));
                        if (j == 3) {
                            threeImage.setThreeImage(images);
                            adapter.add(threeImage);
                        }
                    }
                }

                if (postion + 1 < element.getPost().getImages().size()) {
                    threeImage = new ThreeImage();
                    for (int i = postion + 1; i < element.getPost().getImages().size(); i++) {
                        images = new ArrayList<>();
                        images.add(element.getPost().getImages().get(i));
                    }
                    threeImage.setThreeImage(images);
                    adapter.add(threeImage);
                }
            }
            //Size = 3
            process();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void process() {

        ListView listImageInDetail = (ListView) this.findViewById(R.id.listImageInDetail);
        if (listImageInDetail.getAdapter() == null) {
/*            int size = tempAdapter.size();
            for(int i = 0; i < size; i++){
                adapter.add(tempAdapter.get(i));
                Log.d("=======================>", "DuoC ROI NE");
            }*/
            listImageInDetail.setAdapter(adapter);
        }

        //View content
        TextView detail_content = (TextView) findViewById(R.id.detail_content);
        detail_content.setText(element.getPost().getContentPost());
    }
}