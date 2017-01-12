package com.app.temproject.shipper.Object;

import android.util.Log;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Admin on 14/12/2016.
 */

public class SocketConnection {
    public static final String KEY_SHIPPER_APPLY_REQUEST_PORT = "shipper-apply-request";
    public static final String KEY_SHIPPER_REQUIRE_CONFIRM_REQUEST_PORT = "shipper-require-confirm-request-compeleted";
    public static final String KEY_SHIPPER_CANCEL_ACCEPTED_RESPONSE_PORT = "shipper-cancel-accepted-response";
    public static final String KEY_STORE_CONFIRM_COMPLETED_REQUEST_PORT = "store-confirm-request-compeleted";
    public static final String KEY_STORE_ACCEPT_SHIPPER_PORT = "store-accept-shipper";
    public static final String KEY_STORE_ACCEPT_ANOTHER_SHIPPER_PORT = "store-accept-another-shipper";
    public static final String KEY_STORE_CANCEL_ACCEPTED_REQUEST_PORT = "store-cancel-accepted-request";
    private OnNotifyListener onNotifyListener;
    private String socketUrl;
    private Socket socket;
    private int role;

    public SocketConnection(String mSocketUrl, int role) {
        this.socketUrl = mSocketUrl;
        this.role = role;
        try {
            socket = IO.socket(socketUrl);
            socket.connect();
            if (role == Constant.STORE_ROLE) {
                socket.on(KEY_SHIPPER_APPLY_REQUEST_PORT, onShipperApplyRequest);
                socket.on(KEY_SHIPPER_REQUIRE_CONFIRM_REQUEST_PORT, onShipperRequireConfirmRequestCompeleted);
                socket.on(KEY_SHIPPER_CANCEL_ACCEPTED_RESPONSE_PORT, onShipperCancelAcceptedResponse);

            } else {
                socket.on(KEY_STORE_CONFIRM_COMPLETED_REQUEST_PORT, onStoreConfirmRequestCompeleted);
                socket.on(KEY_STORE_ACCEPT_SHIPPER_PORT, onStoreAcceptShipper);
                socket.on(KEY_STORE_ACCEPT_ANOTHER_SHIPPER_PORT, onStoreAcceptAnotherShipper);
                socket.on(KEY_STORE_CANCEL_ACCEPTED_REQUEST_PORT, onStoreCancelAcceptedRequest);

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private class NotificationListener implements Emitter.Listener {

        @Override
        public void call(Object... args) {

            String data = args[0].toString();
            if (onNotifyListener != null) {
                try {
                    Notification notification = new Gson().fromJson(data, Notification.class);
                    notification.setMessage();
                    notification.setTitle();
                    onNotifyListener.onNotify(notification);
                } catch (Exception e) {
                    Log.e("Socket connection", e.toString());
                }
            }
        }
    }

    private Emitter.Listener onShipperApplyRequest = new NotificationListener();

    private Emitter.Listener onShipperRequireConfirmRequestCompeleted = new NotificationListener();

    private Emitter.Listener onShipperCancelAcceptedResponse = new NotificationListener();

    private Emitter.Listener onStoreAcceptShipper = new NotificationListener();

    private Emitter.Listener onStoreAcceptAnotherShipper = new NotificationListener();

    private Emitter.Listener onStoreConfirmRequestCompeleted = new NotificationListener();

    private Emitter.Listener onStoreCancelAcceptedRequest = new NotificationListener();


//    private Emitter.Listener onShipperApplyRequest = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            String data = args[0].toString();
//            if (onNotifyListener != null) {
//                try {
//                    Notification notification = new Gson().fromJson(data, Notification.class);
//                    notification.setMessage();
//                    notification.setTitle();
//                    onNotifyListener.onNotify(notification);
//                } catch (Exception e) {
//                    Log.e("Socket connection", e.toString());
//                }
//            }
//        }
//    };
//
//    private Emitter.Listener onShipperRequireConfirmRequestCompeleted = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            String data = args[0].toString();
//            if (onNotifyListener != null) {
//                try {
//                    Notification notification = new Gson().fromJson(data, Notification.class);
//                    notification.setMessage();
//                    notification.setTitle();
//                    onNotifyListener.onNotify(notification);
//                } catch (Exception e) {
//                    Log.e("Socket connection", e.toString());
//                }
//            }
//        }
//    };
//
//    private Emitter.Listener onShipperCancelAcceptedResponse = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            String data = args[0].toString();
//            if (onNotifyListener != null) {
//                try {
//                    Notification notification = new Gson().fromJson(data, Notification.class);
//                    notification.setMessage();
//                    notification.setTitle();
//                    onNotifyListener.onNotify(notification);
//                } catch (Exception e) {
//                    Log.e("Socket connection", e.toString());
//                }
//            }
//        }
//    };
//
//    private Emitter.Listener onStoreAcceptShipper = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            String data = args[0].toString();
//            if (onNotifyListener != null) {
//                try {
//                    Notification notification = new Gson().fromJson(data, Notification.class);
//                    notification.setMessage();
//                    notification.setTitle();
//                    onNotifyListener.onNotify(notification);
//                } catch (Exception e) {
//                    Log.e("Socket connection", e.toString());
//                }
//            }
//        }
//    };
//
//    private Emitter.Listener onStoreAcceptAnotherShipper = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            String data = args[0].toString();
//            if (onNotifyListener != null) {
//                try {
//                    Notification notification = new Gson().fromJson(data, Notification.class);
//                    notification.setMessage();
//                    notification.setTitle();
//                    onNotifyListener.onNotify(notification);
//                } catch (Exception e) {
//                    Log.e("Socket connection", e.toString());
//                }
//            }
//        }
//    };
//
//    private Emitter.Listener onStoreConfirmRequestCompeleted = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            String data = args[0].toString();
//            if (onNotifyListener != null) {
//                try {
//                    Notification notification = new Gson().fromJson(data, Notification.class);
//                    notification.setMessage();
//                    notification.setTitle();
//                    onNotifyListener.onNotify(notification);
//                } catch (Exception e) {
//                    Log.e("Socket connection", e.toString());
//                }
//            }
//        }
//    };
//
//    private Emitter.Listener onStoreCancelAcceptedRequest = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            String data = args[0].toString();
//            if (onNotifyListener != null) {
//                try {
//                    Notification notification = new Gson().fromJson(data, Notification.class);
//                    notification.setMessage();
//                    notification.setTitle();
//                    onNotifyListener.onNotify(notification);
//                } catch (Exception e) {
//                    Log.e("Socket connection", e.toString());
//                }
//            }
//        }
//    };

    public void disconnect() {
        socket.disconnect();
    }

    public void setOnNotifyListener(OnNotifyListener onNotifyListener) {
        this.onNotifyListener = onNotifyListener;
    }

    public void connect() {
        socket.connect();
    }

    public Socket getSocket() {
        return socket;
    }
}
