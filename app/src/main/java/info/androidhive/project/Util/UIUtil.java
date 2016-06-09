package info.androidhive.project.Util;


import android.content.res.Resources;

/**
 * Created by VietAnh on 5/27/2016.
 */
public class UIUtil {
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /*****************************************************/
    /*                  Function Create tag              */
    /****************************************************/

}
