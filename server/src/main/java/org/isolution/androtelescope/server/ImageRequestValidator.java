package org.isolution.androtelescope.server;

import javax.servlet.http.HttpServletResponse;

/**
 * User: agwibowo
 * Date: 31/05/11
 * Time: 10:53 PM
 */
public class ImageRequestValidator {

    private final String contentType;

    private final byte[] imageBytes;

    public ImageRequestValidator(String contentType, byte[] imageBytes) {
        this.contentType = contentType;
        this.imageBytes = imageBytes;
    }

    public void validate()
            throws InvalidImageRequestException {
        if (imageBytes != null && imageBytes.length > 0 ) {
            throw new InvalidImageRequestException("Empty image",HttpServletResponse.SC_NO_CONTENT);
        }
        if (!isSupportedContentType()) {
            throw new InvalidImageRequestException("Content type is not supported",HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    private boolean isSupportedContentType() {
        return "image/jpeg".equals(contentType);
    }
}
