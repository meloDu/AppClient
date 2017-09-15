// IControlTaskCallback.aidl
package com.android.rmtd.smartsdk;

// Declare any non-default types here with import statements

interface IControlTaskCallback {

    void controlMsg(String robotId,String controlCode);
}
