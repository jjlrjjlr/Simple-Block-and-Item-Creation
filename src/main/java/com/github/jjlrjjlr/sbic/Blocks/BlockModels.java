package com.github.jjlrjjlr.sbic.Blocks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.github.jjlrjjlr.sbic.References;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ibm.icu.impl.coll.SharedObject.Reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockModels {
    private static Logger logger = LogManager.getLogger();

    private static JsonObject getModelFromString(String modelInformation, Map<String, Object> textureMap){
        JsonObject textureObject = new JsonObject();
        JsonObject generatedBlockModel = new JsonObject();
        generatedBlockModel.addProperty("parent", "block/cube");

        logger.warn(textureMap.toString());
        
        for (String texture : textureMap.keySet()){
            textureObject.addProperty(texture, textureMap.get(texture).toString());
        }
        generatedBlockModel.add("textures", textureObject);
        return generatedBlockModel;
    }

    public static void blockModelWriter(JsonObject jsonIn){
        String blockId = jsonIn.get("id").getAsString().toLowerCase();
        if (!jsonIn.get("display").getAsJsonObject().has("model")){
            logger.warn("Block " + blockId + " does not define a desired model, if this is intentional please ignore this warning.");
            return;
        } else if ("".equalsIgnoreCase(jsonIn.get("display").getAsJsonObject().get("model").getAsString())){
            logger.warn("Block " + blockId + " has a 'model' value that is either null or empty ; Model will not be generated.");
            return;
        } else if (!jsonIn.get("display").getAsJsonObject().has("textures")){
            logger.warn("Block " + blockId + " does not define 'textures' as part of display; No model will be generated.");
            return;
        } else if (jsonIn.get("display").getAsJsonObject().get("textures").getAsJsonObject().size() < 1){
            logger.warn("Block " + blockId + " does not define textures as part of it's 'display' ; No model will be generated.");
            return;
        }
        Gson gson = new Gson();
        Map<String, Object> textures = new Gson().fromJson(jsonIn.get("display").getAsJsonObject().get("textures").getAsString(), Map.class);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(References.BLOCK_MODELS, blockId + ".json")));
            writer.write(gson.toJson(getModelFromString(jsonIn.get("display").getAsJsonObject().get("model").getAsString(), textures)));
            writer.flush();
            writer.close();
        } catch (IOException e){
            logger.error(e);
        }
    }
}