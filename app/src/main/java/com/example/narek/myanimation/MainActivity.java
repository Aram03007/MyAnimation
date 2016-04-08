package com.example.narek.myanimation;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AnimPathView view = (AnimPathView) findViewById(R.id.animation_view);
//        Path path = new Path();
//        path.lineTo(200,800);
//        path.addArc(50,100,400,500,20,200);
//        assert view != null;
//        view.setPath(path);

    }
}
