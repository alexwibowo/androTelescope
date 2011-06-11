package org.isolution.androtelescope.server.domain.imageCaching.impl;

import org.isolution.androtelescope.server.domain.imageCaching.ImageCache;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.write;

/**
 * User: agwibowo
 * Date: 8/06/11
 * Time: 11:30 PM
 */
public class FileSystemImageCache implements ImageCache {

    @Override
    public void save(byte[] imageBytes)
            throws IOException {
        BufferedOutputStream bufferedWriter = null;
        try {
            bufferedWriter = new BufferedOutputStream(new FileOutputStream(new File("/tmp/chicken.jpg")));
            write(imageBytes, bufferedWriter);
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.flush();
                closeQuietly(bufferedWriter);
            }
        }
    }

    @Override
    public byte[] retrieve() {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
