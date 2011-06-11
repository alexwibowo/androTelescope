package org.isolution.androtelescope.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import org.isolution.androtelescope.server.domain.imageCaching.ImageCacheModule;


/**
 * User: agwibowo
 * Date: 9/06/11
 * Time: 12:42 AM
 */
public class AndroTelescopeServletContextListener
        extends GuiceServletContextListener{

    @Override
    protected Injector getInjector() {
        final ServletModule servletModule = new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/imageSaver").with(ImageReceiverServlet.class);
                serve("/imageViewer").with(ImageViewerServlet.class);
            }
        };

        return Guice.createInjector(new ImageCacheModule(),
                servletModule);
    }

}
