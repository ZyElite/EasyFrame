package cn.zy.ef.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zy on 16-9-5.
 */
public class BaseActivity extends AppCompatActivity {

    protected BaseApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) this.getApplicationContext();
        mApplication.dispatchActivityCreated(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mApplication.dispatchActivityStarted(this);
    }

    protected void onResume() {
        super.onResume();
        mApplication.dispatchActivityResumed(this);
        mApplication.setCurrentActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mApplication.dispatchActivitySaveInstanceState(this, outState);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
        mApplication.dispatchActivityPaused(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mApplication.dispatchActivityStopped(this);
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
        mApplication.dispatchActivityDestroyed(this);
    }

    private void clearReferences() {
        Activity currActivity = mApplication.getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            mApplication.setCurrentActivity(null);
    }
}
