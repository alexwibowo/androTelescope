package org.isolution.androtelescope.server.domain.imageCaching;

import com.google.inject.AbstractModule;
import org.isolution.androtelescope.server.domain.imageCaching.ImageCache;
import org.isolution.androtelescope.server.domain.imageCaching.impl.InMemoryImageCache;

/**
 * User: agwibowo
 * Date: 9/06/11
 * Time: 12:31 AM
 */
public class ImageCacheModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(ImageCache.class).to(InMemoryImageCache.class);
    }
}
