package org.isolution.androtelescope.server;

import org.apache.http.HttpResponse;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.io.HttpResponseParser;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import static org.apache.commons.io.IOUtils.*;

/**
 * A simple servlet for receiving image & save it to a local drive
 *
 * User: agwibowo
 * Date: 31/05/11
 * Time: 9:24 PM
 */
@javax.servlet.annotation.WebServlet(
        name = "ImageReceiverServlet",
        urlPatterns = "/imageSaver"
)
public class ImageReceiverServlet  extends HttpServlet{
    public static final Logger LOG = Logger.getLogger(ImageReceiverServlet.class.getName());

    @Override
    public String getServletInfo() {
        return "A servlet for saving images.";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Received incoming image..");


        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        byte[] bytes = getImageAsBytes(request);

        int returnStatus;
        try {
            new ImageRequestValidator(request.getContentType(), bytes).validate();

            LOG.info(MessageFormat.format("Saving image. Image size: {0}", bytes.length));
            returnStatus = saveImage(bytes);
        } catch (InvalidImageRequestException e) {
            LOG.error(e.getMessage());
            returnStatus = e.getStatusCode();
        }

        LOG.info("Finished handling request.");
        response.setStatus(returnStatus);
    }


    private int saveImage(byte[] bytes) throws IOException {
        BufferedOutputStream bufferedWriter = null;
        try {
            bufferedWriter = new BufferedOutputStream(new FileOutputStream(new File("/tmp/chicken.jpg")));
            write(bytes, bufferedWriter);
            return HttpServletResponse.SC_ACCEPTED;
        } catch (Exception e) {
            LOG.error("An error had occurred while saving the image.", e);
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.flush();
                closeQuietly(bufferedWriter);
            }
        }
    }

    private byte[] getImageAsBytes(HttpServletRequest request) throws IOException {
        ServletInputStream is = request.getInputStream();
        return toByteArray(is);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
