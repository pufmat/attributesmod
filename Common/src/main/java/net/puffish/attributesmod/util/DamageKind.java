package net.puffish.attributesmod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;

public class DamageKind {
	private final DamageSource source;

	public DamageKind(DamageSource source) {
		this.source = source;
	}

	public static DamageKind of(DamageSource source) {
		return new DamageKind(source);
	}

	public boolean isMagic() {
		return source.is(DamageTypes.MAGIC)
				|| source.is(DamageTypes.INDIRECT_MAGIC)
				|| source.is(TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath("c", "is_magic")))
				|| source.is(TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath("neoforge", "is_magic")));
	}

	public boolean isProjectile() {
		return (source.getEntity() != null)
				&& (!source.isDirect() || source.is(DamageTypeTags.IS_PROJECTILE));
	}

	public boolean isMelee() {
		return (source.getEntity() != null)
				&& source.isDirect()
				&& !source.is(DamageTypeTags.IS_PROJECTILE);
	}
}
