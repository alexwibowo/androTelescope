package org.isolution.androtelescope.server;

/**
* User: agwibowo
* Date: 31/05/11
* Time: 10:52 PM
*/
class InvalidImageRequestException extends Exception{
    private int statusCode;

    InvalidImageRequestException(String errorMessage, int statusCode) {
        super(errorMessage);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
