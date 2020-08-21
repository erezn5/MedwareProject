package com.medware.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    public static Logger log = LogManager.getLogger(FileUtils.class.getName());
    public static void writeToFile(String filePath , List<String> lines, String type) {

        try(FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            bufferedWriter.write(type + ": ");
            for(String line : lines){
                bufferedWriter.write(line + " ");
            }
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        }catch (IOException e){
            e.getMessage();
        }
    }

    public static void createFolder(File folder , boolean recursive){
        if(folder.exists() && folder.isDirectory()){
            log.info(folder.getName() + " directory already exist");
        }else if((recursive ? folder.mkdirs() : folder.mkdir())){
            log.info(folder.getName() + " directory created successfully");
        }else{
            log.error("failed to create '" + folder.getName() + "' directory");
        }
    }
}

