package com.ua.sdk.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.MultipleCreateCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.ResetPasswordCallback;
import com.ua.sdk.Resource;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.Ua;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.UploadCallback;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;
import java.util.List;

public class EntityEventHandler extends Handler {
    private static final EntityEventHandler HANDLER = new EntityEventHandler();
    private static final int ON_CREATED = 0;
    private static final int ON_DELETED = 3;
    private static final int ON_FETCHED = 1;
    private static final int ON_LOGIN = 4;
    private static final int ON_LOGOUT = 5;
    private static final int ON_MULTIPLE_CREATED = 10;
    private static final int ON_PAGE_FETCHED = 7;
    private static final int ON_RESET_PASSWORD = 6;
    private static final int ON_SAVED = 2;
    private static final int ON_UPLOAD_PROGRESS = 8;
    private static final int ON_UPLOAD_UPLOADED = 9;

    private EntityEventHandler() {
        super(Looper.getMainLooper());
    }

    public void handleMessage(Message msg) {
        try {
            Precondition.isOnMain();
            switch (msg.what) {
                case 0:
                    MessageData data = (MessageData) msg.obj;
                    ((CreateCallback) data.listener).onCreated((Resource) data.value, data.error);
                    return;
                case 1:
                    MessageData data2 = (MessageData) msg.obj;
                    ((FetchCallback) data2.listener).onFetched((Resource) data2.value, data2.error);
                    return;
                case 2:
                    MessageData data3 = (MessageData) msg.obj;
                    ((SaveCallback) data3.listener).onSaved((Resource) data3.value, data3.error);
                    return;
                case 3:
                    MessageData data4 = (MessageData) msg.obj;
                    ((DeleteCallback) data4.listener).onDeleted((Reference) data4.value, data4.error);
                    return;
                case 4:
                    MessageData data5 = (MessageData) msg.obj;
                    ((Ua.LoginCallback) data5.listener).onLogin((User) data5.value, data5.error);
                    return;
                case 5:
                    MessageData data6 = (MessageData) msg.obj;
                    ((Ua.LogoutCallback) data6.listener).onLogout(data6.error);
                    return;
                case 6:
                    MessageData data7 = (MessageData) msg.obj;
                    ((ResetPasswordCallback) data7.listener).onFetched(data7.error);
                    return;
                case 8:
                    MessageData data8 = (MessageData) msg.obj;
                    ((UploadCallback) data8.listener).onProgress(((Long) data8.value).longValue());
                    return;
                case 9:
                    MessageData data9 = (MessageData) msg.obj;
                    ((UploadCallback) data9.listener).onUploaded((Resource) data9.value, data9.error);
                    return;
                case 10:
                    MessageData data10 = (MessageData) msg.obj;
                    ((MultipleCreateCallback) data10.listener).onSynced((List) data10.value, data10.error);
                    return;
                default:
                    return;
            }
        } catch (Throwable t) {
            UaLog.error("Error handling callback.", t);
        }
        UaLog.error("Error handling callback.", t);
    }

    public static <T extends Resource> void callOnSaved(T entity, UaException error, SaveCallback<T> listener) {
        if (listener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                listener.onSaved(entity, error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(2, new MessageData(listener, entity, error)));
        }
    }

    public static <R extends Reference> void callOnDeleted(R ref, UaException error, DeleteCallback<R> listener) {
        if (listener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                listener.onDeleted(ref, error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(3, new MessageData(listener, ref, error)));
        }
    }

    public static <T extends Resource> void callOnFetched(T entity, UaException error, FetchCallback<T> listener) {
        if (listener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                listener.onFetched(entity, error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(1, new MessageData(listener, entity, error)));
        }
    }

    public static void callOnLogin(User user, UaException error, Ua.LoginCallback listener) {
        if (listener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                listener.onLogin(user, error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(4, new MessageData(listener, user, error)));
        }
    }

    public static void callOnLogout(UaException error, Ua.LogoutCallback listener) {
        if (listener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                listener.onLogout(error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(5, new MessageData(listener, (Object) null, error)));
        }
    }

    public static <T extends Resource> void callOnCreated(T entity, UaException error, CreateCallback<T> callback) {
        if (callback != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                callback.onCreated(entity, error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(0, new MessageData(callback, entity, error)));
        }
    }

    public static <T extends Resource> void callOnMultipleCreated(List<T> results, UaException error, MultipleCreateCallback<T> callback) {
        if (callback != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                callback.onSynced(results, error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(10, new MessageData(callback, results, error)));
        }
    }

    public static void callOnResetPassword(UaException error, ResetPasswordCallback callback) {
        if (callback != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                callback.onFetched(error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(6, new MessageData(callback, (Object) null, error)));
        }
    }

    public static void callOnUploadProgress(long progress, UploadCallback callback) {
        if (callback != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                callback.onProgress(progress);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(8, new MessageData(callback, Long.valueOf(progress), (UaException) null)));
        }
    }

    public static <T extends Resource> void callOnUploadUploaded(T entity, UaException error, UploadCallback<T> callback) {
        if (callback != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                callback.onUploaded(entity, error);
                return;
            }
            HANDLER.sendMessage(HANDLER.obtainMessage(9, new MessageData(callback, entity, error)));
        }
    }

    private static class MessageData {
        final UaException error;
        final Object listener;
        final Object value;

        private MessageData(Object listener2, Object value2, UaException error2) {
            this.listener = listener2;
            this.value = value2;
            this.error = error2;
        }
    }
}
