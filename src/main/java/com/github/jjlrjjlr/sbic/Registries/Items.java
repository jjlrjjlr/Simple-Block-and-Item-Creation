package com.github.jjlrjjlr.sbic.Registries;

import com.github.jjlrjjlr.sbic.Main;
import com.github.jjlrjjlr.sbic.References;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Items {
    private static Logger logger = LogManager.getLogger();
    //private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static FoodComponent buildItemFoodComponent(JsonObject jsonIn){
        FoodComponent.Builder foodComponent = new FoodComponent.Builder();
        if (jsonIn.has("hunger")){
            foodComponent.hunger(jsonIn.get("hunger").getAsInt());
        } else{
            foodComponent.hunger(1);
        }
        if (jsonIn.has("saturation")){
            foodComponent.saturationModifier(jsonIn.get("saturation").getAsInt());
        } else{
            foodComponent.saturationModifier(1);
        }
        if (jsonIn.has("is_meat") && jsonIn.get("is_meat").getAsBoolean()){
            foodComponent.meat();
        }
        if (jsonIn.has("is_always_edible") && jsonIn.get("is_always_edible").getAsBoolean()){
            foodComponent.alwaysEdible();
        }
        if (jsonIn.has("is_snack") && jsonIn.get("is_snack").getAsBoolean()){
            foodComponent.snack();
        }    
        if (jsonIn.has("effects")){
            
            jsonIn.get("effects").getAsJsonArray().iterator().forEachRemaining(entry -> {
                JsonObject value = entry.getAsJsonObject();
                StatusEffect effect = value.has("effect_id") ? Registry.STATUS_EFFECT.get(new Identifier(value.has("namespace") ? value.get("namespace").getAsString() : "minecraft", value.get("effect_id").getAsString())) : StatusEffects.NIGHT_VISION;
                int duration = value.has("duration") ? value.get("duration").getAsInt() : 1;
                int amplifier = value.has("amplifier") ? value.get("amplifier").getAsInt() : 0;
                boolean ambient = value.has("is_ambient") ? value.get("is_ambient").getAsBoolean() : false;
                boolean visible = value.has("is_visible") ? value.get("is_visible").getAsBoolean() : false;
                Float chance = value.has("chance") ? value.get("chance").getAsFloat() : 1.0f;
                    
                foodComponent.statusEffect(new StatusEffectInstance(effect, duration, amplifier, ambient, visible), chance);                    
            });
        }

        return foodComponent.build();
    }

    private static Item.Settings buildItemSettings(JsonObject jsonIn){
        Item.Settings settings = new Item.Settings();
        if (jsonIn.has("max_count")){
            settings.maxCount(jsonIn.get("max_count").getAsInt());
        }
        if (jsonIn.has("is_fireproof")){
            settings.fireproof();
        }
        if (jsonIn.has("max_damage")){
            settings.maxDamage(jsonIn.get("max_damage").getAsInt());
        }
        if (jsonIn.has("food")){
            settings.food(buildItemFoodComponent(jsonIn.get("food").getAsJsonObject()));
        }
        if (jsonIn.has("rarity")){
            settings.rarity(Rarity.valueOf(jsonIn.get("rarity").getAsString()));
        }

        return settings;
    }

    public static void registerBlockItems(JsonObject jsonIn, Block blockInstance){
        if (!jsonIn.has("block_item")){
            logger.warn("Block " + jsonIn.get("id").getAsString() + " does not declare properties of an item; A default item will be generated;");
            Registry.register(Registry.ITEM, new Identifier(References.MODID, jsonIn.get("id").getAsString()), new BlockItem(blockInstance, new Item.Settings().group(Main.SIMPLE_BLOCK_ITEM_CREATION_ITEMGROUP)));
        } else{
            Registry.register(Registry.ITEM, new Identifier(References.MODID, jsonIn.get("id").getAsString()), new BlockItem(blockInstance, buildItemSettings(jsonIn.get("block_item").getAsJsonObject())));
        }
    }

    public static void registerItems(JsonObject jsonIn){
        Registry.register(Registry.ITEM, new Identifier(References.MODID, jsonIn.get("id").getAsString()), new Item(buildItemSettings(jsonIn)));
    }
}