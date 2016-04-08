package com.yuyang.fitsystemwindowstestdrawer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 * @author zs
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	
	private static final String TAG = "CrashHandler" ;

	private UncaughtExceptionHandler defaultUEH ;
	
    private static CrashHandler INSTANCE;
    
    private Context mContext;// 程序的Context对象
    
	public static CrashHandler getInstance(){
		if(null == INSTANCE){
			INSTANCE = new CrashHandler();
		}
        return INSTANCE;  
    }  
	
	public void start(Context context){
		this.mContext = context;
		defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex){
		 final Writer result = new StringWriter();
	        final PrintWriter printWriter = new PrintWriter(result);
	        ex.printStackTrace(printWriter);
	        String stacktrace = result.toString();
	        printWriter.close();

	        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
	            defaultUEH.uncaughtException(thread, ex);
	            return;
	        }
	        
	        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"FSO-CloudRefund/";
	        File crashDirFile = new File(sdcardPath+"crash");
	        File logcatDirFile = new File(sdcardPath+"logcat");
	        if(!crashDirFile.exists()){
	        	crashDirFile.mkdirs();
	        }
	        if(!logcatDirFile.exists()){
	        	logcatDirFile.mkdirs();
	        }
	        writeLog(stacktrace, sdcardPath + "crash/crash");
//	        writeLogcat(sdcardPath + "logcat/logcat");

	        defaultUEH.uncaughtException(thread, ex);
	}

	private void writeLog(String log, String name){
        CharSequence timestamp = DateFormat.format("yyyy-MM-dd HH:mm:ss.sss", System.currentTimeMillis());
        String filename = name + "_" + timestamp + ".log";
        
        PackageInfo pinfo = getPackageInfo(mContext);
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: " + pinfo.versionName + "("
		+ pinfo.versionCode + ")\n");
		exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE
		+ "(" + android.os.Build.MODEL + ")\n");
		exceptionStr.append(log);
		Log.e(TAG, exceptionStr.toString());
        /*try {
            FileOutputStream stream = new FileOutputStream(filename);
            OutputStreamWriter output = new OutputStreamWriter(stream);
            BufferedWriter bw = new BufferedWriter(output);

            bw.write(exceptionStr.toString());
            bw.newLine();

            bw.close();
            output.close();
        } catch (IOException e){
            e.printStackTrace();
        }*/
    }

    private void writeLogcat(String name){
        CharSequence timestamp = DateFormat.format("yyyy-MM-dd HH:mm:ss.sss", System.currentTimeMillis());
        String filename = name + "_" + timestamp + ".log";
        String[] args = { "logcat", "-v", "time", "-d" };

        try {
            Process process = Runtime.getRuntime().exec(args);
            InputStreamReader input = new InputStreamReader(
                    process.getInputStream());
            OutputStreamWriter output = new OutputStreamWriter(
                    new FileOutputStream(filename));
            BufferedReader br = new BufferedReader(input);
            BufferedWriter bw = new BufferedWriter(output);
            String line;

            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
            output.close();
            br.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    private PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
			context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
		}

		if (info == null)
			info = new PackageInfo();
		return info;
	}
	
}