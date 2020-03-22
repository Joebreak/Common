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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionFactory {

    private static final Logger logger = LogManager
            .getLogger(ConnectionFactory.class);
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
            logger.error("connect error : {}", ex.getMessage());
        }
        return "";
    }

    private void addHeaders(HttpURLConnection urlConnection,
            Map<String, String> headers) {
        for (Entry<String, String> item : headers.entrySet()) {
            urlConnection.setRequestProperty(item.getKey(), item.getValue());
        }
        // urlConnection.setRequestProperty("Content-Type", "application/json");
        // urlConnection.setRequestProperty("charset", "utf-8");
        // urlConnection.setRequestProperty("neweggbox-sso-token",
        // "002012619c7681b433459e992cf4fe065c5f3f");
    }

//    private Map<String, String> getLineBotHeaders() {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        headers.put("Authorization",
//                "Bearer rTeZbz5F1JtwqRNmc3CVdZIR/Sz58wjRksXogigbVI4xRmn8bklpxNZWxoPZvEiZ"
//                        + "gRGlt7PncUhh96qraOyjlYW7uyLyXIZqLFB+vAzftYCIRU/yBLG/JgWwIDNp/1"
//                        + "PMbuPVtsc7UHoUrlC6BbPYfAdB04t89/1O/w1cDnyilFU=");
//        return headers;
//    }

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
        System.out.println("\nSending 'GET' request to URL : " + url);
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

//	public void sendLineBotReply(MessageReplyRequest request) {
//		String replyUrl = "https://api.line.me/v2/bot/message/reply";
//		sendPostRequestAsEntity(replyUrl,getLineBotHeaders(), JSONTool.writeJSON(request));
//	}
//
//	public void sendLineBotPush(MessagePushRequest request) {
//		String replyUrl = "https://api.line.me/v2/bot/message/push";
//		sendPostRequestAsEntity(replyUrl,getLineBotHeaders(), JSONTool.writeJSON(request));
//	}
}
