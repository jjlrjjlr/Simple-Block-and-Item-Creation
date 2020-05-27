package com.github.jjlrjjlr.sbic.Registries;

import com.github.jjlrjjlr.sbic.References;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {
    private static Logger logger = LogManager.getLogger();

    private static FabricBlockSettings genSettingsObject(JsonObject jsonIn) {
        FabricBlockSettings settings = FabricBlockSettings.of(getMaterialfromObject(jsonIn.get("material").getAsString(), jsonIn.get("id").getAsString()));
        if (jsonIn.has("hardness")) {
            settings.hardness(jsonIn.get("hardness").getAsFloat());
        }
        if (jsonIn.has("lightlevel")){
            settings.lightLevel(jsonIn.get("lightlevel").getAsInt());
        }
        if (jsonIn.has("resistance")){
            settings.resistance(jsonIn.get("resistance").getAsFloat());
        }
        return settings;
    }

    public static void registerBlocks(JsonObject objectIn){
        switch (objectIn.get("type").getAsString().toLowerCase()){
            case "simple_block":
                registerSimpleBlock(objectIn);
                break;
            default:
                logger.error("Unknown error, block id " + objectIn.get("id").getAsString() + " was not registered.");
                break;
        }
    }

    public static void registerSimpleBlock(JsonObject customBlock){
        String id = customBlock.get("id").getAsString().toLowerCase();
        Block simpleBlockInstance = new Block(genSettingsObject(customBlock));
        Registry.register(Registry.BLOCK, new Identifier(References.MODID, id), simpleBlockInstance);
        
    }

    private static Material getMaterialfromObject(String materialIn, String id){
        
        switch(materialIn.toLowerCase()){
            case "aggregate":
                return Material.AGGREGATE;
            case "air":
                return Material.AIR;
            case "bamboo":
                return Material.BAMBOO;
            case "bamboo_sapling":
                return Material.BAMBOO_SAPLING;
            case "stone":
                return Material.STONE;
            case "":
                logger.error("Material empty or null for block " + id + ", this is a required field.");
            default:
                return Material.WOOL;
        }
    }
}