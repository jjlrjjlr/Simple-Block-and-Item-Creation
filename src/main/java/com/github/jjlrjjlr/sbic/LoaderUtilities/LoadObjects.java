package com.github.jjlrjjlr.sbic.LoaderUtilities;

import java.util.Set;

import com.github.jjlrjjlr.sbic.Registries.Blocks;
import com.github.jjlrjjlr.sbic.Registries.Items;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadObjects {
    private static Logger logger = LogManager.getLogger();
    
    private static void loadBlocksFromSet(Set<JsonObject> setIn){

        try{
            logger.info("Loading " + setIn.size() + " blocks...");
            for (JsonObject objectOut : setIn){
                Blocks.registerBlocks(objectOut);
            }
        } catch(NullPointerException e){
            logger.error("LoadObjects.loadBlocksFromSet(Set<JsonObject> setIn) : ln 14 - 24 ; setIn contains no objects, returned null ; " + e);
        }
    }

    private static void loadItemsFromSet(Set<JsonObject> setIn){
        if (setIn.size() == 0){ return;}
        try{
            logger.info("Loading " + setIn.size() + " items...");
            for (JsonObject objectOut : setIn){
                Items.registerItems(objectOut);
            }
        } catch(NullPointerException e){
            logger.error("LoadObjects.loadItemsFromSet(Set<JsonObject> setIn) : ln 26 - 36 ; setIn contains no objects, returned null ; " + e);
        }
    }

    public static void loadFromSets(Set<JsonObject> setBlocks, Set<JsonObject> setItems) {
        loadBlocksFromSet(setBlocks);
        loadItemsFromSet(setItems);
    }
}