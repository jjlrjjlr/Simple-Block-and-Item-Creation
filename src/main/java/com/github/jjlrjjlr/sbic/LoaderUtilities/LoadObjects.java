package com.github.jjlrjjlr.sbic.LoaderUtilities;

import java.util.Set;

import com.github.jjlrjjlr.sbic.References;
import com.github.jjlrjjlr.sbic.Registries.Blocks;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadObjects {
    private static Logger logger = LogManager.getLogger();
    
    private static void loadBlocksFromSet(Set<JsonObject> setIn){

        try{
            for (JsonObject objectOut : setIn){
                Blocks.registerBlocks(objectOut);

            }
        } catch(NullPointerException e){
            logger.error("Null recieved.");
        }
    }

    private static void loadItemsFromSet(Set<JsonObject> setIn){
        if (setIn.size() == 0){ return;}
        try{
            for (JsonObject objectOut : setIn){
                System.out.println(objectOut.get("id").getAsString() + " of type " + objectOut.get("type").getAsString() + " has been found, but a register method does not yet exist. This feature coming soon.");
            }
        } catch(NullPointerException e){
            logger.error(e);
        }
    }

    public static void loadFromSets(Set<JsonObject> setBlocks, Set<JsonObject> setItems) {
        loadBlocksFromSet(setBlocks);
        loadItemsFromSet(setItems);
    }
}