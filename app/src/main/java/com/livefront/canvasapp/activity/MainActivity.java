package com.livefront.canvasapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, MainFragment.newInstance())
                .commit();
    }
}
