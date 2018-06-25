package com.example.varun.exoplayer;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinearLayoutScreenDesign extends AppCompatActivity {



    Switch aSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_screen_design);

        aSwitch=(Switch)findViewById(R.id.switchfirst);
        getSupportActionBar().setTitle("             Settings");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }
}
