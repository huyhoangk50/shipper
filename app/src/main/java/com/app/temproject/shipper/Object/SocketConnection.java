package com.app.temproject.shipper.Object;

import android.util.Log;

import com.app.temproject.shipper.ProjectVariable.Constant;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Admin on 14/12/2016.
 */

public class SocketConnection {
    OnNotifyListener onNotifyListener;
    private String socketUrl;
    private Socket socket;
    private int role;
    public SocketConnection(String mSocketUrl, int role){
        this.socketUrl = mSocketUrl;
        this.role = role;
        try {
            socket = IO.socket(socketUrl);
            socket.connect();
            if(role == Constant.STORE_ROLE){
                socket.on("shipper-apply-request", onShipperApplyRequest);
                socket.on("shipper-require-confirm-request-compeleted", onShipperRequireConfirmRequestCompeleted);
                socket.on("shipper-cancel-accepted-response", onShipperCancelAcceptedResponse);

            } else {
                socket.on("store-confirm-request-conmpeleted", onStoreConfirmRequestCompeleted);
                socket.on("store-accept-shipper", onStoreAcceptShipper);
                socket.on("store-accept-another-shipper", onStoreAcceptAnotherShipper);
                socket.on("store-confirm-request-compeleted", onStoreConfirmRequestCompeleted);
                socket.on("store-cancel-accepted-request", onStoreCancelAcceptedRequest);

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onShipperApplyRequest = new Emitter.Listener(){
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
        }
    };

    private Emitter.Listener onShipperRequireConfirmRequestCompeleted = new Emitter.Listener(){
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            if(onNotifyListener!=null){
                onNotifyListener.onNotify(data);
            }
        }
    };

    private Emitter.Listener onShipperCancelAcceptedResponse = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            if(onNotifyListener!=null){
                onNotifyListener.onNotify(data);
            }
        }
    };

    private Emitter.Listener onStoreAcceptShipper = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];
            if(onNotifyListener!=null){
                onNotifyListener.onNotify(data);
            }
        }
    };

    private Emitter.Listener onStoreAcceptAnotherShipper = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];
            if(onNotifyListener!=null){
                onNotifyListener.onNotify(data);
            }
        }
    };

    private Emitter.Listener onStoreConfirmRequestCompeleted = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            if(onNotifyListener!=null){
                onNotifyListener.onNotify(data);
            }
        }
    };

    private Emitter.Listener onStoreCancelAcceptedRequest = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            if(onNotifyListener!=null){
                onNotifyListener.onNotify(data);
            }
        }
    };

    public void disconnect(){
        socket.disconnect();
    }
    public void setOnNotifyListener(OnNotifyListener onNotifyListener){
        this.onNotifyListener = onNotifyListener;
    }

    public void connect() {
        socket.connect();
    }

    public Socket getSocket() {
        return socket;
    }
}
