package com.storyvendingmachine.www.movienote;

import android.content.res.Resources;

/**
 * Created by Administrator on 2018-02-01.
 */

public class getScreeninfo {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
