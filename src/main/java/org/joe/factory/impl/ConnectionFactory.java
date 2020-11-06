package org.joe.factory.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;

public class ConnectionFactory {

    private boolean proxy;

    public ConnectionFactory() {
        this.proxy = false;
    }

    public ConnectionFactory(boolean proxy) {
        this.proxy = proxy;
    }

    private void addHeaders(HttpURLConnection urlConnection, Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        for (Entry<String, String> item : headers.entrySet()) {
            urlConnection.setRequestProperty(item.getKey(), item.getValue());
        }
    }

    private HttpURLConnection getConnection(String url) throws Exception {
        URL pageUrl = new URL(url);
        if (proxy) {
            return (HttpURLConnection) pageUrl.openConnection(new Proxy(Type.HTTP, new InetSocketAddress("localhost", 3128)));
        } else {
            return (HttpURLConnection) pageUrl.openConnection();
        }
    }

    public String sendPostRequestAsEntity(String url, Map<String, String> headersMap, String jsonBody) {

        try {
            HttpURLConnection connection = getConnection(url);
            addHeaders(connection, headersMap);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream())) {
                wr.write(jsonBody);
                wr.flush();
            }
            String line = null;
            int code = connection.getResponseCode();
            if (code != 200) {
                try (BufferedReader rw = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    while ((line = rw.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                return null;
            }
            StringBuilder sb = new StringBuilder();
            try (BufferedReader rw = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                while ((line = rw.readLine()) != null) {
                    sb.append(line);
                }
            }
            return sb.toString();
        } catch (Exception ex) {
            System.out.println("connect error : " + ex.getMessage());
        }
        return null;
    }

    public String sendPostRequestAsEntity(String url, Map<String, String> headersMap, Path pathBody) {

        try {
            HttpURLConnection connection = getConnection(url);
            addHeaders(connection, headersMap);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            byte[] buf = new byte[4096];
            int length;
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    InputStream inputStream = Files.newInputStream(pathBody)) {
                while ((length = inputStream.read(buf)) > 0) {
                    wr.write(buf, 0, length);
                }
                wr.flush();
            }
            String line = null;
            int code = connection.getResponseCode();
            if (code != 200) {
                try (BufferedReader rw = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    while ((line = rw.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                return null;
            }
            StringBuilder sb = new StringBuilder();
            try (BufferedReader rw = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                while ((line = rw.readLine()) != null) {
                    sb.append(line);
                }
            }
            return sb.toString();
        } catch (Exception ex) {
            System.out.println("connect error : " + ex.getMessage());
        }
        return null;
    }

    public boolean sendHeadRequestPass(String url, Map<String, String> headersMap) {
        try {
            HttpURLConnection connection = getConnection(url);
            addHeaders(connection, headersMap);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("HEAD");

            return connection.getResponseCode() == 200;
        } catch (Exception ex) {
            System.out.println("connect error : " + ex.getMessage());
        }
        return false;
    }

    public InputStream getInputStream(String url) {

        try {
            HttpURLConnection connection = getConnection(url);
            return connection.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public BufferedInputStream sendGetRequestToInputStream(String url, Map<String, String> headers) {

        try {
            HttpURLConnection connection = getConnection(url);

            addHeaders(connection, headers);
            // optional default is GET
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                return null;
            }

            return new BufferedInputStream(connection.getInputStream());
        } catch (Exception e) {
        }
        return null;
    }
}
