package info.androidhive.project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.adapter.ElementAdapter;
import info.androidhive.project.adapter.ImageAdapter;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Image;
import info.androidhive.project.model.ThreeImage;

public class DetailPostActivity extends AppCompatActivity {
    ImageAdapter adapter = null;
    ArrayList<ThreeImage> arrayOfElement = new ArrayList<ThreeImage>();
    Element element = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        ThreeImage threeImage;
        ArrayList<Image> images;
        //TODO
        for (int i = 0; i < (element.getPost().getImages().size() % 3); i++) {
            images = new ArrayList<>();
            images.add(element.getPost().getImages().get(i));
            threeImage = new ThreeImage();
        }
        adapter = new ImageAdapter(this, arrayOfElement);


    }

    private void process() {
        ListView listImageInDetail = (ListView) this.findViewById(R.id.listImageInDetail);
        if (listImageInDetail.getAdapter() == null) {

        } else {

        }
    }
}
