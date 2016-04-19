import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hooyee on 2016/3/1.
 */
public class Response {
    private OutputStream output;
    private Request request;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void sendStaticResource() {
        File file = new File(HttpServer.WEB_ROOT, request.getUri());
        byte[] buffer = new byte[1024];
        int length;
        FileInputStream input = null;
        try {
            if(!file.exists() || file.isDirectory()) {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
                        + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n"
                        + "\r\n"
                        + "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
                return;
            }
            input = new FileInputStream(file);
            while((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
