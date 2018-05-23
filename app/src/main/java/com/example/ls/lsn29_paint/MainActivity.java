package com.example.ls.lsn29_paint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import lsn29_circleprogress.CustomProgressBar;
import lsn29_rectprogress.RoundedRectProgressBar;

public class MainActivity extends AppCompatActivity {

    private CustomProgressBar progressbar;
    private RoundedRectProgressBar rectProgressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressbar=findViewById(R.id.progresssbar);
        rectProgressBar=findViewById(R.id.roundProgreass);
        progressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progress<=100){
                            progress+=2;
                            progressbar.setProgress(progress);
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }).start();
            }
        });
    }

    public void Onclick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress<=100){
                    progress+=2;
                    rectProgressBar.setProgress(progress);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }
}
