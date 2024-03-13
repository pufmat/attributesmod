package net.puffish.attributesmod.main;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.Registrar;

@Mod(AttributesMod.MOD_ID)
public class NeoForgeMain {

	public NeoForgeMain(IEventBus modEventBus) {
		AttributesMod.setup(new RegistrarImpl(modEventBus));
	}

	private record RegistrarImpl(IEventBus modEventBus) implements Registrar {
		@Override
		public <V, T extends V> void register(Registry<V> registry, Identifier id, T entry) {
			var deferredRegister = DeferredRegister.create(registry.getKey(), id.getNamespace());
			deferredRegister.register(id.getPath(), () -> entry);
			deferredRegister.register(modEventBus);
		}
	}
}
