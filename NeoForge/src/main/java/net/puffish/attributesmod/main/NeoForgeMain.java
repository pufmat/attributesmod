package net.puffish.attributesmod.main;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.puffish.attributesmod.AttributesMod;

import java.util.ArrayList;
import java.util.List;

@Mod(AttributesMod.MOD_ID)
public class NeoForgeMain {

	public static final List<DeferredRegister<?>> DEFERRED_REGISTERS = new ArrayList<>();

	public NeoForgeMain(IEventBus modEventBus) {
		AttributesMod.setup();
		for (var deferredRegister : DEFERRED_REGISTERS) {
			deferredRegister.register(modEventBus);
		}
	}
}
