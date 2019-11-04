package com.rachel.neteaselearning2019;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 1001;
    private static String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        //加载大图
        BigView bigView = findViewById(R.id.big_view);
        try {
            InputStream is = getAssets().open("a.png");
            Drawable drawable = Drawable.createFromStream(is, null);
            bigView.setImage(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //自定义不规则控件
        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, view.getTag(view.getId()) + "", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.region_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IrregularRegionView) view).setColors(new int[]{0xFFFF1D22, 0xFFFBD1FF, 0xFFFFB748, 0xFFFF7Abb});
                Toast.makeText(MainActivity.this, view.getTag(view.getId()) + "", Toast.LENGTH_SHORT).show();
            }
        });
        //Glide核心实现
        final LinearLayout mScroll_line = findViewById(R.id.scroll_line);
        findViewById(R.id.singe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigView imageView = new BigView(MainActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                mScroll_line.addView(imageView);
                //设置占位图片
                NetGlide.with(MainActivity.this)
                        .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869573275&di=2bda8dfab570fa2bebb4e438faff2432&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2F02b80dbfb59bc0b87cfa265b6066e9f5200f351c.jpg")
                        .loading(R.mipmap.ic_launcher)
                        .listener(new RequestListener() {
                            @Override
                            public boolean onSuccess(Bitmap bitmap) {
                                Toast.makeText(MainActivity.this, "coming", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public void onFailure() {

                            }
                        }).into(imageView);
                findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 1; i <= 10; i++) {
                            BigView imageView = new BigView(MainActivity.this);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            mScroll_line.addView(imageView);
                            String url = null;
                            switch (i) {
                                case 1:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869187385&di=ff409801caae59bec2784698e0846752&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn17%2F600%2Fw1920h1080%2F20180423%2F22df-fzrwiay8297907.jpg";
                                    break;
                                case 2:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869173690&di=379ec35b0fec7f27cb65a5a121b76417&imgtype=0&src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F10669869291%2F0.jpg";
                                    break;
                                case 3:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869133866&di=76a88c3dad5ba915ccc2e1955fc46718&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F00fd1b3a64b2294fa86c35dd173adc5e9a10a847.jpg";
                                    break;
                                case 4:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869118662&di=f78d9e1a2f8eb4800e5790261073ad55&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2Fbf2ccace9f4cfcdc0df804e1e2c3a35a249c2c3b.jpg";
                                    break;
                                case 5:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869108585&di=a162f5ea6b292ea1350ce023105551d3&imgtype=0&src=http%3A%2F%2Fimg2.c.yinyuetai.com%2Fothers%2Fnews%2F160527%2F0%2F-M-4f74b7d96233fe3990117aa177dd1dad_0x0.jpg";
                                    break;
                                case 6:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869095926&di=5771ac84c694cd6c6caebf4965cc86ac&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2018-06-08%2F5b1a4a2a9fcd0.jpg";
                                    break;
                                case 7:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573463782&di=0fcd2392222b10b5c81b1cee7230bca9&imgtype=jpg&er=1&src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F10653584319%2F0.jpg";
                                    break;
                                case 8:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869046390&di=d92c7aa86a6c0a9312ce346726ef8825&imgtype=0&src=http%3A%2F%2Fdslb.cdn.krcom.cn%2Fimages%2FMSEP-8EU2lWPUU6YQg-UFM7kTQDUwQU9sD2MEA___05dn.jpg";
                                    break;
                                case 9:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572868882098&di=d85c5f09802b426de7d6bb70a89f7846&imgtype=0&src=http%3A%2F%2Fwx2.sinaimg.cn%2Flarge%2F006nnXrXly1g4go8hczztj31hc0u0jun.jpg";
                                    break;
                                case 10:
                                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572869811473&di=bc451ce5b0989f194aebd34dbf669b90&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2Fbe63f30bd0cbd9128c01447b973cb33c545afbe1.png";
                                    break;
                                default:

                            }
                            //设置占位图片
                            NetGlide.with(MainActivity.this)
                                    .load(url + "")
                                    .loading(R.mipmap.ic_launcher)
                                    .into(imageView);

                        }
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }
}
