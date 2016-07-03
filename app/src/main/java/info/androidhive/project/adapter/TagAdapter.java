package info.androidhive.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import info.androidhive.project.R;
import info.androidhive.project.model.Tag;

/**
 * Created by VietAnh on 7/1/2016.
 */
public class TagAdapter extends ArrayAdapter<Tag> {
    Tag tag = null;
    final ArrayList<Tag> tags = new ArrayList<>();

    public TagAdapter(Context context, ArrayList<Tag> userTemps) {
        super(context, 0, userTemps);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        tag = getItem(position);
        tags.add(tag);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tag, parent, false);
        }

        // Lookup view for data population
        // Return the completed view to render on screen
        Button buttonTag = (Button) convertView.findViewById(R.id.buttonTag);
        buttonTag.setText(tag.getTag());

        //Event onclick
        return convertView;
    }
}
