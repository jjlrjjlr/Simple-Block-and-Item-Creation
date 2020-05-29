package com.github.jjlrjjlr.sbic;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.loader.api.FabricLoader;

public class References {
    private static Logger logger = LogManager.getLogger();

    public static final File CONFIG_DIRECTORY = new File(FabricLoader.getInstance().getConfigDirectory() + "/SimpleBlockItemCreation");

    public static final File BLOCK_CONFIG = new File(CONFIG_DIRECTORY + "/CustomBlocks"); 

    public static final File ITEM_CONFIG = new File(CONFIG_DIRECTORY + "/CustomItems");
    
    public static final File RESOURCE_LOCATION = new File(CONFIG_DIRECTORY + "/Resources");

    public static final File BLOCK_TEXTURES = new File(RESOURCE_LOCATION + "/assets/simple_block_item_creation/textures/block");

    public static final File BLOCK_MODELS = new File(RESOURCE_LOCATION + "/assets/simple_block_item_creation/models/block");

    public static final File ITEM_TEXTURES = new File(RESOURCE_LOCATION + "/assets/simple_block_item_creation/textures/item");

    public static final File ITEM_MODELS = new File(RESOURCE_LOCATION + "/assets/simple_block_item_creation/models/item");

    public static final File[] DIRECTORIES = {
        CONFIG_DIRECTORY,
        RESOURCE_LOCATION, 
        ITEM_CONFIG, 
        BLOCK_CONFIG, 
        ITEM_TEXTURES, 
        BLOCK_TEXTURES,
        ITEM_MODELS,
        BLOCK_MODELS
};

    public static final Set<String> BLOCK_TYPES = new HashSet<String>(Arrays.asList("simple_block"));
    public static final Set<String> ITEM_TYPES = new HashSet<String>(Arrays.asList("simple_item"));

    private static JsonObject RESOURCE_META = new JsonObject();
    private static JsonObject RESOURCE_META_CONTAINER = new JsonObject();

    public static JsonObject getResourceMeta(){
        RESOURCE_META.addProperty("pack_format", 5);
        RESOURCE_META.addProperty("description", "The generated resourcepack for Simple Block and Item Creation.");
        RESOURCE_META_CONTAINER.add("pack", RESOURCE_META);
        return RESOURCE_META_CONTAINER;
    }

    public static final String MODID = "simple_block_item_creation";

    public static final String MOD_NAME = "Simple Block and Item Creation";

    private static JsonObject TEST_BLOCK = new JsonObject();
    private static JsonObject TEST_BLOCK_DISPLAY = new JsonObject();
    private static JsonObject TEST_BLOCK_TEXTURES = new JsonObject();
    private static JsonObject TEST_ITEM = new JsonObject();

    public static JsonObject getTestBlock(){   
        TEST_BLOCK.addProperty("type", "simple_block");
        TEST_BLOCK.addProperty("id", "test_block");
        TEST_BLOCK.addProperty("material", "stone");
        TEST_BLOCK.addProperty("lightlevel", 2);
        TEST_BLOCK_TEXTURES.addProperty("#all", "minecraft:block/gold_block");
        TEST_BLOCK_DISPLAY.addProperty("model", "minecraft:block/cube_all");
        TEST_BLOCK_DISPLAY.add("textures", TEST_BLOCK_TEXTURES);
        TEST_BLOCK.add("display", TEST_BLOCK_DISPLAY);
        logger.info(TEST_BLOCK.toString());
        return TEST_BLOCK;
    }

    public static JsonObject getTestItem(){   
        TEST_ITEM.addProperty("type", "simple_item");
        TEST_ITEM.addProperty("id", "test_item");
        logger.info(TEST_ITEM.toString());
        return TEST_ITEM;
    }
}