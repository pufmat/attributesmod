package net.puffish.attributesmod.main;

import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.puffish.attributesmod.util.Platform;

public class NeoForgePlatform implements Platform {

	@Override
	public <T> RegistryEntry<T> registerReference(Registry<T> registry, Identifier id, T entry) {
		var deferredRegister = DeferredRegister.create(registry.getKey(), id.getNamespace());
		NeoForgeMain.DEFERRED_REGISTERS.add(deferredRegister);
		return deferredRegister.register(id.getPath(), () -> entry).getDelegate();
	}

}
