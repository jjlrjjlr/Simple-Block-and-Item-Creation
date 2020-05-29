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

public class BlockStateGenerator {
    private static Logger logger = LogManager.getLogger();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void generateCubeBlockstates(JsonObject jsonIn){
        if (!jsonIn.get("display").getAsJsonObject().has("blockstates")){
            logger.info("No 'blockstates' value found for block " + jsonIn.get("id").getAsString() +"; A default blockstate will be generated;");
            File blockstateFile = new File(References.BLOCKSTATE_DIRECTORY, jsonIn.get("id").getAsString() + ".json");
            JsonObject variants = new JsonObject();
            JsonObject defaultVariant = new JsonObject();
            defaultVariant.addProperty("model", References.MODID + ":block/" + jsonIn.get("id").getAsString());
            variants.add("", defaultVariant);
            try {
                FileWriter writer = new FileWriter(blockstateFile);
                writer.write(gson.toJson(variants));
                writer.flush();
                writer.close();
            } catch (IOException e){
                logger.error(e);
            }
        } else {
            if (!jsonIn.get("display").getAsJsonObject().get("blockstates").getAsJsonObject().has("variants")){
                logger.error("Block " + jsonIn.get("id").getAsString() + " contains a 'blockstates' value but doesn't contain a 'variants' value ; A default blockstate will be generated ;");
                File blockstateFile = new File(References.BLOCKSTATE_DIRECTORY, jsonIn.get("id").getAsString() + ".json");
                JsonObject variants = new JsonObject();
                JsonObject defaultVariant = new JsonObject();
                defaultVariant.addProperty("model", References.MODID + ":block/" + jsonIn.get("id").getAsString());
                variants.add("", defaultVariant);
                try {
                    FileWriter writer = new FileWriter(blockstateFile);
                    writer.write(gson.toJson(variants));
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            } else {
                File blockstateFile = new File(References.BLOCKSTATE_DIRECTORY, jsonIn.get("id").getAsString() + ".json");
                try {
                    FileWriter writer = new FileWriter(blockstateFile);
                    writer.write(gson.toJson(jsonIn.get("display").getAsJsonObject().get("blockstates").getAsJsonObject()));
                    writer.flush();
                    writer.close();
                } catch (IOException e){
                    logger.error(e);
                }
            }
        }
    }
    
}