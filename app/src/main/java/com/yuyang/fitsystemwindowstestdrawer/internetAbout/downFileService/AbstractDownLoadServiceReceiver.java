package com.yuyang.fitsystemwindowstestdrawer.internetAbout.downFileService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by yuyang on 16/9/14.
 */
public abstract class AbstractDownLoadServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(null != intent){
            if(DownLoadFileService.BROADCAST_ACTION_SUFFIX.equals(intent.getAction())){
                final int status = intent.getIntExtra(DownLoadFileService.STATUS, 0);
                final int fileId = intent.getIntExtra(DownLoadFileService.FILE_ID, -1);
                switch (status) {
                    case DownLoadFileService.STATUS_ERROR:
                        final Exception exception = (Exception) intent.getSerializableExtra(DownLoadFileService.ERROR_EXCEPTION);
                        onError(fileId, exception);
                        break;

                    case DownLoadFileService.STATUS_COMPLETED:
                        onCompleted(fileId);
                        break;

                    case DownLoadFileService.STATUS_IN_PROGRESS:
                        final float progress = intent.getFloatExtra(DownLoadFileService.PROGRESS, 0);
                        onProgress(fileId, progress);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Register this upload receiver. It's recommended to register the receiver in Activity's onResume method.
     *
     * @param context
     */
    public void register(final Context context) {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownLoadFileService.BROADCAST_ACTION_SUFFIX);
        context.registerReceiver(this, intentFilter);
    }

    /**
     * Unregister this upload receiver. It's recommended to unregister the receiver in Activity's onPause method.
     *
     * @param context
     */
    public void unregister(final Context context) {
        context.unregisterReceiver(this);
    }

    /**
     * Called when the upload progress changes.
     *
     * @param fileId
     * @param progress value from 0 to 100
     */
    public abstract void onProgress(final int fileId, final float progress);

    /**
     * Called when an error happens during the upload.
     *
     * @param fileId
     * @param exception
     */
    public abstract void onError(final int fileId, final Exception exception);

    /**
     * Called when the upload is completed successfully.
     *
     * @param fileId
     */
    public abstract void onCompleted(final int fileId);
}
