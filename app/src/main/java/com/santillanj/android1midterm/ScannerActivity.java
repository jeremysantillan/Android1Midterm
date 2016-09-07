package com.santillanj.android1midterm;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scanner);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {

        String data = result.getText();
        Intent intent = new Intent();


        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        int count = 0;
        ArrayList<String> stringArray = new ArrayList<String>();
        try {
            StringTokenizer myTokenizer = new StringTokenizer(data, "|||");
            while (myTokenizer.hasMoreTokens()) {
                stringArray.add(myTokenizer.nextToken());
            }
        Pattern invalidQuantity = Pattern.compile("^[1-9]\\d*$");
        Pattern invalidPrice = Pattern.compile("\\d{1,9}.\\d{0,2}");
        Matcher matchQuantity = invalidQuantity.matcher(stringArray.get(1));
        Matcher matchPrice = invalidPrice.matcher(stringArray.get(2));

        if (!matchQuantity.matches()) {
            Toast.makeText(this, "Invalid Quantity Format!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Scan Again", Toast.LENGTH_SHORT).show();
            restartScanner();
        } else if (!matchPrice.matches()) {
            Toast.makeText(this, "Invalid Price Format!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Scan Again", Toast.LENGTH_SHORT).show();
            restartScanner();
        } else {
            String message = "Item Name: " + stringArray.get(0) + System.getProperty("line.separator") +
                    "Quantity: " + stringArray.get(1) + System.getProperty("line.separator") +
                    "Price: " + stringArray.get(2) + System.getProperty("line.separator");

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Item Added to List! Item Information:");
            dialogBuilder.setMessage(message);
            AlertDialog myAlert = dialogBuilder.create();
            myAlert.show();
            myThread.start();
            intent.putExtra("myData", data);
            setResult(RESULT_OK, intent);
        }} catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid QR Format!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Scan Again", Toast.LENGTH_SHORT).show();
            restartScanner();
        }
    }
    public void restartScanner(){
        mScannerView.stopCamera();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onStart() {
        super.onStart();
       //Toast.makeText(this, "onStart() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "onRestart() Called", Toast.LENGTH_SHORT).show();
        mScannerView.startCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(this, "onResume() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this, "onPause() Called", Toast.LENGTH_SHORT).show();
        mScannerView.stopCamera();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(this, "onStop() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // Toast.makeText(this, "onDestroy() Called", Toast.LENGTH_SHORT).show();
    }


}

