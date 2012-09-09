package com.monkeydriver.animationtest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainLayoutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_layout, menu);
        return true;
    }
}
