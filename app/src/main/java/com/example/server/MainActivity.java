package com.example.server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);
        Button btn_start = (Button) findViewById(R.id.start_btn);
        btn_start.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent it = new Intent(MainActivity.this,GameActivity.class);
                startActivity(it);
                MainActivity.this.finish();
            }
        });
    }
}


