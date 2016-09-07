package com.santillanj.android1midterm;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton4, floatingActionButton5, floatingActionButton6;
    private TextView myContent;
    private ArrayList<ShoppingCart> shoppingList = new ArrayList<ShoppingCart>();
    private ListView itemListView;
    private int PICK_CONTACT = 1;
    private Double allTotal = 0.0;
    private ShoppingCart shoppingCartAdd = null;
    private ShoppingCartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemListView = (ListView) findViewById(R.id.listView);
        adapter = null;
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.social_floating_menu);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.floating_email);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.floating_sms);
        floatingActionButton6 = (FloatingActionButton) findViewById(R.id.floating_scan);
        floatingActionButton4.setEnabled(false);
        floatingActionButton5.setEnabled(false);
        if(adapter == null){

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Shopping Cart Empty!");
            dialogBuilder.setMessage("Add items to the list by pressing the Scan Item Button");
            final AlertDialog myAlert = dialogBuilder.create();
            myAlert.show();

            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3500);
                        myAlert.dismiss();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }




        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    materialDesignFAM.close(true);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Shopping Cart");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, returnString());
                    emailIntent.setType("message/rfc822");

                    Intent emailChoose = Intent.createChooser(emailIntent, "Email");
                    startActivity(emailChoose);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    materialDesignFAM.close(true);
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("sms_body", returnString());
                    startActivity(smsIntent);



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    materialDesignFAM.close(true);
                    Intent startScannerActivity = new Intent(getApplicationContext(), ScannerActivity.class);
                    startActivityForResult(startScannerActivity, 1);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }

//    public void onClickQR(View v) {
//        try {
//            Intent startScannerActivity = new Intent(getApplicationContext(), ScannerActivity.class);
//            startActivityForResult(startScannerActivity, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onClickSms(View v) {
//        try {
//            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//            smsIntent.setType("vnd.android-dir/mms-sms");
//            smsIntent.putExtra("sms_body", messageReturn());
//            startActivity(smsIntent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }}
//    public void onClickEmail(View v) {
//        try {
//            Intent emailIntent = new Intent(Intent.ACTION_SEND);
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Shopping Cart");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, messageReturn());
//            emailIntent.setType("message/rfc822");
//
//            Intent emailChoose = Intent.createChooser(emailIntent, "Email");
//            startActivity(emailChoose);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public String returnString() {
        ShoppingCart temp = null;
        String message = "";
        for (int i = 0; i < shoppingList.size(); i++) {
            temp = shoppingList.get(i);
            message += "Item: " + temp.getmItem_name() + System.getProperty("line.separator") +
                    "Quantity: " + temp.getmQuantity() + System.getProperty("line.separator") +
                    "Price: " + temp.getmPrice() + System.getProperty("line.separator") +
                    "Total: " + temp.getmTotalPrice() + System.getProperty("line.separator") + System.getProperty("line.separator");
        }
        message += "Total Price of All Items: " + allTotal;
        return message;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {


                String mydata = data.getStringExtra("myData");

                int count = 0;
                ArrayList<String> stringArray = new ArrayList<String>();

                StringTokenizer myTokenizer = new StringTokenizer(mydata, "|||");

                while (myTokenizer.hasMoreTokens()) {
                    stringArray.add(myTokenizer.nextToken());
                }

                shoppingCartAdd = new ShoppingCart(stringArray.get(0), Integer.parseInt(stringArray.get(1)),
                        Double.parseDouble(stringArray.get(2)), Double.parseDouble(stringArray.get(1))
                        * Double.parseDouble(stringArray.get(2)));
                shoppingList.add(shoppingCartAdd);
                allTotal += Double.parseDouble(String.valueOf(shoppingCartAdd.getmTotalPrice()));
                Toast.makeText(this,"Total Price of All Items: "+allTotal, Toast.LENGTH_SHORT).show();
                 adapter = new ShoppingCartAdapter(this, R.layout.list_view, shoppingList);
                itemListView.setAdapter(adapter);
                floatingActionButton4.setEnabled(true);
                floatingActionButton5.setEnabled(true);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
       // Toast.makeText(this, "onStartMain() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "onRestartMain() Called", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
       // Toast.makeText(this, "onResumeMain() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   Toast.makeText(this, "onPauseMain() Called", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStop() {
        super.onStop();
      //  Toast.makeText(this, "onStopMain() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  Toast.makeText(this, "onDestroyMain() Called", Toast.LENGTH_SHORT).show();
    }











}
