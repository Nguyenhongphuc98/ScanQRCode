package com.example.nguyenhongphuc98.scanqrcode;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private IntentIntegrator QRScan;
    TextView tvResult,tvSurplus;
    Button BtScan;
    int surplus=1000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        QRScan =new IntentIntegrator(this);
        tvResult=findViewById(R.id.TvResultQRCode);
        BtScan =findViewById(R.id.BtScan);
        tvSurplus=findViewById(R.id.TvSurplus);

        BtScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRScan.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult r =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(r!=null){
            if(r.getContents()==null){
                Toast.makeText(this,"result not found",Toast.LENGTH_SHORT).show();
            }
            else {
                try{
                    JSONObject object =new JSONObject(r.getContents());
                    String price=object.getString("price");
                    tvResult.setText("Đã thanh toán: "+object.getString("name")+" giá: "+price);
                    CaculatePrice(price);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    void CaculatePrice(String price){
        int p= Integer.parseInt(price);
        surplus-=p;
        tvSurplus.setText(String.valueOf(surplus));
       // tvResult.setText(object.getString("price"));
    }
}
