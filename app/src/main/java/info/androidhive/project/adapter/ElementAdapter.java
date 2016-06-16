package info.androidhive.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Image;
import info.androidhive.project.model.Post;
import info.androidhive.project.model.User;

/**
 * Created by VietAnh on 6/13/2016.
 */
public class ElementAdapter extends ArrayAdapter<Element> {
    Element element = null;

    public ElementAdapter(Context context, ArrayList<Element> userTemps) {
        super(context, 0, userTemps);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        element = getItem(position);
        //TODO

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_element, parent, false);
        }
        // Lookup view for data population
        // Return the completed view to render on screen
        /****************User************************/
        TextView user_name = (TextView) convertView.findViewById(R.id.user_name);
        TextView time_create = (TextView) convertView.findViewById(R.id.time_create);
        User user = element.getUser();
        ImageView user_image = (ImageView) convertView.findViewById(R.id.user_image);
        user_name.setText(user.getNameUser());

        //new ImageLoadTask(user.getImage().getSrc(), user_image).execute();
        Picasso.with(getContext()).load(user.getImage().getSrc()).into(user_image);
        /****************Post***********************/

        ImageView imageLeft = (ImageView) convertView.findViewById(R.id.image_post_left);
        ImageView imageRight = (ImageView) convertView.findViewById(R.id.image_post_right);
        TableRow tableRow = (TableRow) convertView.findViewById(R.id.tableRowImage);
        TextView post_content = (TextView) convertView.findViewById(R.id.content_post);
        Post post = element.getPost();

        ArrayList<Image> images = post.getImages();
        if (images.size() == 0) {
            //Khong co image
            tableRow.setVisibility(View.INVISIBLE);
        } else if (images.size() == 1) {
            //Co 1 image
            //new ImageLoadTask(images.get(0).getSrc(), imageLeft).execute();
            tableRow.removeView(imageRight);
            Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
        } else {
            Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
            Picasso.with(getContext()).load(images.get(1).getSrc()).into(imageRight);

        }
        post_content.setText(post.getContentPost());

        /****************Feedback*******************/
        ImageButton bt_heart = (ImageButton) convertView.findViewById(R.id.bt_heart);
        ImageButton bt_heart_broken = (ImageButton) convertView.findViewById(R.id.bt_heart_broken);
        //Tag tag = element.getTag();
        //Excute
        return convertView;

    }
}
