package net.puffish.attributesmod.main;

import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.puffish.attributesmod.util.Platform;

public class FabricPlatform implements Platform {

	@Override
	public <T> RegistryEntry.Reference<T> registerReference(Registry<T> registry, Identifier id, T entry) {
		return Registry.registerReference(registry, id, entry);
	}

}
