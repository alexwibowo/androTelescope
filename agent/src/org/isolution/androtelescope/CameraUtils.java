package org.isolution.androtelescope;

import android.hardware.Camera;

import java.util.List;

/**
 * User: agwibowo
 * Date: 26/05/11
 * Time: 10:37 PM
 */
public class CameraUtils {

    public static Camera.Size getOptimalPreviewSize(Camera camera, int screenWidth, int screenHeight, double aspectTolerance) {
        double targetRatio = (double) screenWidth / screenHeight;

        int targetHeight = screenHeight;
        Camera.Size optimalSize = getOptimalSize(camera, aspectTolerance, targetRatio, targetHeight);

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            optimalSize = getDefaultSize(camera, targetHeight);
        }
        return optimalSize;
    }

    private static Camera.Size getDefaultSize(Camera camera, int targetHeight) {
        Camera.Size optimalSize = null;

        double minDiff = Double.MAX_VALUE;
        for (Camera.Size size : camera.getParameters().getSupportedPreviewSizes()) {
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        return optimalSize;
    }

    private static Camera.Size getOptimalSize(Camera camera, double aspectTolerance, double targetRatio, int targetHeight) {
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : camera.getParameters().getSupportedPreviewSizes()) {
            double ratio = (double) size.width / size.height;

            // ratio difference is too high from the desired aspect tolerance
            if (Math.abs(ratio - targetRatio) > aspectTolerance) continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        return optimalSize;
    }

}
