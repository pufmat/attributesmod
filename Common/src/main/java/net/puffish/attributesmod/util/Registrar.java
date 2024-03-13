package net.puffish.attributesmod.util;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface Registrar {
	<V, T extends V> void register(Registry<V> registry, Identifier id, T entry);
}
