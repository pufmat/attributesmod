package net.puffish.attributesmod;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.puffish.attributesmod.api.PuffishAttributes;
import net.puffish.attributesmod.util.DeferredSetup;
import net.puffish.attributesmod.util.DynamicModificationImpl;
import net.puffish.attributesmod.util.Signed;

public class AttributesMod {
	public static final String MOD_ID = DeferredSetup.MOD_ID;

	@Deprecated
	public static final Identifier STAMINA_ID = PuffishAttributes.STAMINA_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> STAMINA = PuffishAttributes.STAMINA;
	@Deprecated
	public static final Identifier MAGIC_DAMAGE_ID = PuffishAttributes.MAGIC_DAMAGE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> MAGIC_DAMAGE = PuffishAttributes.MAGIC_DAMAGE;
	@Deprecated
	public static final Identifier MELEE_DAMAGE_ID = PuffishAttributes.MELEE_DAMAGE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> MELEE_DAMAGE = PuffishAttributes.MELEE_DAMAGE;
	@Deprecated
	public static final Identifier RANGED_DAMAGE_ID = PuffishAttributes.RANGED_DAMAGE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> RANGED_DAMAGE = PuffishAttributes.RANGED_DAMAGE;
	@Deprecated
	public static final Identifier TAMED_DAMAGE_ID = PuffishAttributes.TAMED_DAMAGE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> TAMED_DAMAGE = PuffishAttributes.TAMED_DAMAGE;
	@Deprecated
	public static final Identifier FORTUNE_ID = PuffishAttributes.FORTUNE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> FORTUNE = PuffishAttributes.FORTUNE;
	@Deprecated
	public static final Identifier HEALING_ID = PuffishAttributes.HEALING_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> HEALING = PuffishAttributes.HEALING;
	@Deprecated
	public static final Identifier JUMP_ID = PuffishAttributes.JUMP_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> JUMP = PuffishAttributes.JUMP;
	@Deprecated
	public static final Identifier RESISTANCE_ID = PuffishAttributes.RESISTANCE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> RESISTANCE = PuffishAttributes.RESISTANCE;
	@Deprecated
	public static final Identifier MAGIC_RESISTANCE_ID = PuffishAttributes.MAGIC_RESISTANCE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> MAGIC_RESISTANCE = PuffishAttributes.MAGIC_RESISTANCE;
	@Deprecated
	public static final Identifier MELEE_RESISTANCE_ID = PuffishAttributes.MELEE_RESISTANCE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> MELEE_RESISTANCE = PuffishAttributes.MELEE_RESISTANCE;
	@Deprecated
	public static final Identifier RANGED_RESISTANCE_ID = PuffishAttributes.RANGED_RESISTANCE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> RANGED_RESISTANCE = PuffishAttributes.RANGED_RESISTANCE;
	@Deprecated
	public static final Identifier TAMED_RESISTANCE_ID = PuffishAttributes.TAMED_RESISTANCE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> TAMED_RESISTANCE = PuffishAttributes.TAMED_RESISTANCE;
	@Deprecated
	public static final Identifier MINING_SPEED_ID = PuffishAttributes.MINING_SPEED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> MINING_SPEED = PuffishAttributes.MINING_SPEED;
	@Deprecated
	public static final Identifier PICKAXE_SPEED_ID = PuffishAttributes.PICKAXE_SPEED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> PICKAXE_SPEED = PuffishAttributes.PICKAXE_SPEED;
	@Deprecated
	public static final Identifier AXE_SPEED_ID = PuffishAttributes.AXE_SPEED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> AXE_SPEED = PuffishAttributes.AXE_SPEED;
	@Deprecated
	public static final Identifier SHOVEL_SPEED_ID = PuffishAttributes.SHOVEL_SPEED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> SHOVEL_SPEED = PuffishAttributes.SHOVEL_SPEED;
	@Deprecated
	public static final Identifier SPRINTING_SPEED_ID = PuffishAttributes.SPRINTING_SPEED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> SPRINTING_SPEED = PuffishAttributes.SPRINTING_SPEED;
	@Deprecated
	public static final Identifier KNOCKBACK_ID = PuffishAttributes.KNOCKBACK_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> KNOCKBACK = PuffishAttributes.KNOCKBACK;
	@Deprecated
	public static final Identifier REPAIR_COST_ID = PuffishAttributes.REPAIR_COST_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> REPAIR_COST = PuffishAttributes.REPAIR_COST;
	@Deprecated
	public static final Identifier ARMOR_SHRED_ID = PuffishAttributes.ARMOR_SHRED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> ARMOR_SHRED = PuffishAttributes.ARMOR_SHRED;
	@Deprecated
	public static final Identifier TOUGHNESS_SHRED_ID = PuffishAttributes.TOUGHNESS_SHRED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> TOUGHNESS_SHRED = PuffishAttributes.TOUGHNESS_SHRED;
	@Deprecated
	public static final Identifier PROTECTION_SHRED_ID = PuffishAttributes.PROTECTION_SHRED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> PROTECTION_SHRED = PuffishAttributes.PROTECTION_SHRED;
	@Deprecated
	public static final Identifier RESISTANCE_SHRED_ID = PuffishAttributes.RESISTANCE_SHRED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> RESISTANCE_SHRED = PuffishAttributes.RESISTANCE_SHRED;
	@Deprecated
	public static final Identifier MAGIC_RESISTANCE_SHRED_ID = PuffishAttributes.MAGIC_RESISTANCE_SHRED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> MAGIC_RESISTANCE_SHRED = PuffishAttributes.MAGIC_RESISTANCE_SHRED;
	@Deprecated
	public static final Identifier MELEE_RESISTANCE_SHRED_ID = PuffishAttributes.MELEE_RESISTANCE_SHRED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> MELEE_RESISTANCE_SHRED = PuffishAttributes.MELEE_RESISTANCE_SHRED;
	@Deprecated
	public static final Identifier RANGED_RESISTANCE_SHRED_ID = PuffishAttributes.RANGED_RESISTANCE_SHRED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> RANGED_RESISTANCE_SHRED = PuffishAttributes.RANGED_RESISTANCE_SHRED;
	@Deprecated
	public static final Identifier NATURAL_REGENERATION_ID = PuffishAttributes.NATURAL_REGENERATION_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> NATURAL_REGENERATION = PuffishAttributes.NATURAL_REGENERATION;
	@Deprecated
	public static final Identifier STEALTH_ID = PuffishAttributes.STEALTH_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> STEALTH = PuffishAttributes.STEALTH;
	@Deprecated
	public static final Identifier LIFE_STEAL_ID = PuffishAttributes.LIFE_STEAL_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> LIFE_STEAL = PuffishAttributes.LIFE_STEAL;
	@Deprecated
	public static final Identifier FALL_REDUCTION_ID = PuffishAttributes.FALL_REDUCTION_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> FALL_REDUCTION = PuffishAttributes.FALL_REDUCTION;
	@Deprecated
	public static final Identifier BOW_PROJECTILE_SPEED_ID = PuffishAttributes.BOW_PROJECTILE_SPEED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> BOW_PROJECTILE_SPEED = PuffishAttributes.BOW_PROJECTILE_SPEED;
	@Deprecated
	public static final Identifier CROSSBOW_PROJECTILE_SPEED_ID = PuffishAttributes.CROSSBOW_PROJECTILE_SPEED_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> CROSSBOW_PROJECTILE_SPEED = PuffishAttributes.CROSSBOW_PROJECTILE_SPEED;
	@Deprecated
	public static final Identifier EXPERIENCE_ID = PuffishAttributes.EXPERIENCE_ID;
	@Deprecated
	public static final RegistryEntry<EntityAttribute> EXPERIENCE = PuffishAttributes.EXPERIENCE;

	public static void setup() {

	}

	@Deprecated
	public static Identifier createIdentifier(String path) {
		return DeferredSetup.createIdentifier(path);
	}

	@Deprecated
	public static EntityAttribute createClampedAttribute(Identifier id, double fallback, double min, double max) {
		return DeferredSetup.createClampedAttribute(id, fallback, min, max);
	}

	@Deprecated
	public static RegistryEntry<EntityAttribute> registerAttribute(Identifier id, EntityAttribute attribute) {
		return DeferredSetup.registerAttribute(id, attribute);
	}

	// This method is left for backward compatibility in case someone uses it.
	@Deprecated
	@SafeVarargs
	public static double applyAttributeModifiers(
			double initial,
			Signed<EntityAttributeInstance>... attributes
	) {
		var dm = new DynamicModificationImpl();
		for (var signedAttribute : attributes) {
			dm.with(signedAttribute);
		}
		return dm.applyTo(initial);
	}

}
