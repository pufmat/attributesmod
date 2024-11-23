package net.puffish.attributesmod.mixin;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RegistryEntry.Reference.class)
public interface RegistryEntryReferenceInvoker<T> {
	@Invoker("setKeyAndValue")
	void invokeSetKeyAndValue(RegistryKey<T> key, T value);
}
