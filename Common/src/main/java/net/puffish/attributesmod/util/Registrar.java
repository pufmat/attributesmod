package net.puffish.attributesmod.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Registrar {
	<V, T extends V> void register(Registry<V> registry, Identifier id, T entry);
}
