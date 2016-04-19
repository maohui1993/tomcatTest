
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hooyee on 2016/3/1.
 */
public class HttpServer {
    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while(!shutdown) {
            handleRequest(serverSocket);
        }

    }

    private void handleRequest(ServerSocket serverSocket) {
        InputStream input = null;
        OutputStream output = null;
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            input = socket.getInputStream();
            output = socket.getOutputStream();
            Request request = new Request(input);
            request.paser();
            Response response = new Response(output);
            response.setRequest(request);
            response.sendStaticResource();
            shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
