package cn.zy.ef.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;

import cn.zy.ef.rx.RxSubscriber;
import rx.Subscriber;

/**
 * Created by Administrator on 2015/4/3 0003.
 */
public class ProgressDialogResponseSubscriber<T> extends RxSubscriber<T> {

    private static final String DEFAULT_PROCESSING_MSG = "正在处理";
    private static final String DEFAULT_COMPLETE_MSG = "完成";
    private static final String DEFAULT_ERROR_MSG = "发生错误";
    private static final long DEFAULT_DELAY_MILLIONS = 1000;

    private ProgressDialog progressDialog;
    private CharSequence processingMessage;
    private CharSequence errorMessage;
    private Runnable completeRunnable;
    private CharSequence completeMessage;
    private CharSequence finallyMessage;

    private final Activity activity;
    private final boolean finishActivity;
    private final long delayMillis;

    private ProgressDialogResponseSubscriber(Builder builder) {
        activity = builder.activity;
        finallyMessage = builder.finallyMessage;
        completeMessage = builder.completeMessage;
        completeRunnable = builder.completeRunnable;
        errorMessage = builder.errorMessage;
        processingMessage = builder.processingMessage;
        finishActivity = builder.finishActivity;
        delayMillis = builder.delayMillis;
    }

    @Override
    public void onStart() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setOnCancelListener(dialog -> unsubscribe());
                }
                progressDialog.setMessage(processingMessage);
                progressDialog.show();
            }
        });
        super.onStart();
    }


    @Override
    public void onNext(T t) {
        super.onNext(t);
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        finallyMessage = getErrorMessage(e);
        progressDialog.setMessage(finallyMessage);
        (new Handler()).postDelayed(() -> progressDialog.dismiss(), DEFAULT_DELAY_MILLIONS);
    }

    @Override
    protected void _onNext(T t) {

    }


    @Override
    protected void _onError(String message) {

    }

    public static String getErrorMessage(Throwable throwable) {
        return throwable.getMessage();
    }

    @Override
    public void onCompleted() {
        finallyMessage = completeMessage;
        progressDialog.setMessage(finallyMessage);
        (new Handler()).postDelayed(() -> {
            progressDialog.dismiss();

            if (completeRunnable != null) {
                completeRunnable.run();
            }

            if (finishActivity)
                activity.finish();
        }, delayMillis);
    }

    public static final class Builder<T> {

        private final Activity activity;
        private CharSequence finallyMessage;
        private CharSequence completeMessage;
        private Runnable completeRunnable;
        private CharSequence errorMessage;
        private CharSequence processingMessage;
        private boolean finishActivity;
        private long delayMillis = DEFAULT_DELAY_MILLIONS;

        private Subscriber subscriber;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder finallyMessage(CharSequence finallyMessage) {
            this.finallyMessage = finallyMessage;
            return this;
        }

        public Builder completeMessage(CharSequence completeMessage) {
            this.completeMessage = completeMessage;
            return this;
        }

        public Builder completeRunnable(Runnable completeRunnable) {
            this.completeRunnable = completeRunnable;
            return this;
        }

        public Builder errorMessage(CharSequence errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder processingMessage(CharSequence processingMessage) {
            this.processingMessage = processingMessage;
            return this;
        }

        public Builder finishActivity() {
            this.finishActivity = true;
            return this;
        }

        public Builder delay(long delayMillis) {
            this.delayMillis = delayMillis;
            return this;
        }

        public ProgressDialogResponseSubscriber<T> build() {
            if (processingMessage == null) {
                processingMessage = "正在处理";
            }

            if (completeMessage == null) {
                completeMessage = "完成";
            }

            if (errorMessage == null) {
                errorMessage = "发生错误";
            }

            return new ProgressDialogResponseSubscriber<T>(this);
        }
    }
}
