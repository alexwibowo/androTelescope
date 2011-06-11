package org.isolution.androtelescope.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.isolution.androtelescope.server.domain.imageCaching.ImageCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: agwibowo
 * Date: 8/06/11
 * Time: 2:37 AM
 */
@Singleton
public class ImageViewerServlet extends HttpServlet {
    public static final Logger LOG = Logger.getLogger(ImageViewerServlet.class.getName());

    private ImageCache imageCache;

    @Inject
    public ImageViewerServlet(ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    @Override
    public String getServletInfo() {
        return "A servlet for viewing images.";
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        LOG.info("Viewing image..");
        byte[] imageBytes = imageCache.retrieve();

        if (imageBytes != null && imageBytes.length > 0) {
            IOUtils.write(imageBytes, httpServletResponse.getOutputStream());
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("image/jpeg");
            httpServletResponse.setContentLength(imageBytes.length);
        }else{
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

        LOG.info("Finished serving image");
    }
}
