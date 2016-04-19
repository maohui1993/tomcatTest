package org.hooyee.mao.service;


/**
 * Created by bes6 on 2016/3/16.
 */
public class StaticResoureProcessor {

    public void process(Request request, Response response) {
        response.sendStaticResource();
    }
}
