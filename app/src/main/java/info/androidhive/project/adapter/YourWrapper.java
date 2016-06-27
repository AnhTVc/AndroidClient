package info.androidhive.project.adapter;

import android.view.View;
import android.widget.Button;

import info.androidhive.project.R;

/**
 * Created by VietAnh on 6/27/2016.
 */
public class YourWrapper {
    private View base;
    private Button button;

    public YourWrapper(View base) {
        this.base = base;
    }

    public Button getButton() {
        if (button == null) {
            button = (Button) base.findViewById(R.id.bt_heart);
        }
        return (button);
    }
}
