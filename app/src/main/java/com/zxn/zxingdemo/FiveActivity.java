package com.zxn.zxingdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zxn.zxing.activity.CodeUtils;

public class FiveActivity extends BaseActivity {

    public EditText editText = null;
    public Button button = null;
    public ImageView imageView = null;

    public Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {

        editText = (EditText) findViewById(R.id.edit_content);
        button = (Button) findViewById(R.id.button_content);

        imageView = (ImageView) findViewById(R.id.image_content);

        /**
         * 生成二维码图片
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textContent = editText.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(FiveActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                editText.setText("");
                mBitmap = CodeUtils.createBarcode(textContent, 1000, 200, false);
                imageView.setImageBitmap(mBitmap);
            }
        });

    }

}
