package org.isolution.androtelescope.server;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * User: agwibowo
 * Date: 11/06/11
 * Time: 7:24 PM
 */
class ImageReceiverServletHelper {

    /**
     * Read & validate the bytes from the given input stream
     *
     * @param is inputStream to read the bytes from
     * @param contentType mime type of the bytes being read
     * @return array of bytes that were read
     * @throws IOException error that occurred during reading the bytes
     * @throws InvalidImageRequestException validation failure
     */
    byte[] readBytes(InputStream is, String contentType)
            throws IOException, InvalidImageRequestException {
        byte[] bytes = toByteArray(is);

        new ImageRequestValidator(contentType, bytes).validate();
        return bytes;
    }

}
