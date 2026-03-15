package net.puffish.attributesmod.main;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.puffish.attributesmod.util.Platform;

public class NeoForgePlatform implements Platform {

	@Override
	public <T> Holder<T> registerForHolder(Registry<T> registry, Identifier id, T entry) {
		var deferredRegister = DeferredRegister.create(registry.key(), id.getNamespace());
		NeoForgeMain.DEFERRED_REGISTERS.add(deferredRegister);
		return deferredRegister.register(id.getPath(), () -> entry).getDelegate();
	}

}
