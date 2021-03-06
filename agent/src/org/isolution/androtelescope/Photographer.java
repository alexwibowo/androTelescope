package org.isolution.androtelescope;

import android.hardware.Camera;
import android.util.Log;

/**
 * User: agwibowo
 * Date: 26/05/11
 * Time: 10:54 PM
 */
public class Photographer  extends Thread{
    public static final String TAG = Photographer.class.getName();

    private Camera camera;

    private String cacheDir;

    private boolean running;

    public Photographer(Camera camera, String cacheDir) {
        this.camera = camera;
        this.cacheDir = cacheDir;
    }

        Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] imageData, Camera camera) {
                if (imageData != null) {
                    try {
//                        FileUtilities.storeByteImage(imageData, cacheDir, "chicken");
                        new FileSender().send(imageData, "http://10.1.1.4:8081/androTelescope/imageSaver");
                    } catch (Exception e) {
                        Log.e(TAG, "An error had occurred while sending the file to the destination", e);
                    } finally{
                        camera.startPreview();
                    }
                }
            }
        };


    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while(running){
            Log.i(TAG, "About to take picture...");
            try {
                camera.takePicture(null, mPictureCallback, mPictureCallback);
                Thread.sleep(55000);
            } catch (InterruptedException e) {
            }
        }
    }
}
