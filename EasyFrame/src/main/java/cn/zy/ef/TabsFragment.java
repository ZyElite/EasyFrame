package cn.zy.ef;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.lang.reflect.Field;


/**
 * Created by zy on 16-9-5.
 */

public abstract class TabsFragment extends Fragment {

    private static final Field mTabLayoutIdField;

    static {
        try {
            mTabLayoutIdField = TabHost.class.getDeclaredField("mTabLayoutId");
            mTabLayoutIdField.setAccessible(true);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private FragmentTabHost mFragmentTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentTabHost = new FragmentTabHost(getActivity());
        return mFragmentTabHost;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentTabHost().setup(getActivity(), getChildFragmentManager(), getContainerId());

        int tabLayoutId = getTabLayoutId();
        if (tabLayoutId != 0) {
            try {
                mTabLayoutIdField.set(mFragmentTabHost, tabLayoutId);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        initTabs(getFragmentTabHost());
    }

    protected abstract void initTabs(FragmentTabHost fragmentTabHost);

    public int getContainerId() {
        return R.id.realtabcontent;
    }

    public int getTabLayoutId() {
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (onActivityResultToCurrentFragment(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onActivityResultToCurrentFragment(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
            return true;
        }
        return false;
    }

    public Fragment getCurrentFragment() {
        return getChildFragmentManager().findFragmentById(getContainerId());
    }

    public FragmentTabHost getFragmentTabHost() {
        if (mFragmentTabHost != null) {
            return mFragmentTabHost;
        }

        mFragmentTabHost = (FragmentTabHost) getView().findViewById(android.R.id.tabhost);
        return mFragmentTabHost;
    }
}
