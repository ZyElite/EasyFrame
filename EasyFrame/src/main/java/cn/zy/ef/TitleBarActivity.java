package cn.zy.ef;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.zy.ef.base.BaseApplication;
import cn.zy.ef.runtimepermissions.PermissionsManager;
import cn.zy.ef.runtimepermissions.PermissionsResultAction;
import cn.zy.ef.util.ActivityManager;

/**
 * Created by zy on 16-9-5.
 */
public class TitleBarActivity extends AppCompatActivity {
    protected BaseApplication mApplication;
    public final ArrayList<OnBackPressedListener> mOnBackPressedListeners = new ArrayList<OnBackPressedListener>();
    public static final String ACCOUNT_REMOVED = "account_removed";
    protected TextView mLeftButton;
    protected TextView mMidButton;
    protected TextView mRightButton;
    protected int titleViewLayoutId = 0;
    private View actionBarCustomView;

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }

    public void addBackPressedListener(OnBackPressedListener onBackPressedListener) {
        synchronized (mOnBackPressedListeners) {
            // This is a linear search, but in practice we'll
            // have only a couple callbacks, so it doesn't matter.
            if (mOnBackPressedListeners.contains(onBackPressedListener) == false) {
                mOnBackPressedListeners.add(onBackPressedListener);
            }
        }
    }

    public void removeBackPressedListener(OnBackPressedListener onBackPressedListener) {
        synchronized (mOnBackPressedListeners) {
            mOnBackPressedListeners.remove(onBackPressedListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewGroup actionBarCustomView = (ViewGroup) getLayoutInflater().inflate(R.layout.title_bar, null);
        mLeftButton = (TextView) actionBarCustomView.findViewById(R.id.btn_left);
        mMidButton = (TextView) actionBarCustomView.findViewById(R.id.btn_mid);
        mRightButton = (TextView) actionBarCustomView.findViewById(R.id.btn_right);
        super.onCreate(savedInstanceState);


        ActivityManager.getInstance().addActivity(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(actionBarCustomView);//48
            initTitle();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }

        requestPermissions();
    }

    protected void initTitle() {

    }

    @Override
    public void setTitle(CharSequence title) {
        mMidButton.setText(title);
        mMidButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setRightButtonEmtpy();
    }

    public TextView setLeftButton(CharSequence text) {
        mLeftButton.setText(text);
        return mLeftButton;
    }

    public TextView setLeftButton(CharSequence text, View.OnClickListener listener) {
        mLeftButton.setText(text);
        mLeftButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        mLeftButton.setOnClickListener(listener);
        return mLeftButton;
    }


    public void setBackButton(Drawable leftArrow) {
        setBackButton(" ", leftArrow);
    }


    public void setBackButton(String text, Drawable leftArrow) {
        setLeftButton(text, v -> onBackPressed()).setCompoundDrawablesWithIntrinsicBounds(leftArrow, null, null, null);
    }

    public void setLeftButtonEmtpy() {
        setLeftButton("", null).setCompoundDrawables(null, null, null, null);
    }

    public TextView setRightButton(String text, View.OnClickListener listener) {
        mRightButton.setText(text);
        mRightButton.setOnClickListener(listener);
        return mRightButton;
    }

    public void setRightButtonEmtpy() {
        setRightButton("", null).setCompoundDrawables(null, null, null, null);
    }

    public void setRightButton(Drawable drawable, View.OnClickListener listener) {
        setRightButton("  ", listener).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void showToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public TextView getMidButton() {
        return mMidButton;
    }

    public TextView getRightButton() {
        return mRightButton;
    }

    public TextView getLeftButton() {
        return mLeftButton;
    }

    @Override
    public void onBackPressed() {
        for (OnBackPressedListener OnBackPressedListener : mOnBackPressedListeners) {
            if (OnBackPressedListener.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }


    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
