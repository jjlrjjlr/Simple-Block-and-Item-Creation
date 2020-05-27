package com.github.jjlrjjlr.sbic.LoaderUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;

import com.github.jjlrjjlr.sbic.References;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadJSON {

    public static Logger logger = LogManager.getLogger();

    private static FilenameFilter jsonFileType = new WildcardFileFilter("*.json", IOCase.INSENSITIVE);
    
    private static File[] BLOCK_JSONS = References.BLOCK_CONFIG.listFiles((FilenameFilter) jsonFileType);
    private static File[] ITEM_JSONS = References.ITEM_CONFIG.listFiles((FilenameFilter) jsonFileType);

    public static Set<JsonObject> getItemFiles(){
        Set<JsonObject> loadedItemObjects = new HashSet<>();
        loadedItemObjects.add(References.getTestItem());
        for(File readIn : ITEM_JSONS){
            JsonObject object = readJsonFromFile(readIn);
            if(object == null){
                logger.error("File not read; LoadJSON.readJsonFromFile() returned null for file: " + readIn.toString());
                break;
            }
            try{
                loadedItemObjects.add(object);
            } catch(Exception e){
                logger.error(e);
            }
        }
        
        return loadedItemObjects;
    }
    
    public static Set<JsonObject> getBlockFiles(){
        Set<JsonObject> loadedBlockObjects = new HashSet<>();
        loadedBlockObjects.add(References.getTestBlock());
        for(File readIn : BLOCK_JSONS){
            JsonObject object = readJsonFromFile(readIn);
            if(object == null){
                logger.error("File not read; LoadJSON.readJsonFromFile() returned null for file: " + readIn.toString());
                break;
            } else if(!object.has("type") || object.get("type").getAsString().isEmpty()){
                logger.error("type is null or empty for file: " + readIn.toString() + " ; Please use a supported type when registering objects.");
                break;
            }else if (!References.BLOCK_TYPES.contains(object.get("type").getAsString())){
                logger.error(object.get("type") + " is not a recognized block type; Please use only valid types when registering blocks.");
                break;
            }
            try{
                loadedBlockObjects.add(object);
            } catch(Exception e){
                logger.error(e);
            }
        } 
        
        return loadedBlockObjects;
    }

    private static JsonObject readJsonFromFile(File JsonFile){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(JsonFile));
            JsonObject outputObject = new JsonParser().parse(reader).getAsJsonObject();
            return outputObject;
        } catch (FileNotFoundException e){
            logger.warn("File " + JsonFile.toPath().toString() + " not found.", e);
            return null;
        } 
    }
}