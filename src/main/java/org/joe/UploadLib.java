package org.joe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joe.factory.impl.ConnectionFactory;
import org.joe.model.PathItem;
import org.joe.model.response.FolderInfoResponse;
import org.joe.utils.CollectionTool;
import org.joe.utils.DateTool;
import org.joe.utils.FileTool;
import org.joe.utils.JSONTool;
import org.joe.utils.StringTool;

public class UploadLib {

    private static String BOX_DOMIN = "https://apis.neweggbox.com";

    public static void main(String[] args) throws IOException {
        Path jarPath = Paths.get(System.getProperty("user.dir"), "build", "libs", "metadata.jar");
        if (!Files.exists(jarPath)) {
            System.out.println("no build file!");
            return;
        }
        String blockId = FileTool.getSHA256(jarPath);
        if (StringTool.isNullOrEmpty(blockId)) {
            System.out.println("file error!");
            return;
        }
        FolderInfoResponse info = getFolderInfo();
        if (info == null) {
            System.out.println("info error!");
        }
        List<PathItem> pathItems = info.getItems();
        List<String> names = new ArrayList<>();
        if (!CollectionTool.isNullOrEmpty(pathItems)) {
            for (PathItem pathItem : pathItems) {
                if (blockId.equalsIgnoreCase(pathItem.getBlockId())) {
                    System.out.println("jar exists");
                    return;
                }
                names.add(pathItem.getName());
            }
        }
        if (!fileExists(blockId)) {
            System.out.println("upload file...");
            if (fileUpload(blockId, jarPath)) {
                System.out.println("file upload error!");
            }
        }
        String name = String.format("metadata_%s.jar", DateTool.getCurrentTime());
        int index = 1;
        while (names.contains(name)) {
            name = String.format("metadata_%s_%03d.jar", args, index++);
        }
        if (crateFile(name, jarPath, blockId)) {
            System.out.println("done");
        }
    }

    public static FolderInfoResponse getFolderInfo() {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        String url = String.format("%s/meta/api/v5/folder/info", BOX_DOMIN);

        PathItem request = new PathItem();
        request.setPath("ghost/lib");

        String response = connectionFactory.sendPostRequestAsEntity(url, getHeaders(), JSONTool.writeJSON(request));
        if (response == null) {
            return null;
        }
        return JSONTool.readJSON(response, FolderInfoResponse.class);
    }

    public static boolean fileExists(String blockId) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        String url = String.format("%s/gateway/file/%s", BOX_DOMIN, blockId);

        return connectionFactory.sendHeadRequestPass(url, getHeaders());
    }

    public static boolean fileUpload(String blockId, Path path) {
        if (!Files.exists(path)) {
            return false;
        }
        ConnectionFactory connectionFactory = new ConnectionFactory();

        String url = String.format("%s/upload/file/%s", BOX_DOMIN, blockId);

        String response = connectionFactory.sendPostRequestAsEntity(url, getHeaders(), path);
        return response.isEmpty();
    }

    public static boolean crateFile(String name, Path path, String blockId) throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        String url = String.format("%s/meta/api/v5/file/create", BOX_DOMIN);

        long size = Files.size(path);

        PathItem request = new PathItem();
        request.setPath("ghost/lib/" + name);
        request.setSize(size);
        request.setBlockId(blockId);

        String response = connectionFactory.sendPostRequestAsEntity(url, getHeaders(), JSONTool.writeJSON(request));
        System.out.println(response);

        return !StringTool.isNullOrEmpty(response);
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("neweggbox-sso-token", "0010014b624e47b07a4e6898cda01f33455054");
        return headers;
    }

}
