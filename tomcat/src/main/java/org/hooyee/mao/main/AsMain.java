package org.hooyee.mao.main;

import org.hooyee.mao.service.Request;
import org.hooyee.mao.service.Response;
import org.hooyee.mao.service.ServletProcessor1;
import org.hooyee.mao.service.StaticResoureProcessor;
import org.hooyee.mao.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bes6 on 2016/3/16.
 */
public class AsMain {
    private boolean shutdown;

    public void await() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8080);
            while (!shutdown) {
                socket = serverSocket.accept();
                handleConnection(socket);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConnection(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        Request request = new Request(input);
        request.parse();
        Response response = new Response(out, request);
        response.setRequest(request);
        if(request.getUri().startsWith(Constants.SERVLET_FLAG_PRE)) {
            ServletProcessor1 processor1 = new ServletProcessor1();
            processor1.process(request, response);
        } else {
            StaticResoureProcessor processor = new StaticResoureProcessor();
            processor.process(request, response);
        }
        shutdown = request.getUri().equals(Constants.SHUTDOWN_COMMAND);
    }

    public static void main(String[] args) {
        new AsMain().await();
    }
}
