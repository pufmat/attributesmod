package net.puffish.attributesmod;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.puffish.attributesmod.attribute.DynamicEntityAttribute;
import net.puffish.attributesmod.mixin.EntityAttributeInstanceInvoker;
import net.puffish.attributesmod.util.Platform;
import net.puffish.attributesmod.util.Signed;

public class AttributesMod {
	public static final String MOD_ID = "puffish_attributes";

	public static final Identifier STAMINA_ID
			= AttributesMod.createIdentifier("stamina");
	public static final RegistryEntry<EntityAttribute> STAMINA = registerAttribute(
			STAMINA_ID,
			createClampedAttribute(
					STAMINA_ID,
					4.0,
					0.0,
					1024.0
			).setTracked(true)
	);

	public static final Identifier MAGIC_DAMAGE_ID
			= AttributesMod.createIdentifier("magic_damage");
	public static final RegistryEntry<EntityAttribute> MAGIC_DAMAGE = registerAttribute(
			MAGIC_DAMAGE_ID,
			createDynamicAttribute(MAGIC_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier MELEE_DAMAGE_ID
			= AttributesMod.createIdentifier("melee_damage");
	public static final RegistryEntry<EntityAttribute> MELEE_DAMAGE = registerAttribute(
			MELEE_DAMAGE_ID,
			createDynamicAttribute(MELEE_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier RANGED_DAMAGE_ID
			= AttributesMod.createIdentifier("ranged_damage");
	public static final RegistryEntry<EntityAttribute> RANGED_DAMAGE = registerAttribute(
			RANGED_DAMAGE_ID,
			createDynamicAttribute(RANGED_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier FORTUNE_ID
			= AttributesMod.createIdentifier("fortune");
	public static final RegistryEntry<EntityAttribute> FORTUNE = registerAttribute(
			FORTUNE_ID,
			createDynamicAttribute(FORTUNE_ID).setTracked(true)
	);

	public static final Identifier HEALING_ID
			= AttributesMod.createIdentifier("healing");
	public static final RegistryEntry<EntityAttribute> HEALING = registerAttribute(
			HEALING_ID,
			createDynamicAttribute(HEALING_ID).setTracked(true)
	);

	public static final Identifier JUMP_ID
			= AttributesMod.createIdentifier("jump");
	public static final RegistryEntry<EntityAttribute> JUMP = registerAttribute(
			JUMP_ID,
			createDynamicAttribute(JUMP_ID).setTracked(true)
	);

	public static final Identifier RESISTANCE_ID
			= AttributesMod.createIdentifier("resistance");
	public static final RegistryEntry<EntityAttribute> RESISTANCE = registerAttribute(
			RESISTANCE_ID,
			createDynamicAttribute(RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier MAGIC_RESISTANCE_ID
			= AttributesMod.createIdentifier("magic_resistance");
	public static final RegistryEntry<EntityAttribute> MAGIC_RESISTANCE = registerAttribute(
			MAGIC_RESISTANCE_ID,
			createDynamicAttribute(MAGIC_RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier MELEE_RESISTANCE_ID
			= AttributesMod.createIdentifier("melee_resistance");
	public static final RegistryEntry<EntityAttribute> MELEE_RESISTANCE = registerAttribute(
			MELEE_RESISTANCE_ID,
			createDynamicAttribute(MELEE_RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier RANGED_RESISTANCE_ID
			= AttributesMod.createIdentifier("ranged_resistance");
	public static final RegistryEntry<EntityAttribute> RANGED_RESISTANCE = registerAttribute(
			RANGED_RESISTANCE_ID,
			createDynamicAttribute(RANGED_RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier MINING_SPEED_ID
			= AttributesMod.createIdentifier("mining_speed");
	public static final RegistryEntry<EntityAttribute> MINING_SPEED = registerAttribute(
			MINING_SPEED_ID,
			createDynamicAttribute(MINING_SPEED_ID).setTracked(true)
	);

	public static final Identifier PICKAXE_SPEED_ID
			= AttributesMod.createIdentifier("pickaxe_speed");
	public static final RegistryEntry<EntityAttribute> PICKAXE_SPEED = registerAttribute(
			PICKAXE_SPEED_ID,
			createDynamicAttribute(PICKAXE_SPEED_ID).setTracked(true)
	);

	public static final Identifier AXE_SPEED_ID
			= AttributesMod.createIdentifier("axe_speed");
	public static final RegistryEntry<EntityAttribute> AXE_SPEED = registerAttribute(
			AXE_SPEED_ID,
			createDynamicAttribute(AXE_SPEED_ID).setTracked(true)
	);

	public static final Identifier SHOVEL_SPEED_ID
			= AttributesMod.createIdentifier("shovel_speed");
	public static final RegistryEntry<EntityAttribute> SHOVEL_SPEED = registerAttribute(
			SHOVEL_SPEED_ID,
			createDynamicAttribute(SHOVEL_SPEED_ID).setTracked(true)
	);

	public static final Identifier SPRINTING_SPEED_ID
			= AttributesMod.createIdentifier("sprinting_speed");
	public static final RegistryEntry<EntityAttribute> SPRINTING_SPEED = registerAttribute(
			SPRINTING_SPEED_ID,
			createDynamicAttribute(SPRINTING_SPEED_ID).setTracked(true)
	);

	public static final Identifier KNOCKBACK_ID
			= AttributesMod.createIdentifier("knockback");
	public static final RegistryEntry<EntityAttribute> KNOCKBACK = registerAttribute(
			KNOCKBACK_ID,
			createDynamicAttribute(KNOCKBACK_ID).setTracked(true)
	);

	public static final Identifier REPAIR_COST_ID
			= AttributesMod.createIdentifier("repair_cost");
	public static final RegistryEntry<EntityAttribute> REPAIR_COST = registerAttribute(
			REPAIR_COST_ID,
			createDynamicAttribute(REPAIR_COST_ID).setTracked(true)
	);

	public static final Identifier ARMOR_SHRED_ID
			= AttributesMod.createIdentifier("armor_shred");
	public static final RegistryEntry<EntityAttribute> ARMOR_SHRED = registerAttribute(
			ARMOR_SHRED_ID,
			createDynamicAttribute(ARMOR_SHRED_ID).setTracked(true)
	);

	public static final Identifier TOUGHNESS_SHRED_ID
			= AttributesMod.createIdentifier("toughness_shred");
	public static final RegistryEntry<EntityAttribute> TOUGHNESS_SHRED = registerAttribute(
			TOUGHNESS_SHRED_ID,
			createDynamicAttribute(TOUGHNESS_SHRED_ID).setTracked(true)
	);

	public static final Identifier PROTECTION_SHRED_ID
			= AttributesMod.createIdentifier("protection_shred");
	public static final RegistryEntry<EntityAttribute> PROTECTION_SHRED = registerAttribute(
			PROTECTION_SHRED_ID,
			createDynamicAttribute(PROTECTION_SHRED_ID).setTracked(true)
	);

	public static final Identifier NATURAL_REGENERATION_ID
			= AttributesMod.createIdentifier("natural_regeneration");
	public static final RegistryEntry<EntityAttribute> NATURAL_REGENERATION = registerAttribute(
			NATURAL_REGENERATION_ID,
			createDynamicAttribute(NATURAL_REGENERATION_ID).setTracked(true)
	);

	public static final Identifier STEALTH_ID
			= AttributesMod.createIdentifier("stealth");
	public static final RegistryEntry<EntityAttribute> STEALTH = registerAttribute(
			STEALTH_ID,
			createDynamicAttribute(STEALTH_ID).setTracked(true)
	);


	public static void setup() {

	}

	public static Identifier createIdentifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static EntityAttribute createClampedAttribute(Identifier id, double fallback, double min, double max) {
		return new ClampedEntityAttribute(
				id.toTranslationKey("attribute"),
				fallback,
				min,
				max
		);
	}

	public static EntityAttribute createDynamicAttribute(Identifier id) {
		return new DynamicEntityAttribute(
				id.toTranslationKey("attribute")
		);
	}

	public static RegistryEntry<EntityAttribute> registerAttribute(Identifier id, EntityAttribute attribute) {
		var ref = Platform.INSTANCE.registerReference(Registries.ATTRIBUTE, id, attribute);
		Platform.INSTANCE.registerAlias(Registries.ATTRIBUTE, id.withPrefixedPath("player."), id);
		return ref;
	}

	@SafeVarargs
	public static double applyAttributeModifiers(
			double initial,
			Signed<EntityAttributeInstance>... attributes
	) {
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((EntityAttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.ADD_VALUE)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> initial += modifier.value();
					case NEGATIVE -> initial -= modifier.value();
					default -> throw new IllegalStateException();
				}
			}
		}
		double result = initial;
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((EntityAttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result += initial * modifier.value();
					case NEGATIVE -> result -= initial * modifier.value();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((EntityAttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result *= 1.0 + modifier.value();
					case NEGATIVE -> result *= 1.0 - modifier.value();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			result = signedAttribute.value().getAttribute().value().clamp(result);
		}
		return result;
	}

}
