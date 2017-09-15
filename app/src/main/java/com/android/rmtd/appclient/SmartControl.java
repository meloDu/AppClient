package com.android.rmtd.appclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.rmtd.smartsdk.IControlTaskCallback;
import com.android.rmtd.smartsdk.ISmartControlBinder;

/**
 * Created by melo on 2017/9/15.
 * 智能家居
 */
public class SmartControl {

    private static Context sContext;
    /**
     * 通信binder对象
     */
    private static ISmartControlBinder mControlBinder;
    /**
     * 监听控制功能
     */
    private static IControlTaskCallback mCallback;

    /**
     * 初始化启动远程服务
     * @param context 上下文
     */
    public static void initSDK(Context context) {
        sContext = context;
        bindService();

    }

    /**
     * 绑定服务
     */
    private static void bindService() {
        Intent service = new Intent();
        service.setAction(Config.SERVICE_ACTION);
        service.setPackage(Config.SERVICE_PACKAGE);
        sContext.bindService(service, mSmartControlConn, sContext.BIND_AUTO_CREATE);

    }

    /**
     * 注销绑定
     */
    public static void unbindService() {
        sContext.unbindService(mSmartControlConn);
    }

    /**
     * 返回控制结果
     * @param result 控制结果
     * @return
     */
    public static String controlResult(int result) {
        try {
            if (mControlBinder == null) {
                bindService();
                // 101  binder为空
                return String.valueOf(101);
            } else {
                mControlBinder.controlResult(result);
                //100 操作成功
                return String.valueOf(100);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传递设备状态
     * @param state  设备状态
     * @return
     */
    public static String equipmentState(int state) {
        try {
            if (mControlBinder == null) {
                bindService();
                // 101  binder为空
                return String.valueOf(101);
            } else {
                mControlBinder.equipmentState(state);
                //100 操作成功
                return String.valueOf(100);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static ServiceConnection mSmartControlConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mControlBinder = ISmartControlBinder.Stub.asInterface(iBinder);
            try {
                if (mCallback!=null){
                    mControlBinder.registerCallback(mCallback);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            try {
                mControlBinder.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mControlBinder = null;
        }
    };

    /**
     * 设置监听器
     * @param iControlTaskCallback 监听器对象
     */
    public static void setControlTaskListener(IControlTaskCallback iControlTaskCallback) {
        mCallback = iControlTaskCallback;
        try {
            if (mControlBinder!=null){
                mControlBinder.registerCallback(iControlTaskCallback);
            }else {
                bindService();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
