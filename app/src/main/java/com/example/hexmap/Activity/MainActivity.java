package com.example.hexmap.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hexmap.R;

import java.util.ArrayList;

//  ================================================================================================
/**
 * HexMap
 *
 * Created by Hosung.Kim
 * Final Update : 2021.06.23.00.43
 *
 * AppName   : HexMap Creator
 * Language  : Java
 * Tool      : Android Studio
 * Developer : Hosung.Kim
 * Contact   : hyongak516@mail.hongik.ac.kr
 * Github    : https://github.com/hyongak516
 */
//  ================================================================================================

public class MainActivity extends AppCompatActivity {

    private Button       mButton1;
    private Button       mButton2;
    private Button       mButton3;
    private Button       mInfoBtn;

    private int    mMapSize;
    private int    infoBtnCount;

    private ArrayList<String> mPermission = new ArrayList<String>();

    private Dialog mDialog1;


    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            mDialog1.dismiss();
        } catch (Exception e) {
            Log.d("Hosung.Kim", e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Final Update =============================================
        Log.d("Hosung.Kim", "VERSION ==> 2021.08.03.20.46");
        // ==========================================================

        mPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        for (int i=0; i<mPermission.size(); i++) {
            if (ContextCompat.checkSelfPermission(this, mPermission.get(i)) == PackageManager.PERMISSION_GRANTED) {
                mPermission.remove(i);
            }
        }


        ActivityCompat.requestPermissions(this, mPermission.toArray(new String[0]), 1000);

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, mPermission, 1000);
        }*/





        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mInfoBtn = findViewById(R.id.infoButton);

        mButton1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 /*try {
                     mMapSize   = Integer.parseInt(mEditText.getText().toString());
                     if (mMapSize > 0 && mMapSize <= 20) {
                         Intent intent = new Intent(MainActivity.this, HexMapActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         intent.putExtra("mapSize", mMapSize);
                         getBaseContext().startActivity(intent);
                     } else if (mMapSize > 20) {
                         Toast.makeText(getBaseContext(), "Map Size가 너무 큽니다.", Toast.LENGTH_LONG).show();
                     } else {
                         Toast.makeText(getBaseContext(), "올바른 수를 입력하세요.", Toast.LENGTH_LONG).show();
                     }
                 } catch (Exception e) {
                     Toast.makeText(getBaseContext(), "올바른 수를 입력하세요.\nError ↓\n" + e.toString(), Toast.LENGTH_LONG).show();
                 }*/
                 mDialog1 = new Dialog(MainActivity.this);
                 mDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                 mDialog1.setContentView(R.layout.dialog_main01);
                 mDialog1.show();
                 EditText mDialogEditText = mDialog1.findViewById(R.id.dialogedittext1);
                 Button mDialogButton = mDialog1.findViewById(R.id.dialogbtn1);
                 mDialogButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         try {
                             mMapSize = Integer.parseInt(mDialogEditText.getText().toString());
                             if (mMapSize > 0 && mMapSize <= 25) {
                                 mDialogButton.setText("Loading...");
                                 Intent intent = new Intent(MainActivity.this, HexMapActivity.class);
                                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                 intent.putExtra("mode", 0);
                                 intent.putExtra("mapSize", mMapSize);
                                 getBaseContext().startActivity(intent);
                                 //mDialog1.dismiss();
                             } else if (mMapSize > 20) {
                                 Toast.makeText(getBaseContext(), "Map Size가 너무 큽니다.", Toast.LENGTH_LONG).show();
                             } else {
                                 Toast.makeText(getBaseContext(), "올바른 수를 입력하세요.", Toast.LENGTH_LONG).show();
                             }
                         } catch (Exception e) {
                             Toast.makeText(getBaseContext(), "올바른 수를 입력하세요.\nError ↓\n" + e.toString(), Toast.LENGTH_LONG).show();
                         }
                     }
                 });


             }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog1 = new Dialog(MainActivity.this);
                mDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog1.setContentView(R.layout.dialog_main02);
                mDialog1.show();
                EditText mDialogEditText = mDialog1.findViewById(R.id.dialogedittext2);
                Button mDialogButton = mDialog1.findViewById(R.id.dialogbtn2);
                mDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mMapCode = mDialogEditText.getText().toString();
                        if (mMapCode.contains("HexMapCode")) {
                            mDialogButton.setText("Loading...");
                            Intent intent = new Intent(MainActivity.this, HexMapActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("mode", 1);
                            intent.putExtra("mapCode", mMapCode);
                            getBaseContext().startActivity(intent);
                            //mDialog1.dismiss();
                        } else {
                            Toast.makeText(getBaseContext(), "올바른 MapCode를 입력하세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapCodeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
            }
        });

        mInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoBtnCount = infoBtnCount + 1;
                if (infoBtnCount > 9) {
                    infoBtnCount = 0;
                    Dialog mPasswordDialog = new Dialog(MainActivity.this);
                    mPasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mPasswordDialog.setContentView(R.layout.dialog_password);
                    mPasswordDialog.show();
                    Button mDialogBtn = mPasswordDialog.findViewById(R.id.dialogbutton);
                    EditText mDialogEditText = mPasswordDialog.findViewById(R.id.dialogedittext);

                    mDialogBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String mPassword = String.valueOf(mDialogEditText.getText());
                            if (mPassword.equals("Hosung.Kim#HexMapCreator")) {
                                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getBaseContext().startActivity(intent);
                                mPasswordDialog.dismiss();

                            } else {
                                TextView mDialogTextView = mPasswordDialog.findViewById(R.id.dialogtextview);
                                mDialogTextView.setText("잘못된 Password입니다. 다시 입력하세요.");
                                mDialogEditText.setText("");
                            }
                        }
                    });
                }
            }
        });



    }

}