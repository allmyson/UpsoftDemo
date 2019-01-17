package com.upsoft.demo.framework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.AudioRecorder2Mp3Util;
import com.upsoft.demo.framework.api.NativeUtil;
import com.upsoft.demo.framework.api.RecordStreamListener;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.util.FileUtil;
import com.upsoft.sdk.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：MainActivity<br>
 * 摘要：<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：董杰科<br>
 * 完成日期：2016/11/14<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：董杰科<br>
 * 完成日期：2016/11/14<br>
 */

public class AudioActivity extends FragmentActivity implements View.OnClickListener {
    private Context mContext;
    /**
     * 录音声音大小显示
     */
    private ImageView surfaceView;
    /**
     * 开始录音
     */
    private Button startButton;
    /**
     * 继续录音
     */
    private Button contineButton;
    /**
     * 停止录音
     */
    private Button stopButton;
    /**
     * 录音文件地址
     */
    private String aduioFilePath;
    private boolean isRunning = false;
    private double VOLUME = 0;
    private Handler mHandler = new Handler();
    /*** 录音长短 ***/
    private TextView tvHinttext2;
    /*** 录音时长 ***/
    private int voiceDuration;
    /*** 录音开始时间 ***/
    private long voiceBeginTime;
    private Date startTime;
    private Date endTime;
    /**
     * 采样频率
     */
    private static final int SAMPLE_RATE = 8000;
    /**
     * 转换
     */
    private boolean convertOk = false;
    private AudioRecorder2Mp3Util util;
    private String filepatnMp3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_d);
        mContext = this;
        initView();
    }

    private void initView() {
        surfaceView = (ImageView) findViewById(R.id.surfaceView);
        tvHinttext2 = (TextView) findViewById(R.id.activity_audiostime);
        startButton = (Button) findViewById(R.id.audio_start_button);
        contineButton = (Button) findViewById(R.id.audio_contine_button);
        stopButton = (Button) findViewById(R.id.audio_stop_button);
        startButton.setOnClickListener(this);
        contineButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        findViewById(R.id.audio_play_file).setOnClickListener(this);//
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.audio_start_button) {
            isRunning = true;
            try {
                FileUtil.createSDDir(Constant.AUDIO_PATH);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            String filedir = Constant.AUDIO_PATH;
            filepatnMp3 = filedir + getNowTimeStr(2);
            aduioFilePath = filedir + getNowTimeStr(4);
            if (util == null) {
                util = new AudioRecorder2Mp3Util(null, aduioFilePath, filepatnMp3);
            }
            util.startRecording();
            mHandler.postDelayed(mPollTask, 5);//
            //开始动画
            surfaceView.setImageResource(R.drawable.lottery_animlist);
            AnimationDrawable animationDrawable = (AnimationDrawable) surfaceView.getDrawable();
            animationDrawable.start();
            startButton.setVisibility(View.GONE);
            startTime = new Date();
            stopButton.setText("停止");
            stopButton.setEnabled(true);
            if (isRunning) {
                voiceDuration = 0;
                tvHinttext2.setText("");
                voiceBeginTime = System.currentTimeMillis();
            }

        } else if (i == R.id.audio_pause_button) {
            isRunning = false;
            NativeUtil.pauseAudioRecorder();
            contineButton.setVisibility(View.VISIBLE);
        } else if (i == R.id.audio_contine_button) {
            isRunning = true;
            NativeUtil.continueAudioRecorder(new RecordStreamListener() {
                @Override
                public void recordOfByte(byte[] data, int begin, int end) {
                }

                @Override
                public void recordAmplitude(double AmlitudeMax) {
                    VOLUME = AmlitudeMax;
                }
            });
            mHandler.postDelayed(mPollTask, 5);
            contineButton.setVisibility(View.GONE);
            stopButton.setText("停止");
        } else if (i == R.id.audio_stop_button) {
            if (stopButton.getText().toString().equals("停止")) {
                stopButton.setText("提交");
                isRunning = false;
                AnimationDrawable animationDrawable = (AnimationDrawable) surfaceView.getDrawable();
                animationDrawable.stop();
                surfaceView.setImageDrawable(getResources().getDrawable(R.mipmap.icon_voice_suspend));
                startButton.setVisibility(View.VISIBLE);
                contineButton.setVisibility(View.GONE);
                endTime = new Date();
                voiceDuration = (int) Math.ceil((System.currentTimeMillis() - voiceBeginTime) / 1000);
                tvHinttext2.setText(voiceDuration + "''");
                if (Build.VERSION.SDK_INT >= 1) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            convertOk = util.stopRecordingAndConvertFile();
                            util.close();
                            util = null;
                        }
                    }.start();
                }
            } else {
                if (convertOk) {
                    intentData();
                } else {
                    ToastUtil.show(mContext, "请稍后点击", 1);
                }
            }
        } else if (i == R.id.audio_play_file) {
            stopButton.setText("停止");
            NativeUtil.playAduio(mContext, aduioFilePath);
        }
    }


    private void intentData() {
        Intent intent = new Intent();
        intent.putExtra("filePath", filepatnMp3);
        intent.putExtra("fileLength", new File(filepatnMp3).length());
        String filename = filepatnMp3.substring(filepatnMp3.lastIndexOf("/") + 1, filepatnMp3.length());
        intent.putExtra("filename", filename);
        intent.putExtra("fileDuration", endTime.getTime() - startTime.getTime());
        // 设置结果，并进行传送
        this.setResult(Activity.RESULT_OK, intent);
        finish();
    }


    private Runnable mPollTask = new Runnable() {
        public void run() {
            if (isRunning) {
                updateDisplay(VOLUME);
                mHandler.postDelayed(mPollTask, 200);//
            }
        }
    };


    /**
     * 更新声音大小-图标显示
     */
    private void updateDisplay(double signalEMA) {
        voiceDuration = (int) Math.ceil((System.currentTimeMillis() - voiceBeginTime) / 1000);
        tvHinttext2.setText(voiceDuration + "''");
    }


    /**
     * 系统自带返回
     */
    @Override
    public void onBackPressed() {
        onBack(null);
    }


    /**
     * 返回
     *
     * @param view
     * @author： WX
     */
    public void onBack(View view) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NativeUtil.destoryAudioRecorder();
    }


    public static String getNowTimeStr(int type) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        String result = "";
        if (type == 1) {
            result = sf.format(new Date()).toString() + ".jpg";
        } else if (type == 2) {
            result = sf.format(new Date()).toString() + ".mp3";
        } else if (type == 3) {
            result = sf.format(new Date()).toString() + ".mp4";
        } else if (type == 4) {
            result = sf.format(new Date()).toString() + ".raw";
        }
        return "/" + result;
    }
}
