package cn.zy.ef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by zy on 16-9-5.
 */
public abstract class BottomTabsFragment extends TabsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_tabs, container, false);
        return rootView;
    }
}
