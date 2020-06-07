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

public class BlockModelGenerator {
    private static Logger logger = LogManager.getLogger();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public static void generateBlockModels(JsonObject jsonIn){
        if (!jsonIn.get("display").getAsJsonObject().has("model")){
            logger.warn("Block " + jsonIn.get("id").getAsString() + " does not define a model;");
            return;
        } else{
            if (!jsonIn.get("display").getAsJsonObject().has("textures")){
                logger.warn("Block " + jsonIn.get("id").getAsString() + " does not define any textures;");
                return;
            } else{
                JsonObject blockModelObject = new JsonObject();
                blockModelObject.addProperty("parent", jsonIn.get("display").getAsJsonObject().get("model").getAsString());
                blockModelObject.add("textures", jsonIn.get("display").getAsJsonObject().get("textures").getAsJsonObject());
                File blockModelFile = new File(References.BLOCK_MODELS, jsonIn.get("id").getAsString() + ".json");
                try {
                    FileWriter writer = new FileWriter(blockModelFile);
                    writer.write(gson.toJson(blockModelObject));
                    writer.flush();
                    writer.close();
                } catch (IOException e){
                    logger.error(e);
                }
            }
        }
    }

    public static void generateBlockItemModels(JsonObject jsonIn){
        if (!jsonIn.has("block_item")){
            logger.warn("Block " + jsonIn.get("id").getAsString() + " does not define an item model ; A model will be generated from its block model;");
            JsonObject blockItemModelObject = new JsonObject();
            blockItemModelObject.addProperty("parent", References.MODID + ":block/" + jsonIn.get("id").getAsString());
            File blockItemModelFile = new File(References.ITEM_MODELS, jsonIn.get("id").getAsString() + ".json");
            try {
                FileWriter writer = new FileWriter(blockItemModelFile);
                writer.write(gson.toJson(blockItemModelObject));
                writer.flush();
                writer.close();
            } catch (IOException e){
                logger.error(e);
            }
        } else{
            JsonObject blockItemModelObject = new JsonObject();
            if (!jsonIn.get("block_item").getAsJsonObject().has("model")){
                logger.info("Block " + jsonIn.get("id").getAsString() + " contains 'block_item' but does not define a model; A model will be generated from the block model;");
                blockItemModelObject.addProperty("parent", References.MODID + ":block/" + jsonIn.get("id").getAsString());
            } else{
                blockItemModelObject.addProperty("parent", jsonIn.get("block_item").getAsJsonObject().get("model").getAsString());    
            }
            if (jsonIn.get("block_item").getAsJsonObject().has("textures")){
                blockItemModelObject.add("textures", jsonIn.get("block_item").getAsJsonObject().get("textures").getAsJsonObject());
            } else{
                logger.warn("Block " + jsonIn.get("id").getAsString() + " defines an item model but is missing 'textures'; Model will be generated without textures;");
            }
            File blockItemModelFile = new File(References.ITEM_MODELS, jsonIn.get("id").getAsString() + ".json");
            try{
                FileWriter writer = new FileWriter(blockItemModelFile);
                writer.write(gson.toJson(blockItemModelObject));
                writer.flush();
                writer.close();
            } catch (IOException e){
                logger.error(e);
            }
        }
    }
}