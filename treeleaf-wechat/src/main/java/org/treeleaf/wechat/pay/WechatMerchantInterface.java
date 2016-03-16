package org.treeleaf.wechat.pay;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leaf on 2016/3/17 0017.
 */
public abstract class WechatMerchantInterface {

    /**
     * 将map对象转为微信需要的xml格式
     *
     * @param map
     * @return
     */
    protected String mapToXml(Map<String, String> map) {
        //将数据转为xml
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotEmpty(entry.getValue())) {
                root.addElement(entry.getKey()).addText(entry.getValue());
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setIndent(true);
        format.setSuppressDeclaration(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLWriter output;
        try {
            output = new XMLWriter(outputStream, format);
            output.write(document);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return outputStream.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("微信返回的数据转为UTF-8失败", e);
        }
    }

    /**
     * 将微信返回的xml解析成map对象
     *
     * @param xml
     * @return
     */
    protected Map<String, String> xmlToMap(String xml) {
        Document document;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            throw new RuntimeException("解析的xml格式不正确:\n" + xml, e);
        }
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        Map<String, String> map = new HashMap<>();
        for (Element item : list) {
            map.put(item.getName(), item.getText());
        }
        return map;
    }


}
