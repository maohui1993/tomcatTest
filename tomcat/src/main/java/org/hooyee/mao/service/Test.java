package org.hooyee.mao.service;

import org.hooyee.mao.util.Constants;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bes6 on 2016/3/17.
 */
public class Test {


    public static void main(String[] args) throws IOException {
        File file = new File(Constants.WEB_ROOT);
        System.out.println((new URL("file", null, file.getCanonicalPath()+File.separator)).toString());

        InetAddress inet = InetAddress.getByName("bes12-PC");
        System.out.println(inet.getHostName() + " " + inet.getHostAddress());
        System.out.println(InetAddress.getAllByName("192.168.1.21").length);
    }

    public static void stringsContainDigitSort(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null || "".equals(o1)) {
                    return -1;
                }
                if (o2 == null || "".equals(o2)) {
                    return 1;
                }
                if (o1.equals(o2)) {
                    return 0;
                }

                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher1 = pattern.matcher(o1);
                Matcher matcher2 = pattern.matcher(o2);
                int result = o1.compareTo(o2);

                if (result != 0) {
                    int index = 0;
                    while (true) {
                        System.out.println("t1:");
                        // will return when anyone doesn`t have digit
                        if (matcher1.find() && matcher2.find()) {
                            int start1 = matcher1.start();
                            int start2 = matcher2.start();
                            //数字开始位置不同，则无需考虑数字的大小关系
                            if (start1 != start2) {
                                return result;
                            } else {
                                int s1 = Integer.parseInt(matcher1.group());
                                int s2 = Integer.parseInt(matcher2.group());
                                if (s1 == s2) {
                                    index = matcher1.end();
                                    // matcher.region(first,end)前开后闭
                                    matcher1 = matcher1.region(index, o1.length());
                                    matcher2 = matcher2.region(index, o2.length());
                                    continue;
                                }
                                return s1 - s2;
                            }
                        } else {
                            return result;
                        }
                    }
                }
                return result;
            }
        });
    }
}
