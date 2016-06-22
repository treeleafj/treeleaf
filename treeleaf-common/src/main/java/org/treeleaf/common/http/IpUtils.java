package org.treeleaf.common.http;

import org.apache.commons.io.IOUtils;
import org.treeleaf.common.exception.ServiceException;
import org.treeleaf.common.json.Jsoner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ip工具
 * <p>
 * Created by yaoshuhong on 2016/5/4.
 */
public class IpUtils {

    /**
     * 中国ip段
     */
    private static List<IpRegion> chinaIpHeads;

    static {
        InputStream in = IpUtils.class.getResourceAsStream("ip.txt");
        try {
            List<String> list = IOUtils.readLines(in, "utf-8");
            chinaIpHeads = new ArrayList<>(list.size());
            list.forEach(c -> {
                String[] array = c.split("-");
                chinaIpHeads.add(new IpRegion().setStart(array[0].trim()).setEnd(array[1].trim()));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 基于淘宝的ip地址查询接口查询ip地址
     *
     * @param ip
     * @return
     */
    public static IpAddress query(String ip) {
        String r = new Get("http://ip.taobao.com/service/getIpInfo2.php").param("ip", ip).send();
        Map result = Jsoner.toObj(r, Map.class);
        if (!"0".equals(String.valueOf(result.get("code")))) {
            throw new ServiceException("查询ip失败," + result.get("data"));
        }

        return Jsoner.toObj(result.get("data").toString(), IpAddress.class);
    }

    /**
     * 生成一个随机ip
     *
     * @param isChina 是否中国国内ip
     * @return
     */
    public static String randomIp(boolean isChina) {

        StringBuilder ip = new StringBuilder();

        if (isChina) {
            int index = new Random().nextInt(chinaIpHeads.size());
            IpRegion ipRegion = chinaIpHeads.get(index);
            String[] starts = ipRegion.getStart().split("\\.");
            String[] ends = ipRegion.getEnd().split("\\.");
            for (int i = 0; i < 4; i++) {
                int a = Integer.parseInt(starts[i]);
                int b = Integer.parseInt(ends[i]);
                int c = b - a;
                ip.append(a + (c > 0 ? new Random().nextInt(c) : 0));
                ip.append(".");
            }
            ip.deleteCharAt(ip.length() - 1);
            return ip.toString();
        } else {
            ip.append(new Random().nextInt(111) + 1)
                    .append('.').append(new Random().nextInt(255))
                    .append('.').append(new Random().nextInt(255))
                    .append('.').append(new Random().nextInt(255));
        }
        return ip.toString();
    }

    /**
     * ip地址
     */
    public static class IpAddress {

        /**
         * 地区(华南,华中,华北等)
         */
        private String area;

        /**
         * 国家
         */
        private String country;

        /**
         * 省份
         */
        private String region;

        /**
         * 城市
         */
        private String city;

        /**
         * 运营商
         */
        private String isp;

        public String getArea() {
            return area;
        }

        public IpAddress setArea(String area) {
            this.area = area;
            return this;
        }

        public String getCountry() {
            return country;
        }

        public IpAddress setCountry(String country) {
            this.country = country;
            return this;
        }

        public String getRegion() {
            return region;
        }

        public IpAddress setRegion(String region) {
            this.region = region;
            return this;
        }

        public String getCity() {
            return city;
        }

        public IpAddress setCity(String city) {
            this.city = city;
            return this;
        }

        public String getIsp() {
            return isp;
        }

        public IpAddress setIsp(String isp) {
            this.isp = isp;
            return this;
        }
    }

    /**
     * ip段
     */
    public static class IpRegion {
        private String start;
        private String end;

        public String getStart() {
            return start;
        }

        public IpRegion setStart(String start) {
            this.start = start;
            return this;
        }

        public String getEnd() {
            return end;
        }

        public IpRegion setEnd(String end) {
            this.end = end;
            return this;
        }
    }
}
