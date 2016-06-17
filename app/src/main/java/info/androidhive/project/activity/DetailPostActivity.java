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
    ArrayList<ThreeImage> tempAdapter = new ArrayList<ThreeImage>();
    Element element = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        adapter = new ImageAdapter(this, arrayOfElement);
        ThreeImage threeImage;
        ArrayList<Image> images;
        int postion = 0;
        //TODO
        for (int i = 1; i < (element.getPost().getImages().size() % 3) + 1; i++) {
            threeImage = new ThreeImage();
            images = new ArrayList<>();
            for (int j = 0; j <= 3; j++) {
                postion = j * i - 1;
                images.add(element.getPost().getImages().get(i * j - 1));
                if (j == 3) {
                    threeImage.setThreeImage(images);
                    tempAdapter.add(threeImage);
                    images.clear();
                }
            }
        }

        if (postion + 1 < element.getPost().getImages().size()) {
            threeImage = new ThreeImage();
            for (int i = postion + 1; i < element.getPost().getImages().size(); i++) {
                images = new ArrayList<>();
                images.add(element.getPost().getImages().get(i));
                threeImage.setThreeImage(images);
                tempAdapter.add(threeImage);
                images.clear();
            }
        }

        process();

    }

    private void process() {
        ListView listImageInDetail = (ListView) this.findViewById(R.id.listImageInDetail);
        if (listImageInDetail.getAdapter() == null) {
            for (int i = 0; i < tempAdapter.size(); i++) {
                adapter.add(tempAdapter.get(i));
            }
        } else {
            for (int i = 0; i < tempAdapter.size(); i++) {
                adapter.add(tempAdapter.get(i));
                adapter.notifyDataSetChanged();
            }
        }
    }
}
