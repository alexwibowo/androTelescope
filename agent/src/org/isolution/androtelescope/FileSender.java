package org.isolution.androtelescope;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.File;

/**
 * User: agwibowo
 * Date: 1/06/11
 * Time: 8:59 PM
 */
public class FileSender {

    public static final String TAG = FileSender.class.getName();

    /**
     * Send the given file to the specified destination
     *
     * @param file      file to be sent
     * @param urlString destination to send the file to
     * @throws Exception any error that occurred during the  file transmission
     */
    public void send(File file, String urlString)
            throws Exception {
        Log.i(TAG, String.format("About to send file [%s] to [%s]", file.getName(), urlString));
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(urlString);

        FileEntity entity = new FileEntity(file, "image/jpeg");
        postRequest.setEntity(entity);
        postRequest.addHeader(new BasicHeader("Content-Type", "image/jpeg"));
        HttpResponse response = httpClient.execute(postRequest);
        System.out.println("Status code: " + response.getStatusLine().getStatusCode());
        Log.i(TAG, String.format("Finished sending file [%s] to [%s]", file.getName(), urlString));
    }


    public void send(byte[] data, String urlString)
            throws Exception {
        Log.i(TAG, String.format("About to send data to [%s]", urlString));
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(urlString);


        ByteArrayEntity entity = new ByteArrayEntity(data);
        postRequest.setEntity(entity);
        postRequest.addHeader(new BasicHeader("Content-Type", "image/jpeg"));
        HttpResponse response = httpClient.execute(postRequest);
        System.out.println("Status code: " + response.getStatusLine().getStatusCode());
        Log.i(TAG, String.format("Finished sending data to [%s]", urlString));

    }


}
