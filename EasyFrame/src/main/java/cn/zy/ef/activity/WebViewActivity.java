package cn.zy.ef.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.zy.ef.R;
import cn.zy.ef.TitleBarActivity;
import cn.zy.ef.fragment.WebViewFragment;


public class WebViewActivity extends TitleBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        if (savedInstanceState == null) {
            Fragment fragment = Fragment.instantiate(this, WebViewFragment.class.getName(), getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    public static void start(Activity activity, String url) {
        start(activity, "", url);
    }

    public static void start(Activity activity, String titleName, String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("titleName", titleName);
        intent.putExtra(Uri.class.getName(), url);
        activity.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        setTitle(getIntent().getStringExtra("titleName"));
        setBackButton(getResources().getDrawable(R.mipmap.left_arrow));
    }

}
