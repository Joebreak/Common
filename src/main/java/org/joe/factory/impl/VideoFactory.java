package org.joe.factory.impl;

import java.nio.file.Files;
import java.nio.file.Path;

import org.joe.model.VideoData;
import org.joe.utils.CommandTools;
import org.joe.utils.JSONTool;
import org.joe.utils.StringTool;
import org.springframework.stereotype.Service;

@Service
public class VideoFactory {

    private String ffprobePath = "tools/ffprobe";

    public VideoData getVideoDate(Path source) {
        if (!Files.exists(source)) {
            return null;
        }
        String code = String.format("%s -v quiet -print_format json -show_format -show_streams %s", ffprobePath, source);
        System.out.println(code);
        String message = CommandTools.execute(code);
        return JSONTool.readJSON(message, VideoData.class);
    }

    public void convert(Path sourcePath, Path outPath, String fromTime, String toTime, String videType, String audioType,
            String picsTime, boolean videoOnly, boolean audioOnly, boolean copy, boolean ts, boolean snapshot, boolean execute) {
        if (!Files.exists(sourcePath)) {
            return;
        }
        StringBuilder code = new StringBuilder();
        code.append(ffprobePath);
        code.append(" ");
        // input
        code.append("-i ");
        code.append(sourcePath);
        code.append(" ");
        //
        code.append("-y ");
        // fromTime
        if (!StringTool.isNullOrEmpty(fromTime)) {
            code.append("-ss ");
            code.append(fromTime);
            code.append(" ");
        }
        // toTime
        if (!StringTool.isNullOrEmpty(toTime)) {
            code.append("-t ");
            code.append(toTime);
            code.append(" ");
        }
        //videType
        if (!StringTool.isNullOrEmpty(videType)) {
            code.append("-vcodec ");
            code.append(videType);
            code.append(" ");
        }
        //audioType
        if (!StringTool.isNullOrEmpty(audioType)) {
            code.append("-acodec ");
            code.append(audioType);
            code.append(" ");
        }
        if (!StringTool.isNullOrEmpty(picsTime)) {
            code.append("-r ");
            code.append(picsTime);
            code.append(" ");
        }
        if (audioOnly) {
            code.append("-vn ");
        }else if (videoOnly) {
            code.append("-an ");
        }
        // snapshot
        if (snapshot) {
            code.append("-vframes:v 1 ");
        }
        //for merge
        if (ts) {
            code.append("-bsf:v h264_mp4toannexb -f mpegts "); 
        }
        // out
        code.append(outPath);
        System.out.println(code.toString());
        if (execute) {
            CommandTools.execute(code.toString());  
        }
    }

}
