package com.example.unive.httputil.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unive.httputil.R;
import com.example.unive.httputil.util.MyCallBack;
import com.example.unive.httputil.util.NetClient;


public class MainActivity extends BaseActivity {
    private Button callButton;
    private TextView resultTextView;
    private EditText numberEditView;
    private Handler handler =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1 :
                        String result =(String) msg.obj;
                        Log.e("TAG",result);
                        resultTextView.setText(result);
                        break;
                }
            }
        };


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(MainActivity.this);
                String telRegex = "[1][3578]\\d{9}";
                String phone =numberEditView.getText().toString();
                final String url ="https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+phone;
                if (!TextUtils.isEmpty(phone)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                             NetClient.getNetClient().callNet(url, new MyCallBack() {
                                 @Override
                                 public void onFailure(int code) {
                                  Log.e("TAG","FAILURE");
                                  runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          closeProgressDialog();
                                      }
                                  });

                                 }

                                 @Override
                                 public void onResponse(String json) {
                                     String result =json.toString();
                                     Log.e("TAG","SUCCESS");
                                     Log.e("TAG",json);
                                     Message message=Message.obtain();
                                     message.what=1;
                                     message.obj=result;
                                     handler.sendMessage(message);
                                     runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             closeProgressDialog();
                                         }
                                     });
                                 }
                             });
                        }
                    }).start();
                }else {
                   Toast.makeText(MainActivity.this,"参数错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

      private void initView() {
        numberEditView = (EditText) findViewById(R.id.number_ed);
        resultTextView = (TextView) findViewById(R.id.result_tv);
        callButton = (Button) findViewById(R.id.call_btn);
    }
}

