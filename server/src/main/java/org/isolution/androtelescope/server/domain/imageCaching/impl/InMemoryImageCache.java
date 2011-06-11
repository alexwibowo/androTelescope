package org.isolution.androtelescope.server.domain.imageCaching.impl;

import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import org.isolution.androtelescope.server.domain.imageCaching.ImageCache;

import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link ImageCache} which saves the image bytes in memory.
 * 
 * User: agwibowo
 * Date: 8/06/11
 * Time: 2:26 AM
 */
@Singleton
public class InMemoryImageCache implements ImageCache {
    private static final Logger LOG = Logger.getLogger(InMemoryImageCache.class.getName());

    private byte[] bytes;

    public InMemoryImageCache() { }

    @Override
    public void save(byte[] imageBytes) {
        LOG.debug(MessageFormat.format("Saving image to memory. Image size: {0}", imageBytes.length));
        checkNotNull(imageBytes);
        checkArgument(imageBytes.length > 0);
        this.bytes = imageBytes;
    }

    @Override
    public byte[] retrieve() {
        return bytes;
    }
}
