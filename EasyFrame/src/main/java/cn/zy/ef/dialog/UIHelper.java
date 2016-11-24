package cn.zy.ef.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.zy.ef.R;


/**
 * Created by Android on 2016/7/23 0023.
 */
public class UIHelper {
    /** 加载数据对话框 */
    private static Dialog mLoadingDialog;

    /**
     * 显示加载对话框
     * @param context 上下文
     * @param rootlayoutId  对话框布局
     * @param msgViewId 显示提示信息的viewId
     * @param msg   对话框显示内容
     * @param cancelable   对话框是否可以取消
     */
    public static void showDialogForLoading(Context context, int rootlayoutId, int msgViewId, String msg, boolean cancelable) {
        View view = View.inflate(context,rootlayoutId, null);
        TextView loadingText = (TextView)view.findViewById(msgViewId);
        loadingText.setText(msg);

        mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
    }

    /**
     * 关闭加载对话框
     */
    public static void hideDialogForLoading() {
        if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }
}
