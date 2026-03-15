package net.puffish.attributesmod.util;

import java.util.ServiceLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

public interface Platform {
	Platform INSTANCE = ServiceLoader.load(Platform.class).findFirst().orElseThrow();

	<T> Holder<T> registerForHolder(Registry<T> registry, Identifier id, T entry);
}
