package cn.zy.ef.rx;


import cn.zy.ef.base.BaseRespose;
import cn.zy.ef.util.Log;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * des:对服务器返回数据成功和失败处理
 * Created by xsf
 * on 2016.09.9:59
 */

/**************
 * 使用例子
 ******************/
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/

public class RxHelper {
    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseRespose<T>, T> handleResult() {
        return new Observable.Transformer<BaseRespose<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseRespose<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<BaseRespose<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(BaseRespose<T> result) {

                                if (result.success()) {
                                    return createData(result.data);
                                } else {
                                    Log.toast(result.msg);
//                                    if (result.getCode() == 200) {
//                                        SPUtils.setSharedIntData(BaseApplication.getAppContext(), "status", -1);
//                                        SPUtils.setSharedStringData(BaseApplication.getAppContext(), "token", "");
//                                    }
                                    return Observable.error(new ServerException(result.msg));
                                }
                            }
                        });
            }
        };

    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }
}
