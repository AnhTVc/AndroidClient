package info.androidhive.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.ThreeImage;

/**
 * Created by VietAnh on 6/17/2016.
 */
public class ImageAdapter extends ArrayAdapter<ThreeImage> {
    ThreeImage threeImage = null;

    public ImageAdapter(Context context, ArrayList<ThreeImage> userTemps) {
        super(context, 0, userTemps);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        threeImage = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }
        //TODO
        ImageView left_image = (ImageView) convertView.findViewById(R.id.left_image);
        ImageView cent_image = (ImageView) convertView.findViewById(R.id.center_image);
        ImageView right_image = (ImageView) convertView.findViewById(R.id.right_images);

        TableRow tableRowImage = (TableRow) convertView.findViewById(R.id.tableRowImage);
        int size = threeImage.getThreeImage().size();
        if (size == 0) {
            tableRowImage.removeAllViews();
        } else if (size == 1) {
            //Có mỗi 1 ảnh
            Picasso.with(getContext()).load(threeImage.getThreeImage().get(0).getSrc()).into(cent_image);
            tableRowImage.removeView(left_image);
            tableRowImage.removeView(right_image);
        } else if (size == 2) {
            //Có 2 ảnh
            Picasso.with(getContext()).load(threeImage.getThreeImage().get(0).getSrc()).into(left_image);
            Picasso.with(getContext()).load(threeImage.getThreeImage().get(1).getSrc()).into(cent_image);
            tableRowImage.removeView(right_image);
        } else {
            //Có 3 ánh
            Picasso.with(getContext()).load(threeImage.getThreeImage().get(0).getSrc()).into(left_image);
            Picasso.with(getContext()).load(threeImage.getThreeImage().get(1).getSrc()).into(cent_image);
            Picasso.with(getContext()).load(threeImage.getThreeImage().get(2).getSrc()).into(right_image);
        }
        return convertView;
    }
}
