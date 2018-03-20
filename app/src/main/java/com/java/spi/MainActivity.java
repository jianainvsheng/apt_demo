package com.java.spi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.annotation.Test1Annotation;
import com.example.annotation.Test2Annotation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Test2Annotation
    public String test;

    public void onClick(View view){

        switch (view.getId()){

            case R.id.text:

                break;
        }
    }

    @Test1Annotation
    public static void test(){

    }
}
