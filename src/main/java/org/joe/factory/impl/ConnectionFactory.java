package org.joe.factory.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

public class ConnectionFactory {

    private boolean proxy;

    public ConnectionFactory() {
        super();
        this.proxy = false;
    }

    public ConnectionFactory(boolean proxy) {
        super();
        this.proxy = proxy;
    }

    public String sendPostRequestAsEntity(String url, Map<String, String> headersMap,
            String jsonBody) {
        URL pageUrl = null;
        HttpURLConnection connection = null;

        this.proxy = false;
        try {
            pageUrl = new URL(url);
            if (proxy) {
                connection = (HttpURLConnection) pageUrl.openConnection(new Proxy(
                        Type.HTTP, new InetSocketAddress("localhost", 3128)));
            } else {
                connection = (HttpURLConnection) pageUrl.openConnection();
            }
            addHeaders(connection, headersMap);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            try (OutputStreamWriter wr = new OutputStreamWriter(
                    connection.getOutputStream())) {
                wr.write(jsonBody);
                wr.flush();
            }
            int code = connection.getResponseCode();
            if (code == 200) {
                return "";
            }

            String line;
            StringBuilder sb = new StringBuilder();
            try (BufferedReader rw = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                while ((line = rw.readLine()) != null) {
                    sb.append(line);
                }
            }
            return sb.toString();
        } catch (Exception ex) {
            System.out.println("connect error : "+ ex.getMessage());
        }
        return "";
    }

    private void addHeaders(HttpURLConnection urlConnection,
            Map<String, String> headers) {
        for (Entry<String, String> item : headers.entrySet()) {
            urlConnection.setRequestProperty(item.getKey(), item.getValue());
        }
    }

    public InputStream getInputStream(String url) {
        URL pageUrl = null;
        URLConnection urlConnection = null;

        try {
            pageUrl = new URL(url);
            if (proxy) {
                urlConnection = (HttpURLConnection) pageUrl.openConnection(new Proxy(
                        Type.HTTP, new InetSocketAddress("localhost", 3128)));
            } else {
                urlConnection = (HttpURLConnection) pageUrl.openConnection();
            }
            return urlConnection.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // HTTP GET request
    public void sendGet() throws Exception {

        String url = "http://festive-cistern-197604.appspot.com/api/LineChat/TLJS";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        // add request header
        // con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

    }
}
