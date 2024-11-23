package net.puffish.attributesmod.util;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;

public class RegistryEntryReferenceWrapper<T> extends RegistryEntry.Reference<T> {
	private final RegistryEntry.Reference<T> original;

	public RegistryEntryReferenceWrapper(RegistryEntryOwner<T> owner, RegistryEntry.Reference<T> original) {
		super(Type.STAND_ALONE, owner, original.registryKey(), original.value());
		this.original = original;
	}

	public Reference<T> getOriginal() {
		return original;
	}
}
