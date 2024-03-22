package net.puffish.attributesmod.main;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.puffish.attributesmod.AttributesMod;

public class FabricMain implements ModInitializer {

	@Override
	public void onInitialize() {
		AttributesMod.setup(Registry::register);
	}

}
