package info.androidhive.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.activity.DetailPostActivity;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Image;
import info.androidhive.project.model.Post;
import info.androidhive.project.model.User;

public class ElementAdapter extends ArrayAdapter<Element> {
    Element element = null;

    public ElementAdapter(Context context, ArrayList<Element> userTemps) {
        super(context, 0, userTemps);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        element = getItem(position);

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
        time_create.setText(element.getPost().getCreatePost());

        //new ImageLoadTask(user.getImage().getSrc(), user_image).execute();
        Picasso.with(getContext()).load(user.getImage().getSrc()).into(user_image);
        /****************Post***********************/

        ImageView imageLeft = (ImageView) convertView.findViewById(R.id.image_post_left);
        ImageView imageRightTop = (ImageView) convertView.findViewById(R.id.image_post_right_top);
        ImageView imageRightBottom = (ImageView) convertView.findViewById(R.id.image_post_right_bottom);
        RelativeLayout relativeLayoutImage = (RelativeLayout) convertView.findViewById(R.id.tableRowImage);
        TextView post_content = (TextView) convertView.findViewById(R.id.content_post);
        Post post = element.getPost();

        ArrayList<Image> images = post.getImages();

        int widthscreen = Resources.getSystem().getDisplayMetrics().widthPixels;
        if (images.size() == 0) {
            //Khong co image
            relativeLayoutImage.setVisibility(View.INVISIBLE);
        } else if (images.size() == 1) {
            //Remove imageRight
            relativeLayoutImage.removeView(imageRightTop);
            relativeLayoutImage.removeView(imageRightBottom);

            //Size image left = full
            imageLeft.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            try {
                Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
            } catch (Exception e) {
                Log.d("ERROR NÈ: ===========================>", images.get(0).getSrc().toString());
                e.printStackTrace();
            }
        } else if (images.size() == 2) {
            //Remove imageRightBottom
            relativeLayoutImage.removeView(imageRightBottom);

            imageRightTop.getLayoutParams().height = imageLeft.getLayoutParams().height;
            try {
                Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
                Picasso.with(getContext()).load(images.get(1).getSrc()).into(imageRightTop);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ERROR NÈ: ===========================>", images.get(0).getSrc().toString());
                Log.d("ERROR NÈ: ===========================>", images.get(1).getSrc().toString());
                e.printStackTrace();
            }
        } else {

            try {
                Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
                Picasso.with(getContext()).load(images.get(1).getSrc()).into(imageRightTop);
                Picasso.with(getContext()).load(images.get(2).getSrc()).into(imageRightBottom);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ERROR NÈ: ===========================>", images.get(0).getSrc().toString());
                Log.d("ERROR NÈ: ===========================>", images.get(1).getSrc().toString());
                e.printStackTrace();
            }
        }
        post_content.setText(post.getContentPost());

        /****************Feedback*******************/
        Button bt_heart = (Button) convertView.findViewById(R.id.bt_heart);
        Button bt_heart_broken = (Button) convertView.findViewById(R.id.bt_heart_broken);
        //Tag tag = element.getTag();]

        bt_heart.setText(element.getPost().getCountTruePost());
        bt_heart.setText(element.getPost().getCountFalsePost());

        bt_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bt_heart_broken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        TableRow tableTag = (TableRow) convertView.findViewById(R.id.tableTag);
        if (element.getTag() != null) {
            for (int i = 0; i < element.getTag().size(); i++) {
                tableTag.addView(createTag(element.getTag().get(i).getNameTag()));
            }
        }

        //Excute



        return convertView;

    }

    private Button createTag(String tagName) {
        Button button = new Button(getContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        button.setLayoutParams(lp);
        button.setText(tagName);
        button.setTextColor(getContext().getResources().getColor(R.color.user_update));
        return button;
    }
}
