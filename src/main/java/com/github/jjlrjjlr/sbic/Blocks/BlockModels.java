package com.github.jjlrjjlr.sbic.Blocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.github.jjlrjjlr.sbic.References;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockModels {
    private static Logger logger = LogManager.getLogger();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static JsonObject getModelFromString(String model, JsonObject textures){
        JsonObject generatedBlockModel = new JsonObject();
        generatedBlockModel.addProperty("parent", model);

        logger.warn(textures.toString());
        generatedBlockModel.add("textures", textures);
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
        
        try{
            FileWriter writer = new FileWriter(new File(References.BLOCK_MODELS, blockId + ".json"));
            writer.write(gson.toJson(getModelFromString(jsonIn.get("display").getAsJsonObject().get("model").getAsString(), jsonIn.get("display").getAsJsonObject().get("textures").getAsJsonObject())));
            writer.flush();
            writer.close();
        } catch (IOException e){
            logger.error(e);
        }
    }
}