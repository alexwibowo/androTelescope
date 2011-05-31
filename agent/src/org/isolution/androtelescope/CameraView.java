package org.isolution.androtelescope;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    public static final String TAG = CameraView.class.getName();

    private Camera camera;

    private Camera.Size mPreviewSize;

    private Photographer photographer;

    public CameraView(Context context) {
        super(context);
        getHolder().addCallback(this);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    public void initialize() {
        if (this.camera != null && this.photographer != null) {
            requestLayout();
        }
    }

    /**
     * Measure the view and its content to determine the measured width and the measured height. This method is invoked by measure(int, int)
     * and should be overriden by subclasses to provide accurate and efficient measurement of their contents.
     *
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent. The requirements are encoded with View.MeasureSpec.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent. The requirements are encoded with View.MeasureSpec.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // We purposely disregard child measurements because act as a
        // wrapper to a SurfaceView that centers the camera preview instead
        // of stretching it.
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

//        mPreviewSize = CameraUtils.getOptimalPreviewSize(camera, width, height, 0.1);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where to draw.
        try {
            if (camera != null) {
                camera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        if (camera != null) {
            camera.stopPreview();
            photographer.setRunning(false);
            try {
                photographer.join();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * This is called immediately after any structural changes (format or size) have been made to the surface.
     * You should at this point update the imagery in the surface.
     * This method is always called at least once, after surfaceCreated(SurfaceHolder).
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format  The new PixelFormat of the surface.
     * @param width The new width of the surface.
     * @param height The new height of the surface.
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        Camera.Parameters parameters = camera.getParameters();
//        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        parameters.setPictureFormat(ImageFormat.JPEG);
        requestLayout();

        camera.setParameters(parameters);
        camera.startPreview();

        photographer.setRunning(true);
        photographer.start();
    }


}
