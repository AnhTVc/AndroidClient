package info.androidhive.project.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.project.R;

/**
 * Created by VietAnh on 7/11/2016.
 */
public class ViewMoreTagFragment extends android.support.v4.app.DialogFragment {
    public ViewMoreTagFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_view_more_tag, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public static ViewMoreTagFragment newInstance(String title) {
        ViewMoreTagFragment frag = new ViewMoreTagFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


}
