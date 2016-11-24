package cn.zy.ef.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import cn.zy.ef.util.DensityUtil;


/**
 * Created by Android on 2016/6/8 0008.
 */
public class FilletDialog extends Dialog {
    private static int default_width = 270; //默认宽度
    private static int default_height = 200;//默认高度

    public FilletDialog(Context context, int layout, int style) {
        this(context,default_width,default_height, layout, style);
    }

    public FilletDialog(final Context context, int width, int height, int layout, int style) {
        super(context, style);
        setContentView(layout);
//        DensityUtil.init(context);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DensityUtil.dip2px(getContext(),width);
        params.height = DensityUtil.dip2px(getContext(),height);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

    }


    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

}
