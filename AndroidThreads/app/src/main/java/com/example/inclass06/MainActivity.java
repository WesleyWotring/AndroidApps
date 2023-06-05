package com.example.inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * InClass06
 * InClass06
 * Wesley Wotring and Zachary Hall
 */

public class MainActivity extends AppCompatActivity {

    Handler handler;

    ArrayAdapter<Double> adapter;
    ArrayList<Double> heavyWorkList = new ArrayList<Double>();
    ListView listView;
    SeekBar seekBar;
    TextView taskProgress, averageAmt, taskAmt;
    Button generate;
    ProgressBar progressBar;
    TextView average;
    int tasks;
    ExecutorService threadPool;
    final String TAG = "demo";
    double averageCalc;
    double sum;
    String averageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        averageAmt = findViewById(R.id.textViewAverageAmt);
        taskAmt = findViewById(R.id.textViewTaskAmount);
        taskProgress = findViewById(R.id.textViewTaskProgress);
        listView = findViewById(R.id.taskList);
        average = findViewById(R.id.textViewAverage);
        progressBar = findViewById(R.id.progressBar);
        generate = findViewById(R.id.buttonGenerate);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case DoWork.STATUS_START:
                        heavyWorkList.clear();
                        sum = 0;
                        averageCalc = 0;
                        progressBar.setProgress(0);
                    taskProgress.setText(0 + "/" + tasks);



                    generate.setVisibility(View.INVISIBLE);
                    Log.d("demo", getString(R.string.start));

                    break;
                case DoWork.STATUS_STOP:
                    generate.setVisibility(View.VISIBLE);
                    Log.d("demo", getString(R.string.stop));


                    break;
                case DoWork.STATUS_PROGRESS:
                    progressBar.setMax(tasks);
                    progressBar.setProgress(heavyWorkList.size() + 1);
                    //update TaskProgress
                    //update AverageAmount
                    sum = sum + msg.getData().getDouble(DoWork.OBJ_KEY);

                    taskProgress.setText((heavyWorkList.size() + 1) + "/" + tasks);

                    averageCalc = sum / (heavyWorkList.size() + 1);

                    averageAmt.setText(String.valueOf(averageCalc));


                    heavyWorkList.add(msg.getData().getDouble(DoWork.OBJ_KEY));

                    break;
            }

                return false;
            }
        });

        taskAmt.setText(seekBar.getProgress() + " " + getString(R.string.times));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged:" + progress);
                String progressBar2 = String.valueOf(progress + " "+ getString(R.string.times));
                //seekProgress.setText(progressBar2 + "%");
                taskAmt.setText(progressBar2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadPool = Executors.newFixedThreadPool(2);
                progressBar.setVisibility(View.VISIBLE);
                averageAmt.setVisibility(View.VISIBLE);
                average.setVisibility(View.VISIBLE);
                taskProgress.setVisibility(View.VISIBLE);

               tasks = seekBar.getProgress();

                //Thread thread = new Thread(new DoWork(), "Thread1");
                //thread.start();

               threadPool.execute(new DoWork(tasks));
               heavyWorkList.clear();
                adapter = new ArrayAdapter<Double>(MainActivity.this,android.R.layout.simple_list_item_1, android.R.id.text1,heavyWorkList);
                listView.setAdapter(adapter);
                averageAmt.setText(String.valueOf(0));

            }
        });

    }


    class DoWork implements Runnable {
        static final int STATUS_START = 0x00;
        static final int STATUS_STOP = 0x01;
        static final int STATUS_PROGRESS = 0x02;
        int taskAmount;

        static final String OBJ_KEY = "OBJECT";

        DoWork(int tasks){
            taskAmount = tasks;
        }

        @Override
        public void run() {
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);

            for(int i = 0; i < taskAmount; i++){


                Bundle bundle = new Bundle();
                bundle.putSerializable(OBJ_KEY, HeavyWork.getNumber());
                Message message = new Message();
                message.what = STATUS_PROGRESS;
                message.setData(bundle);
                handler.sendMessage(message);
            }


            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);



            //heavyWorkList.add(HeavyWork.getNumber());





            //message.setData(bundle);
            // message.obj = heavyWork;
            //handler.sendMessage(message);

        }
    }

}