package org.treeleaf.web.spring.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.web.Json;

import java.io.IOException;

/**
 * 专门处理Controller中的方法上标有@ResponseBody返回数据,
 * 因为spring mvc默认采用jackson,当项目是使用的是fastjson,
 * 为了统一,使用本类替换掉spring mvc默认的转json处理
 * Created by yaoshuhong on 2015/4/29.
 *
 * @see org.treeleaf.web.Json
 */
public class JsonHttpMessageConverter extends AbstractHttpMessageConverter<Json> {

    private String charset = "utf-8";

    @Override
    protected boolean supports(Class<?> clazz) {
        return Json.class.isAssignableFrom(clazz);
    }

    @Override
    protected Json readInternal(Class<? extends Json> clazz,
                                HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        return null;//暂不处理@RequestBody
    }

    @Override
    protected void writeInternal(Json t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        //处理@ResponseBody,转为json输出给前台
        String json = Jsoner.toJson(t);
        IOUtils.write(json, outputMessage.getBody(), charset);
    }

}
