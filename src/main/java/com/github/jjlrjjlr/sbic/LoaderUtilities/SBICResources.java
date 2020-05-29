package com.github.jjlrjjlr.sbic.LoaderUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.github.jjlrjjlr.sbic.References;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProfile.Factory;
import net.minecraft.resource.ResourcePackProvider;

public class SBICResources implements ResourcePackProvider {
    private static File resourceDirectory = References.RESOURCE_LOCATION;
    private static File metaFile = new File(resourceDirectory, "pack.mcmeta");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public <T extends ResourcePackProfile> void register(Map<String, T> registry, Factory<T> factory) {
        
        if (!metaFile.exists()){
            try{
                FileWriter writer = new FileWriter(metaFile);
                writer.write(gson.toJson(References.getResourceMeta()));
                writer.flush();
                writer.close();
            } catch(IOException e){
                
            }
        }

        T packProfile = ResourcePackProfile.of("Simple Block and Item Creation generated resources", true, () -> new DirectoryResourcePack(resourceDirectory), factory, ResourcePackProfile.InsertionPosition.TOP);
        try{
            registry.put("Simple Block and Item Creation generated resources", packProfile);
        } catch (NullPointerException e) {

        }
    }
}