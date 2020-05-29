package com.github.jjlrjjlr.sbic;

import java.io.File;

import com.github.jjlrjjlr.sbic.LoaderUtilities.LoadJSON;
import com.github.jjlrjjlr.sbic.LoaderUtilities.LoadObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
	Logger logger = LogManager.getLogger();

	@Override
	public void onInitialize() {
		logger.info("Simple Block and Item Creation started.");
		fileChecks();
		LoadObjects.loadFromSets(LoadJSON.getBlockFiles(), LoadJSON.getItemFiles());
	}

	public static final ItemGroup SIMPLE_BLOCK_ITEM_CREATION_ITEMGROUP = FabricItemGroupBuilder.build(
			new Identifier(References.MODID, "blocks"), 
			() -> new ItemStack(Blocks.ANVIL));

	private static void fileChecks(){

		for(File f : References.DIRECTORIES){
			if(!f.exists()){
				f.mkdirs();
			}
		}
	}
}
