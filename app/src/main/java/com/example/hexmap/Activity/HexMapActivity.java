package com.example.hexmap.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hexmap.R;
import com.example.hexmap.View.BlockColorView;
import com.example.hexmap.View.BlockRefactor;
import com.example.hexmap.View.HexMapView;

public class HexMapActivity extends AppCompatActivity implements BlockRefactor {

    private int    mMapSize;

    private Button mRefactorBtn;
    private Button mCreateBtn;
    private ImageButton mMapSaveBtn;
    private ImageButton mMapCaptureBtn;
    private HexMapView mHexMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex);

        mHexMapView = findViewById(R.id.hexmapview);
        Intent intent = getIntent();

        mHexMapView.setBlockRefactor(this);
        mRefactorBtn = findViewById(R.id.refactorbtn);
        mCreateBtn = findViewById(R.id.createbtn);
        mMapSaveBtn = findViewById(R.id.mapsavebtn);
        mMapCaptureBtn = findViewById(R.id.capturebtn);
        mMapSaveBtn.bringToFront();

        if (intent.getIntExtra("mode",2) == 0) {
            mMapSize   = intent.getIntExtra("mapSize", 0);
            mHexMapView.firstSetLst(mMapSize);
        } else if (intent.getIntExtra("mode",2) == 1) {
            String mapCode = intent.getStringExtra("mapCode");
            try {
                mHexMapView.loadMap(mapCode);
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Wrong MapCode!!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }




        mRefactorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHexMapView.refactorBlockDialog();
            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHexMapView.createBlockDialog();
            }
        });

        mMapSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hosung.Kim", "ImageButton Activated");
                Log.d("Hosung.Kim", mHexMapView.mapCode());
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", mHexMapView.mapCode());
                clipboardManager.setPrimaryClip(clip);
            }
        });

        mMapCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHexMapView.captureMap(mHexMapView);
            }
        });

        //Log.d("Hosung.Kim", "mBlockSize = " + mBlockSize + ", mMapSize = " + mMapSize);
    }

    @Override
    public void openRefactorDialog(int x, int y, String name, int r, int g, int b) {
        Dialog dialog = new Dialog(HexMapActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.refactordialog);
        dialog.show();

        BlockColorView blockColorView;
        blockColorView = dialog.findViewById(R.id.blockcolorview);
        blockColorView.drawColor(r, g, b);

        TextView xYZTextView;
        xYZTextView = dialog.findViewById(R.id.xyztextview);
        xYZTextView.setText("(" + x + ", " + y + ")");

        EditText nameEditText;
        nameEditText = dialog.findViewById(R.id.nameedittext);
        nameEditText.setText(name);

        EditText redEditText;
        redEditText = dialog.findViewById(R.id.redittext);
        redEditText.setText(String.valueOf(r));

        EditText greenEditText;
        greenEditText = dialog.findViewById(R.id.gedittext);
        greenEditText.setText(String.valueOf(g));

        EditText blueEditText;
        blueEditText = dialog.findViewById(R.id.bedittext);
        blueEditText.setText(String.valueOf(b));

        ColorTextWatcher redColorTextWatcher = new ColorTextWatcher(blockColorView, redEditText, greenEditText, blueEditText, ColorTextWatcher.COLOR_INDEX_R);
        ColorTextWatcher greenColorTextWatcher = new ColorTextWatcher(blockColorView, redEditText, greenEditText, blueEditText, ColorTextWatcher.COLOR_INDEX_G);
        ColorTextWatcher blueColorTextWatcher = new ColorTextWatcher(blockColorView, redEditText, greenEditText, blueEditText, ColorTextWatcher.COLOR_INDEX_B);

        redEditText.addTextChangedListener(redColorTextWatcher);
        greenEditText.addTextChangedListener(greenColorTextWatcher);
        blueEditText.addTextChangedListener(blueColorTextWatcher);

        Button deleteBtn;
        deleteBtn = dialog.findViewById(R.id.deletebtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHexMapView.deleteBlock();
                dialog.dismiss();
            }
        });

        Button saveBtn;
        saveBtn = dialog.findViewById(R.id.savebtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mHexMapView.applyChange(nameEditText.getText().toString(), Integer.parseInt(redEditText.getText().toString()), Integer.parseInt(greenEditText.getText().toString()), Integer.parseInt(blueEditText.getText().toString()));
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "저장 실패!!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void openCreateDialog() {
        Dialog dialog = new Dialog(HexMapActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.createdialog);
        dialog.show();

        EditText xEditText;
        xEditText = dialog.findViewById(R.id.xedittext);

        EditText yEditText;
        yEditText = dialog.findViewById(R.id.yedittext);

        Button createBtn;
        createBtn = dialog.findViewById(R.id.createdialogbtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mHexMapView.createBlock(Integer.parseInt(xEditText.getText().toString()), Integer.parseInt(yEditText.getText().toString()));
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "생성 실패!!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

// 사용자가 R, G, B 값을 입력할때마다 값을 체크해서 R, G, B 값을 적절하게 설정하고 view에 알맞은 색을 칠하기 위한 클래스

class ColorTextWatcher implements TextWatcher {
    public static final int COLOR_INDEX_R = 0;
    public static final int COLOR_INDEX_G = 1;
    public static final int COLOR_INDEX_B = 2;

    public static final int COLOR_MAX = 255;
    public static final int COLOR_MIN = 0;

    public BlockColorView mBlockColorView;
    public EditText mREditText;
    public EditText mGEditText;
    public EditText mBEditText;
    public int      mColorIndex;

    public ColorTextWatcher(BlockColorView blockColorView, EditText redEditText, EditText greenEditText, EditText blueEditText, int colorIndex) {
        mBlockColorView = blockColorView;
        mREditText = redEditText;
        mGEditText = greenEditText;
        mBEditText = blueEditText;
        mColorIndex = colorIndex;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int redValue;
        int greenValue;
        int blueValue;
        try {
            redValue = Integer.parseInt(mREditText.getText().toString());
        } catch (Exception ignored) {
            redValue = 0;
        }
        try {
            greenValue = Integer.parseInt(mGEditText.getText().toString());
        } catch (Exception ignored) {
            greenValue = 0;
        }
        try {
            blueValue = Integer.parseInt(mBEditText.getText().toString());
        } catch (Exception ignored) {
            blueValue = 0;
        }

        int value;
        try {
            value = Integer.parseInt(charSequence.toString());
        } catch (Exception ignored) {
            value = 0;
        }

        switch (mColorIndex) {
            case COLOR_INDEX_R:
                redValue = value;
                break;
            case COLOR_INDEX_G:
                greenValue = value;
                break;
            case COLOR_INDEX_B:
                blueValue = value;
                break;
        }

        if (value >= 0 && value <= 255) {
            mBlockColorView.drawColor(redValue, greenValue, blueValue);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        int value;
        EditText editText = null;
        switch (mColorIndex) {
            case COLOR_INDEX_R:
                editText = mREditText;
                break;
            case COLOR_INDEX_G:
                editText = mGEditText;
                break;
            case COLOR_INDEX_B:
                editText = mBEditText;
                break;
        }
        try {
            value = Integer.parseInt(editText.getText().toString());
        } catch (Exception ignored) {
            value = 0;
        }

        if (value > COLOR_MAX) {
            editText.setText(String.valueOf(COLOR_MAX));
        } else if (value < COLOR_MIN) {
            editText.setText(String.valueOf(COLOR_MIN));
        }
    }
}