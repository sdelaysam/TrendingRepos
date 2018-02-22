package test.trendingrepos.utils;

import android.databinding.BindingConversion;
import android.view.View;

/**
 * Created on 22/02/2018
 *
 * @author sdelaysam
 */

public abstract class  BindingUtils {

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

}
