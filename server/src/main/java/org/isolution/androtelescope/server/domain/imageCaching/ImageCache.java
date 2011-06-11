package org.isolution.androtelescope.server.domain.imageCaching;

import java.io.IOException;

/**
 * User: agwibowo
 * Date: 8/06/11
 * Time: 11:29 PM
 */
public interface ImageCache {

    /**
     * Persist the image bytes provided, for later retrieval
     *
     * @param imageBytes image bytes
     * @throws IOException any error that occurred during saving of the image
     */
    public void save(byte[] imageBytes)
            throws IOException;

    /**
     * Retrieve the last image persisted
     *
     * @return image bytes
     */
    public byte[] retrieve();
}
