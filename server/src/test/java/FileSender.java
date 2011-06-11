import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
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

    public void send(File file, String urlString) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(urlString);

        FileEntity entity = new FileEntity(file, "image/jpeg");
        postRequest.setEntity(entity);
        postRequest.addHeader(new BasicHeader("Content-Type", "image/jpeg"));
        HttpResponse response = httpClient.execute(postRequest);
        System.out.println("Status code: " + response.getStatusLine().getStatusCode());
    }

    public static void main(String[] args) throws Exception {
        FileSender fileSender = new FileSender();
        fileSender.send(new File("/home/agwibowo/Pictures/Air_1280x800.jpg"), "http://localhost:8081/androTelescope/imageSaver");
    }


}
