package net.puffish.attributesmod.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class DamageKind {
	private final DamageSource source;

	public DamageKind(DamageSource source) {
		this.source = source;
	}

	public static DamageKind of(DamageSource source) {
		return new DamageKind(source);
	}

	public boolean isMagic() {
		return source.isOf(DamageTypes.MAGIC)
				|| source.isOf(DamageTypes.INDIRECT_MAGIC)
				|| source.isIn(TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("c", "is_magic")))
				|| source.isIn(TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("neoforge", "is_magic")));
	}

	public boolean isProjectile() {
		return (source.getAttacker() != null)
				&& (!source.isDirect() || source.isIn(DamageTypeTags.IS_PROJECTILE));
	}

	public boolean isMelee() {
		return (source.getAttacker() != null)
				&& source.isDirect()
				&& !source.isIn(DamageTypeTags.IS_PROJECTILE);
	}
}
