package dev.xtracube.crosshairs;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CubesCrosshair implements ModInitializer {
	public static final String MOD_ID = "cubes-crosshair";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("wasup chat, c'est xtracube with un cool minecraft mod");
	}
}