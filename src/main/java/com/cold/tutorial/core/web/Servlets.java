package com.cold.tutorial.core.web;

import com.google.common.collect.Collections2;
import com.google.common.net.HttpHeaders;
import org.apache.commons.io.Charsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Http与Servlet工具类.
 * Created by faker on 2015/9/13.
 */
public class Servlets {

    // -- 常用数值定义 --//
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    /**
     * 设置客户端缓存过期时间
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        //http 1.0 set a expires date
        response.setDateHeader(HttpHeaders.EXPIRES, System.currentTimeMillis() + (expiresSeconds * 1000));
        //http 1.1 set a time after now
        response.setHeader(HttpHeaders.CACHE_CONTROL, "private, max-age=" + expiresSeconds);
    }

    /**
     * 设置客户端禁止缓存
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader(HttpHeaders.EXPIRES, 1L);
        response.addHeader(HttpHeaders.PRAGMA, "no-cache");
        // Http 1.1 header
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
    }

    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModifiedDate);
    }

    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader(HttpHeaders.ETAG, etag);
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     *
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     *
     * @param lastModified 内容的最后修改时间.
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified) {
        long ifModifiedSince = request.getDateHeader(HttpHeaders.LAST_MODIFIED);
        if ((ifModifiedSince != -1) && (lastModified < (ifModifiedSince + 1000))) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     *
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param etag 内容的ETag.
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String header = request.getHeader(HttpHeaders.IF_NONE_MATCH);
        if (header != null) {
            boolean conditionSatisfied = false;
            if (!"*".endsWith(header)) {
                StringTokenizer tokenizer = new StringTokenizer(header, ",");

                while (!conditionSatisfied && tokenizer.hasMoreElements()) {
                    String token = tokenizer.nextToken();
                    if (token.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader(HttpHeaders.ETAG, etag);
                return false;
            }

        }
        return true;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        // 替换空格，否则firefox下有空格文件名会被截断,其他浏览器会将空格替换成+号
        String encodedfileName = fileName.trim().replaceAll(" ", "_");

        String header = request.getHeader(HttpHeaders.USER_AGENT);
        boolean isMSIE = (header != null && header.toUpperCase().indexOf("MSIE") != -1);

        if (isMSIE) {
            encodedfileName = URLEncoder.encode(encodedfileName, Charsets.UTF_8.toString());
        } else {
            encodedfileName = new String(encodedfileName.getBytes(), Charsets.ISO_8859_1);
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedfileName + "\"");
    }

    public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
        if ((params == null) || params.isEmpty()) {
            return "";
        }

        if (prefix == null) {
            prefix = "";
        }

        StringBuilder queryStringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
            if (it.hasNext()) {
                queryStringBuilder.append("&");
            }
        }

        return queryStringBuilder.toString();
    }
}
