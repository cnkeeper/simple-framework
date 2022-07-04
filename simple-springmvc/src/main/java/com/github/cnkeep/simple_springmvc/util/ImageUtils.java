package com.github.cnkeep.simple_springmvc.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

/**
 * ImageUtils
 *
 * @author LeiLi.Zhang <mailto:zhangleili 924@gmail.com>
 * @date 2022/6/21
 */
public class ImageUtils {

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) throws Exception {
        if (isBase64Str(strUrl)) {
            return readFromBase64(strUrl);
        }

        InputStream inStream = null;
        strUrl = trimSpecialUrl(strUrl);
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Referer", strUrl);
            conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();//通过输入流获取图片数据
            Map<String, List<String>> headerFields = conn.getHeaderFields();
            boolean legalImageResponse = isLegalImageResponse(headerFields);
            if (!legalImageResponse) {
                throw new RuntimeException("not legal response");
            }
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } finally {
            if (Objects.nonNull(inStream)) {
                inStream.close();
            }
        }
    }

    private static boolean isLegalImageResponse(Map<String, List<String>> headerFields) {
        List<String> contentType = headerFields.getOrDefault("Content-Type", headerFields.get("content-type"));
        if (CollectionUtils.isEmpty(contentType)) {
            return false;
        }
        return contentType.stream().filter(e -> e.contains("image") || e.contains("application/octet-stream"))
            .findFirst().isPresent();
    }

    private static String trimSpecialUrl(String strUrl) {
        if (strUrl.contains("static.wixstatic.com")) {
            int index = strUrl.indexOf("/v1");
            if (index > 0) {
                return strUrl.substring(0, index);
            }
        }
        return strUrl;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    public static boolean isBase64Str(String url) {
        return url.startsWith("data:");
    }

    public static byte[] readFromBase64(String baseStr) {
        if (isBase64Str(baseStr)) {
            baseStr = baseStr.substring(baseStr.indexOf(",") + 1);
        }

        return Base64.getDecoder().decode(baseStr);
    }
}
