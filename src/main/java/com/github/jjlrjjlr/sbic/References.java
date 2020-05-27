package com.github.jjlrjjlr.sbic;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

public class References {
    
    public static final File CONFIG_DIRECTORY = new File(FabricLoader.getInstance().getConfigDirectory() + "/SimpleBlockItemCreation");

    public static final File BLOCK_CONFIG = new File(CONFIG_DIRECTORY + "/CustomBlocks"); 

    public static final File ITEM_CONFIG = new File(CONFIG_DIRECTORY + "/CustomItems");

    public static final File BLOCK_TEXTURES = new File(CONFIG_DIRECTORY + "/textures/Blocks");

    public static final File ITEM_TEXTURES = new File(CONFIG_DIRECTORY + "/textures/Items");

    public static final File[] DIRECTORIES = {
        CONFIG_DIRECTORY, 
        BLOCK_CONFIG, 
        ITEM_CONFIG, 
        ITEM_TEXTURES, 
        BLOCK_TEXTURES
};

    public static final Set<String> BLOCK_TYPES = new HashSet<String>(Arrays.asList("simple_block"));
    public static final Set<String> ITEM_TYPES = new HashSet<String>(Arrays.asList("simple_item"));

    public static final String MODID = "simple_block_item_creation";

    public static JsonObject TEST_BLOCK = new JsonObject();
    public static JsonObject TEST_ITEM = new JsonObject();

    public static JsonObject getTestBlock(){   
        TEST_BLOCK.addProperty("type", "simple_block");
        TEST_BLOCK.addProperty("id", "test_block");
        TEST_BLOCK.addProperty("material", "stone");
        TEST_BLOCK.addProperty("lightlevel", 2);
        System.out.println(TEST_BLOCK.toString());
        return TEST_BLOCK;
    }

    public static JsonObject getTestItem(){   
        TEST_ITEM.addProperty("type", "simple_item");
        TEST_ITEM.addProperty("id", "test_item");
        System.out.println(TEST_ITEM.toString());
        return TEST_ITEM;
    }
}