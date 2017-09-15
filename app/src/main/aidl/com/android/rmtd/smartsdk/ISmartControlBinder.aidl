// ISmartControlBinder.aidl
package com.android.rmtd.smartsdk;

import com.android.rmtd.smartsdk.IControlTaskCallback;

interface ISmartControlBinder {

    //控制结果
    int controlResult(int result);
    //设备状态
    int equipmentState(int state);
    //控制指令
    void sendControlMsg(String robotId,String controlCode);
    //注册回调
    void registerCallback(IControlTaskCallback cb);
    //注销回调
    void unregisterCallback(IControlTaskCallback cb);


}
