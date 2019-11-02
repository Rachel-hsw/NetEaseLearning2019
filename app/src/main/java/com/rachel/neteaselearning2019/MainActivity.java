package com.rachel.neteaselearning2019;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BigView bigView=findViewById(R.id.big_view);
        try {
            InputStream is=getAssets().open("a.png");
            bigView.setImage(is);
        } catch (IOException e) {
           e.printStackTrace();
        }
        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, view.getTag(view.getId()) + "", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.region_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IrregularRegionView)view).setColors(new int[]{0xFFFF1D22, 0xFFFBD1FF, 0xFFFFB748, 0xFFFF7Abb});
                Toast.makeText(MainActivity.this, view.getTag(view.getId()) + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
