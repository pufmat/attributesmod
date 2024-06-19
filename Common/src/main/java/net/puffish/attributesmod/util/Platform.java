package net.puffish.attributesmod.util;

import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ServiceLoader;

public interface Platform {
	Platform INSTANCE = ServiceLoader.load(Platform.class).findFirst().orElseThrow();

	<T> RegistryEntry<T> registerReference(Registry<T> registry, Identifier id, T entry);
}
