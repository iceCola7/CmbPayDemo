package com.cxz.cmbpaydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private CmbWebView webView;

    /**
     * 签约地址
     */
    private static final String signUrl = "http://121.15.180.66:801/mobilehtml/DebitCard/M_NetPay/OneNetRegister/NP_BindCard.aspx";

    /**
     * 支付地址地址
     */
    private static final String payUrl = "http://121.15.180.66:801/NetPayment/BaseHttp.dll?MB_EUserPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        webView = findViewById(R.id.webview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl();
            }
        });

        loadUrl();

    }

    private void loadUrl() {
        String jsonData = "jsonRequestData=" + getJsonRequestData(
                "2017031700",
                "10.00",
                "https://www.baidu.com/",
                "https://www.baidu.com/",
                "https://www.baidu.com/",
                "20170317001",
                "20170317002"
        );
        webView.postUrl(payUrl, jsonData.getBytes());
    }


    /**
     * 获得支付请求的参数
     *
     * @param orderId          订单号
     * @param due              金额
     * @param signInformUrl    签约成功通知后台的地址
     * @param payInformUrl     支付成功通知后台的地址
     * @param returnUrl        支付成功的返回商户的地址
     * @param agrNo            协议号
     * @param merchantSerialNo 协议开通的流水号
     * @return
     */
    private String getJsonRequestData(String orderId, String due, String signInformUrl,
                                      String payInformUrl, String returnUrl, String agrNo,
                                      String merchantSerialNo) {
        CmbBaseJsonRequestData<CmbPayReqData> requestData = new CmbBaseJsonRequestData<>();
        CmbPayReqData ywtPayReqData = new CmbPayReqData(
                CmbUtil.getCurrentTime(),
                CmbConfig.branchNo,
                CmbConfig.merchentNo,
                CmbUtil.getYWTOrderTime(),
                orderId,
                due,
                signInformUrl,
                payInformUrl,
                agrNo,
                merchantSerialNo,
                returnUrl
        );
        requestData.setSign(CmbUtil.getSign(ywtPayReqData));
        requestData.setReqData(ywtPayReqData);
        String json = new Gson().toJson(requestData);
        System.out.println(json);
        return json;
    }

}
