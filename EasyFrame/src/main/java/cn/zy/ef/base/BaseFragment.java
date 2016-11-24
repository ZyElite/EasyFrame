package cn.zy.ef.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.zy.ef.R;
import cn.zy.ef.TitleBarActivity;
import rx.Subscription;


/**
 * Created by zy on 16-9-5.
 */
public class BaseFragment extends Fragment {

    private Subscription subscribe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutResId(), container, false);
    }

    protected int getFragmentLayoutResId() {
        return 0;
    }

    protected void initTitleBar(TitleBarActivity titleBarActivity) {
    }

    public TitleBarActivity getTitleBarActivity() {
        return (TitleBarActivity) getActivity();
    }

    protected void initChildFragments() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        initChildFragments();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitleBar(getTitlebarActivity());
    }

    public TitleBarActivity getTitlebarActivity() {
        return (TitleBarActivity) getActivity();
    }

    public void startFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this);
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }


    public void startActivity(Class<? extends Activity> activityClass) {
        startActivity(new Intent(getActivity(), activityClass));
    }

    public void subscribe(Subscription subscribe) {
        if (this.subscribe != null) {
            this.subscribe.unsubscribe();
            this.subscribe = null;
        }
        this.subscribe = subscribe;
    }

    @Override
    public void onDestroyView() {
        if (subscribe != null) {
            subscribe.unsubscribe();
            subscribe = null;
        }
        super.onDestroyView();
    }
}
