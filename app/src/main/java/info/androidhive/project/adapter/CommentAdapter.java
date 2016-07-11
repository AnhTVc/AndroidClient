package info.androidhive.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.ReturnCode.Default;
import info.androidhive.project.model.Comment;

/**
 * Created by VietAnh on 7/7/2016.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    private Comment comment;

    public CommentAdapter(Context context, ArrayList<Comment> userTemps) {
        super(context, 0, userTemps);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        comment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }

        //Bắt đầu xử lý ở đây
        ImageView userImage = (ImageView) convertView.findViewById(R.id.user_image_comment_IN_ITEM_COMMENT);
        TextView userName = (TextView) convertView.findViewById(R.id.user_comment_IN_ITEM_COMMENT);
        TextView content = (TextView) convertView.findViewById(R.id.content_comment_IN_ITEM_COMMENT);
        TextView timeCreate = (TextView) convertView.findViewById(R.id.time_create_IN_ITEM_COMMENT);

        userName.setText(comment.getUser().getNameUser());
        content.setText(comment.getContent());
        timeCreate.setText(comment.getTimeCreate());
        try {
            Picasso.with(getContext()).load(comment.getUser().getImage().getSrc()).into(userImage);
        } catch (Exception e) {
            e.printStackTrace();
            Picasso.with(getContext()).load(Default.URL_IMAGE_NO_NAME).into(userImage);
        }
        return convertView;
    }

}
