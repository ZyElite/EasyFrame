package cn.zy.ef.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/3/8 0008.
 */
public class EmptyAutoHideTextView extends TextView {
    public EmptyAutoHideTextView(Context context) {
        super(context);
    }

    public EmptyAutoHideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyAutoHideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
    }
}
