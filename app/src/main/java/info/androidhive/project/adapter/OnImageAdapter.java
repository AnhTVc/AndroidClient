package info.androidhive.project.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.model.Image;

/**
 * Created by VietAnh on 7/1/2016.
 */
public class OnImageAdapter extends ArrayAdapter<Image> {
    Image image = null;
    ImageView child_image = null;

    public OnImageAdapter(Context context, ArrayList<Image> userTemps) {
        super(context, 0, userTemps);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        image = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_one_image, parent, false);
        }
        // Lookup view for data population
        // Return the completed view to render on screen
        child_image = (ImageView) convertView.findViewById(R.id.child_image);
        int widthscreen = Resources.getSystem().getDisplayMetrics().widthPixels;
        if (image.getSrc() != null) {
            child_image.getLayoutParams().width = widthscreen;
            child_image.getLayoutParams().height = widthscreen;
            try {
                Picasso.with(getContext()).load(image.getSrc()).into(child_image);
            } catch (Exception e) {
                e.getStackTrace();
            }

        }

        return convertView;
    }

}
