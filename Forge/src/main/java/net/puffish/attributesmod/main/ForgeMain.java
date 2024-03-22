package net.puffish.attributesmod.main;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.Registrar;

@Mod(AttributesMod.MOD_ID)
public class ForgeMain {

	public ForgeMain() {
		AttributesMod.setup(new RegistrarImpl());
	}

	private record RegistrarImpl() implements Registrar {
		@Override
		public <V, T extends V> void register(Registry<V> registry, Identifier id, T entry) {
			var deferredRegister = DeferredRegister.create(registry.getKey(), id.getNamespace());
			deferredRegister.register(id.getPath(), () -> entry);
			deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
		}
	}
}
