package net.puffish.attributesmod.mixin;

import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RegistryEntry.Reference.class)
public interface RegistryEntryReferenceInvoker<T> {
	@Invoker("setValue")
	void invokeSetValue(T value);
}
