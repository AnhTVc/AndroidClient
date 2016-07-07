package info.androidhive.project.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.ReturnCode.Default;
import info.androidhive.project.Util.GSONUtil;
import info.androidhive.project.WebService.Rest;
import info.androidhive.project.adapter.CommentAdapter;
import info.androidhive.project.adapter.OnImageAdapter;
import info.androidhive.project.adapter.TagAdapter;
import info.androidhive.project.model.Comment;
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
    private CommentAdapter commentAdapter = null;

    private ArrayList<Tag> tags = new ArrayList<Tag>();
    private ArrayList<Image> images = new ArrayList<>();

    private ListView listviewTag;
    private ListView listviewImage;
    private TextView textviewContent;
    private ListView listViewComment;

    private ArrayList<Comment> comments = null;
    Rest restAPI = null;

    private Button postCommentButton = null;
    /**
     * Hàm onCreate thực hiện các bước sau
     * 1. Get element
     * 2. Get Array Tag gán và adapter cho ItemTag
     * 3. Set text tới content Post
     * 4. Get Array Image gán cho adapter cho Image
     * 5. Get element
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

        restAPI = new Rest();

        listviewImage = (ListView) findViewById(R.id.listviewImage_IN_ON_POST_ACTIVITY);
        listviewTag = (ListView) findViewById(R.id.listviewTag_IN_ON_POST_ACTIVITY);
        textviewContent = (TextView) findViewById(R.id.textviewContent_IN_ON_POST_ACTIVITY);
        listViewComment = (ListView) findViewById(R.id.listviewComment_IN_ON_POST_ACTIVITY);

        tagAdapter = new TagAdapter(this, tags);
        onImageAdapter = new OnImageAdapter(this, images);
        commentAdapter = new CommentAdapter(this, comments);
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

        //5.
        //TODO gọi lên server lấy các comment.
        if (comments != null) {
            for (int i = 0; i < comments.size(); i++) {
                commentAdapter.add(comments.get(i));
            }

            listViewComment.setAdapter(commentAdapter);
        }
        //6. user thực hiện comment
/*        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);*/
        //hideSoftKeyboard(this);
        final EditText editText = (EditText) findViewById(R.id.edit_text_post);
        postCommentButton = (Button) findViewById(R.id.bt_post_comment);

        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check null edittext
                String strComment = editText.getText().toString();
                if (!strComment.trim().matches("")) {
                    //Post lên server
                    //TODO
                    String url = null;
                    new CommentTask().execute(url);
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
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

    //Gọi lên server lấy thông tin comment cho từng bài viết
    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            /****Hien dang fix la GET  */
            //TODO gọi lên server
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
            //TODO có Array list Comment
        }
    }

    //Post comment lên server
    private class CommentTask extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {
            try {
                //TODO
                return restAPI.asyncResponse(Default.WSURL + Default.URL_FEEDBACK + urls[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
            //Thay đổi adapter
            Comment tempComment = new Comment();

            ArrayList<Comment> tempComments = new ArrayList<>();

            tempComments.add(tempComment);
            if (comments != null) {
                for (int i = 0; i < comments.size(); i++) {
                    tempComments.add(comments.get(i));
                }
            }
            comments = tempComments;
            if (listViewComment.getAdapter() == null) {
                // Create new adapter + list.setAdapter()
                commentAdapter.addAll(comments);
                listViewComment.setAdapter(commentAdapter);
            } else {
                commentAdapter.clear();
                commentAdapter.addAll(comments);
                commentAdapter.notifyDataSetChanged();
            }

        }
    }
}
