package org.isolution.androtelescope.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import org.isolution.androtelescope.server.domain.imageCaching.ImageCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple servlet for receiving image & save it to a local drive
 *
 * User: agwibowo
 * Date: 31/05/11
 * Time: 9:24 PM
 */
@Singleton
public class ImageReceiverServlet  extends HttpServlet{
    public static final Logger LOG = Logger.getLogger(ImageReceiverServlet.class.getName());

    private ImageCache imageCache;

    private ImageReceiverServletHelper servletHelper;

    @Inject
    public ImageReceiverServlet(ImageCache imageCache, ImageReceiverServletHelper servletHelper) {
        this.imageCache = imageCache;
        this.servletHelper = servletHelper;
    }

    @Override
    public String getServletInfo() {
        return "A servlet for saving images.";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Received incoming image..");

//        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        int returnStatus;
        try {
            imageCache.save(servletHelper.readBytes(request.getInputStream(), request.getContentType()));
            returnStatus = HttpServletResponse.SC_ACCEPTED;
        } catch (InvalidImageRequestException e) {
            LOG.error(e.getMessage());
            returnStatus = e.getStatusCode();
        }

        LOG.info("Finished handling request.");
        response.setStatus(returnStatus);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
