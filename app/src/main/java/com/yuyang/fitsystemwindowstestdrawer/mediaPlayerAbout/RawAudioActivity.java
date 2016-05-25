package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 录制音频
 *
 * 使用AudioRecord和AudioTrack类可以录音、播放原始音频文件
 *
 * AudioRecord录制声音（<uses-permission android:name="android.permission.RECORD_AUDIO"/>）
 *  首先要创建一个AudioRecord对象，并指定音频源（音频输入源：麦克风）、频率、声道配置、音频编码和缓冲区大小
 *
 * AudioTrack播放音频
 *  创建一个AudioTrack对象，并指定流模式、频率、声道配置、音频编码类型和要播放的音频长度
 *  *由于录制的原始音频文件没有关联元数据，因此将音频的数据属性设为与录制文件相同的值是很重要的！
 */
public class RawAudioActivity extends AppCompatActivity {
    private static final String TAG = RawAudioActivity.class.getName();
    private boolean isRecording = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_audio);
    }

    /**
     * 录制声音
     * @param view
     */
    public void record(View view){
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                isRecording = true;
                record();
                return null;
            }
        };
        task.execute();
    }

    /**
     * 停止录音
     * @param view
     */
    public void stop(View view){
        isRecording = false;
    }

    /**
     * 播放录音（将音频的数据属性设为与录制文件相同的值很重要）
     * @param view
     */
    public void playback(View view){
        AsyncTask<Void,Void,Void> task1 = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                playback();
                return null;
            }
        };
        task1.execute();
    }

    private void record(){
        int frequency = 11025;//频率
        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;//声道
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;//音频编码
        //输出文件
        File file = new File(Environment.getExternalStorageDirectory(), "yuyang.pcm");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            //根据配置计算最小缓冲区大小
            int bufferSize = AudioRecord.getMinBufferSize(frequency,
                    channelConfiguration,
                    audioEncoding);
            short[] buffer = new short[bufferSize];
            //创建一个新的AudioRecord对象来录制音频
            AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,//音频源
                                                    frequency,//频率
                                                    channelConfiguration,//声道
                                                    audioEncoding,//音频编码
                                                    bufferSize); //缓冲区大小
            audioRecord.startRecording();

            while (isRecording){
                int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                for (int i=0; i<bufferReadResult; i++){
                    dos.writeShort(buffer[i]);
                }
            }

            audioRecord.stop();
            dos.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void playback(){
        int frequency = 11025;//频率
        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;//声道
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;//音频编码

        File file = new File(Environment.getExternalStorageDirectory(), "yuyang.pcm");

        //用于存储音轨的short数组（每个short占用16位，即2字节）
        int audioLength = (int) (file.length()/2);
        short[] audio = new short[audioLength];

        try {
            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            int i = 0;
            while (dis.available() > 0){
                audio[i] = dis.readShort();
                i++;
            }
            dis.close();
            //创建和播放新的AudioTrack对象
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,//流模式
                    frequency,//频率
                    channelConfiguration,//声道
                    audioEncoding,//音频编码
                    audioLength,//音频长度
                    AudioTrack.MODE_STREAM);
            audioTrack.play();
            audioTrack.write(audio, 0, audioLength);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
