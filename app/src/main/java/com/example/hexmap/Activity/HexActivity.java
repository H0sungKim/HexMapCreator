package com.example.hexmap.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.hexmap.R;
import com.example.hexmap.View.HexMapView;

public class HexActivity extends AppCompatActivity {

    private double mBlockSize;
    private int    mMapSize;

    private HexMapView mHexMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex);

        mHexMapView = findViewById(R.id.hexmapview);
        Intent intent = getIntent();
        mBlockSize = intent.getDoubleExtra("blockSize", 0);
        mMapSize   = intent.getIntExtra("mapSize", 0);

        mHexMapView.firstSetLst(mBlockSize, mMapSize);

        Log.d("Hosung.Kim", "mBlockSize = " + mBlockSize + ", mMapSize = " + mMapSize);
    }
}