package com.github.jjlrjjlr.sbic;

import java.io.File;

import com.github.jjlrjjlr.sbic.LoaderUtilities.LoadJSON;
import com.github.jjlrjjlr.sbic.LoaderUtilities.LoadObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	Logger logger = LogManager.getLogger();

	@Override
	public void onInitialize() {
		logger.info("Simple Block and Item Creation running.");
		fileChecks();
		LoadObjects.loadFromSets(LoadJSON.getBlockFiles(), LoadJSON.getItemFiles());
	}

	private static void fileChecks(){

		for(File f : References.DIRECTORIES){
			if(!f.exists()){
				f.mkdirs();
			}
		}
	}
}
