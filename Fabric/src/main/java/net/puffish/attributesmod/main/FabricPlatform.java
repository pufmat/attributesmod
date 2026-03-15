package net.puffish.attributesmod.main;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.puffish.attributesmod.util.Platform;

public class FabricPlatform implements Platform {

	@Override
	public <T> Holder<T> registerForHolder(Registry<T> registry, Identifier id, T entry) {
		return Registry.registerForHolder(registry, id, entry);
	}

}
