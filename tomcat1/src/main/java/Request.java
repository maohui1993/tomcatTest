
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hooyee on 2016/3/1.
 */
public class Request {
    public static final String INDEX_UTI = "/index.html";

    private InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public void paser() {
        byte[] bytes = new byte[2048];
        StringBuffer request = new StringBuffer();
            /*  此处与从socket中获得的input的输入流是由浏览器传来的数据，数据没有明确的末尾标识，故一直阻塞在此处
            while((length = input.read(bytes)) != -1) {
                for(int i = 0; i < length; i++) {
                    request.append((char)i);
                }
            }*/
        try {
            int length = input.read(bytes);
            for(int i = 0; i < length; i++) {
                request.append((char)bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(request.toString());
        uri = paserUri(request.toString());
    }

    private String paserUri(String requestString) {
        int first = requestString.indexOf(" ");
        if (first != -1) {
            int second = requestString.indexOf(" ", first + 1);
            if(second > first) {
                return requestString.substring(first + 1, second);
            }
        }

        return INDEX_UTI;
    }

    public String getUri() {
        return uri;
    }
}
