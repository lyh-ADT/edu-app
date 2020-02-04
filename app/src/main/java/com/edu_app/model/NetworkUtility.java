package com.edu_app.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtility {

    /**
     * 向目标链接发起Cookies带UID的GET请求，将返回的json数据转换为cls对应的对象
     * @param url 链接字符串
     * @param uid UID
     * @param cls json数据对应的java对象的类
     * @param <T> json数据对应的java对象的类型
     * @return 返回json数据对应的T对象
     * @throws IOException 网络异常和链接格式错误时抛出
     *
     * @see NetworkUtility#getRequest(String, String)
     * @see NetworkUtility#parseJson(String, Class)
     */
    static public <T> T getToJson(String url, String uid, Class<T> cls) throws IOException {
        return parseJson(getRequest(url, uid), cls);
    }

    /**
     * 向目标链接发起Cookies带UID的GET请求，将返回的json数据转换为cls对应的对象
     * @param url 链接字符串
     * @param uid UID
     * @param cls json数据对应的java对象的类型对象
     * @param <T> json数据对应的java对象的类型
     * @return 返回json数据对应的T对象
     * @throws IOException 网络异常和链接格式错误时抛出
     *
     * 这个方法和{@link NetworkUtility#getToJson(String, String, Class)}类似，当需要把json转换为List对象的时候使用
     * @see NetworkUtility#parseJson(String, Type)
     * @see NetworkUtility#getRequest(String, String)
     */
    static public <T> T getToJson(String url, String uid, Type cls) throws IOException {
        return parseJson(getRequest(url, uid), cls);
    }

    /**
     * 向目标链接发起Cookies带UID的POST请求，将返回的json数据转换为cls对应的对象
     * @param url 链接字符串
     * @param uid UID
     * @param cls json数据对应的java对象的类
     * @param <T> json数据对应的java对象的类型
     * @return 返回json数据对应的T对象
     * @throws IOException 网络异常和链接格式错误时抛出
     *
     * @see NetworkUtility#postRequest(String, String, byte[])
     */
    static public <T> T postToJson(String url, String uid, String data, Class<T> cls) throws IOException {
        return parseJson(postRequest(url, uid, data.getBytes()), cls);
    }

    /**
     * 向目标链接发起Cookies带UID的POST请求，将返回的json数据转换为cls对应的对象
     * @param url 链接字符串
     * @param uid UID
     * @param cls json数据对应的java对象的类
     * @param <T> json数据对应的java对象的类型
     * @return 返回json数据对应的T对象
     * @throws IOException 网络异常和链接格式错误时抛出
     *
     * 这个方法和{@link NetworkUtility#postToJson(String, String, String, Class)}类似，当需要把json转换为List对象的时候使用
     * @see NetworkUtility#postRequest(String, String, byte[])
     */
    static public <T> T postToJson(String url, String uid, String data, Type cls) throws IOException {
        return parseJson(postRequest(url, uid, data.getBytes()), cls);
    }

    /**
     * 向目标链接发起Cookies带UID的GET请求, 返回响应字符串
     * @param url 目标链接字符串
     * @param uid UID字符串
     * @return 响应字符串
     * @throws IOException 网络异常和链接格式错误时抛出
     */
    static public String getRequest(String url, String uid) throws IOException {
        HttpURLConnection connection = getUidConnection(url, uid, "GET");

        connection.connect();

        return read(connection.getInputStream());
    }

    /**
     * 向目标链接发起Cookies带UID的POST请求, 返回响应字符串
     * @param url 目标链接字符串
     * @param uid UID字符串
     * @param data POST的数据
     * @return 响应字符串
     * @throws IOException 网络异常和链接格式错误时抛出
     */
    static public String postRequest(String url, String uid, byte[] data) throws IOException {
        HttpURLConnection connection = getUidConnection(url, uid, "POST");
        connection.setRequestProperty("Content-Length", String.valueOf(data.length));
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        connection.connect();

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        outputStream.close();

        return read(connection.getInputStream());
    }

    /**
     * 将Json字符串转换成对应的对象
     * @param json json字符串
     * @param cls json数据对应的java对象的类
     * @param <T> json数据对应的java对象的类型
     * @return 返回json数据对应的T对象
     *
     * @see Gson#fromJson(String, Class)
     */
    static public <T> T parseJson(String json, Class<T> cls){
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }

    /**
     * 将Json字符串转换成对应的对象
     * @param json json字符串
     * @param typeOfT json数据对应的java对象的类型对象
     * @param <T> json数据对应的java对象的类型
     * @return 返回json数据对应的T对象
     *
     * 这个方法和{@link NetworkUtility#parseJson(String, Class)}类似，当需要把json转换为List对象的时候使用
     * @see Gson#fromJson(String, Type)
     */
    @SuppressWarnings("unchecked")
    static public <T> T parseJson(String json, Type typeOfT){
        Gson gson = new Gson();
        return (T)gson.fromJson(json, typeOfT);
    }

    static private String read(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while((len = inputStream.read(buffer, 0, buffer.length)) != -1){
            sb.append(new String(buffer, 0, len));
        }
        return sb.toString();
    }

    static private HttpURLConnection getUidConnection(String url, String uid, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Cookies", "UID="+uid+";");
        return connection;
    }
}
