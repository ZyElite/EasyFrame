package cn.zy.ef.base;

import java.io.Serializable;


/**
 * @author zy
 * @version 1.0
 * @date 16-10-6
 * @des BaseRespose.java 封装服务器返回数据
 */

public class BaseRespose<T> implements Serializable {
    public int code;
    public String msg;
    public T data;

    public boolean success() {
        return 100 == code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public static class ResponseException extends RuntimeException {
        private final BaseRespose response;

        public ResponseException(BaseRespose response) {
            super(response.getMsg());
            this.response = response;
        }

        public ResponseException(Throwable throwable, BaseRespose response) {
            super(response.getMsg(), throwable);
            this.response = response;
        }

        public ResponseException(String detailMessage, BaseRespose response) {
            super(detailMessage);
            this.response = response;
        }

        public int getErrorCode() {
            return response.getCode();
        }
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
