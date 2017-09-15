package com.android.rmtd.appclient;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.rmtd.smartsdk.IControlTaskCallback;

public class MainActivity extends AppCompatActivity {

    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn_OK);

        SmartControl.setControlTaskListener(mCallback);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = SmartControl.controlResult(10000);
                Toast.makeText(getApplicationContext(), "操作结果：" + i, Toast.LENGTH_LONG).show();
            }
        });
    }

    private IControlTaskCallback mCallback = new IControlTaskCallback.Stub() {
        @Override
        public void controlMsg(String robotId, String controlCode) throws RemoteException {
            Log.i("tag", "robotId=" + robotId + ",controlCode=" + controlCode);
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmartControl.unbindService();
    }

}
