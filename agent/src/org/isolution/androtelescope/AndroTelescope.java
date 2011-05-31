package org.isolution.androtelescope;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

public class AndroTelescope extends Activity {

    private Camera cameraInstance;
    private CameraView cameraView;
    private Photographer photographer;

    /** Called when the activity is first created. */
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cameraView = new CameraView(this);
        setContentView(cameraView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraInstance = Camera.open();
        photographer = new Photographer(cameraInstance, Environment.getExternalStorageDirectory().getAbsolutePath());
        cameraView.setPhotographer(photographer);
        cameraView.setCamera(cameraInstance);
        cameraView.initialize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cleanup();
    }

    private void cleanup() {
        if (cameraInstance != null) {
            cameraView.setCamera(null);
            cameraInstance.release();
            cameraInstance=null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanup();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cleanup();
    }
}