package com.github.jjlrjjlr.sbic.Registries;

import com.github.jjlrjjlr.sbic.Main;
import com.github.jjlrjjlr.sbic.References;
import com.github.jjlrjjlr.sbic.Blocks.BlockModels;
import com.github.jjlrjjlr.sbic.Blocks.BlockStateGenerator;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
        if (jsonIn.has("slipperiness")){
            settings.slipperiness(jsonIn.get("slipperiness").getAsFloat());
        }
        if (jsonIn.has("velocity_multiplier")){
            settings.velocityMultiplier(jsonIn.get("velocity_multiplier").getAsFloat());
        }
        if (jsonIn.has("break_by_hand")){
            settings.breakByHand(true);
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
        logger.info("Creating simple_block with id: " + id);
        Block simpleBlockInstance = new Block(genSettingsObject(customBlock));
        Registry.register(Registry.BLOCK, new Identifier(References.MODID, id), simpleBlockInstance);
        
        Registry.register(Registry.ITEM, new Identifier(References.MODID, id), new BlockItem(simpleBlockInstance, new Item.Settings().group(Main.SIMPLE_BLOCK_ITEM_CREATION_ITEMGROUP)));
        
        BlockModels.blockModelWriter(customBlock);
        
        BlockStateGenerator.generateCubeBlockstates(customBlock);
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
            case "barrier":
                return Material.BARRIER;
            case "stone":
                return Material.STONE;
            case "":
                logger.error("Material empty or null for block " + id + ", this is a required field.");
            default:
                return Material.WOOL;
        }
    }
}