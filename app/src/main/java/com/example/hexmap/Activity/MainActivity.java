package com.example.hexmap.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hexmap.R;

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

    private Button       mButton;
    private EditText     mEditText1;
    private EditText     mEditText2;

    private double mBlockSize;
    private int    mMapSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button);
        mEditText1 = findViewById(R.id.editTextNumber1);
        mEditText2 = findViewById(R.id.editTextNumber2);

        mButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mBlockSize = Double.parseDouble(mEditText1.getText().toString());
                 mMapSize   = Integer.parseInt(mEditText2.getText().toString());

                 Intent intent = new Intent(MainActivity.this, HexActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 intent.putExtra("blockSize", mBlockSize);
                 intent.putExtra("mapSize", mMapSize);
                 getBaseContext().startActivity(intent);

             }
        });


    }
}