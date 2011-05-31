package org.isolution.androtelescope;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;

/**
 * User: agwibowo
 * Date: 26/05/11
 * Time: 11:08 PM
 */
public class FileUtilities {

    /*
    * Convertie un byte[] en image et l'enregistre sur la carte SD dans le dossier image
    */
    public static boolean storeByteImage(byte[] imageData, String folder, String pictureName) {
        File sdImageMainDirectory = new File(folder);
        BufferedOutputStream bos = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 5;

            Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

            String filepath = sdImageMainDirectory.toString() + "/" + pictureName + ".jpg";
            bos = new BufferedOutputStream(new FileOutputStream(filepath));


            image.compress(Bitmap.CompressFormat.JPEG, 95, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (Throwable ignore) {
                }
            }
        }
        return true;
    }

    public static boolean deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
        return (file.delete());
    }
}

