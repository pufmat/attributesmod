package net.puffish.attributesmod;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.puffish.attributesmod.api.DynamicEntityAttribute;
import net.puffish.attributesmod.util.DynamicModificationImpl;
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
			DynamicEntityAttribute.create(MAGIC_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier MELEE_DAMAGE_ID
			= AttributesMod.createIdentifier("melee_damage");
	public static final RegistryEntry<EntityAttribute> MELEE_DAMAGE = registerAttribute(
			MELEE_DAMAGE_ID,
			DynamicEntityAttribute.create(MELEE_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier RANGED_DAMAGE_ID
			= AttributesMod.createIdentifier("ranged_damage");
	public static final RegistryEntry<EntityAttribute> RANGED_DAMAGE = registerAttribute(
			RANGED_DAMAGE_ID,
			DynamicEntityAttribute.create(RANGED_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier FORTUNE_ID
			= AttributesMod.createIdentifier("fortune");
	public static final RegistryEntry<EntityAttribute> FORTUNE = registerAttribute(
			FORTUNE_ID,
			DynamicEntityAttribute.create(FORTUNE_ID).setTracked(true)
	);

	public static final Identifier HEALING_ID
			= AttributesMod.createIdentifier("healing");
	public static final RegistryEntry<EntityAttribute> HEALING = registerAttribute(
			HEALING_ID,
			DynamicEntityAttribute.create(HEALING_ID).setTracked(true)
	);

	public static final Identifier JUMP_ID
			= AttributesMod.createIdentifier("jump");
	public static final RegistryEntry<EntityAttribute> JUMP = registerAttribute(
			JUMP_ID,
			DynamicEntityAttribute.create(JUMP_ID).setTracked(true)
	);

	public static final Identifier RESISTANCE_ID
			= AttributesMod.createIdentifier("resistance");
	public static final RegistryEntry<EntityAttribute> RESISTANCE = registerAttribute(
			RESISTANCE_ID,
			DynamicEntityAttribute.create(RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier MAGIC_RESISTANCE_ID
			= AttributesMod.createIdentifier("magic_resistance");
	public static final RegistryEntry<EntityAttribute> MAGIC_RESISTANCE = registerAttribute(
			MAGIC_RESISTANCE_ID,
			DynamicEntityAttribute.create(MAGIC_RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier MELEE_RESISTANCE_ID
			= AttributesMod.createIdentifier("melee_resistance");
	public static final RegistryEntry<EntityAttribute> MELEE_RESISTANCE = registerAttribute(
			MELEE_RESISTANCE_ID,
			DynamicEntityAttribute.create(MELEE_RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier RANGED_RESISTANCE_ID
			= AttributesMod.createIdentifier("ranged_resistance");
	public static final RegistryEntry<EntityAttribute> RANGED_RESISTANCE = registerAttribute(
			RANGED_RESISTANCE_ID,
			DynamicEntityAttribute.create(RANGED_RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier MINING_SPEED_ID
			= AttributesMod.createIdentifier("mining_speed");
	public static final RegistryEntry<EntityAttribute> MINING_SPEED = registerAttribute(
			MINING_SPEED_ID,
			DynamicEntityAttribute.create(MINING_SPEED_ID).setTracked(true)
	);

	public static final Identifier PICKAXE_SPEED_ID
			= AttributesMod.createIdentifier("pickaxe_speed");
	public static final RegistryEntry<EntityAttribute> PICKAXE_SPEED = registerAttribute(
			PICKAXE_SPEED_ID,
			DynamicEntityAttribute.create(PICKAXE_SPEED_ID).setTracked(true)
	);

	public static final Identifier AXE_SPEED_ID
			= AttributesMod.createIdentifier("axe_speed");
	public static final RegistryEntry<EntityAttribute> AXE_SPEED = registerAttribute(
			AXE_SPEED_ID,
			DynamicEntityAttribute.create(AXE_SPEED_ID).setTracked(true)
	);

	public static final Identifier SHOVEL_SPEED_ID
			= AttributesMod.createIdentifier("shovel_speed");
	public static final RegistryEntry<EntityAttribute> SHOVEL_SPEED = registerAttribute(
			SHOVEL_SPEED_ID,
			DynamicEntityAttribute.create(SHOVEL_SPEED_ID).setTracked(true)
	);

	public static final Identifier SPRINTING_SPEED_ID
			= AttributesMod.createIdentifier("sprinting_speed");
	public static final RegistryEntry<EntityAttribute> SPRINTING_SPEED = registerAttribute(
			SPRINTING_SPEED_ID,
			DynamicEntityAttribute.create(SPRINTING_SPEED_ID).setTracked(true)
	);

	public static final Identifier KNOCKBACK_ID
			= AttributesMod.createIdentifier("knockback");
	public static final RegistryEntry<EntityAttribute> KNOCKBACK = registerAttribute(
			KNOCKBACK_ID,
			DynamicEntityAttribute.create(KNOCKBACK_ID).setTracked(true)
	);

	public static final Identifier REPAIR_COST_ID
			= AttributesMod.createIdentifier("repair_cost");
	public static final RegistryEntry<EntityAttribute> REPAIR_COST = registerAttribute(
			REPAIR_COST_ID,
			DynamicEntityAttribute.create(REPAIR_COST_ID).setTracked(true)
	);

	public static final Identifier ARMOR_SHRED_ID
			= AttributesMod.createIdentifier("armor_shred");
	public static final RegistryEntry<EntityAttribute> ARMOR_SHRED = registerAttribute(
			ARMOR_SHRED_ID,
			DynamicEntityAttribute.create(ARMOR_SHRED_ID).setTracked(true)
	);

	public static final Identifier TOUGHNESS_SHRED_ID
			= AttributesMod.createIdentifier("toughness_shred");
	public static final RegistryEntry<EntityAttribute> TOUGHNESS_SHRED = registerAttribute(
			TOUGHNESS_SHRED_ID,
			DynamicEntityAttribute.create(TOUGHNESS_SHRED_ID).setTracked(true)
	);

	public static final Identifier PROTECTION_SHRED_ID
			= AttributesMod.createIdentifier("protection_shred");
	public static final RegistryEntry<EntityAttribute> PROTECTION_SHRED = registerAttribute(
			PROTECTION_SHRED_ID,
			DynamicEntityAttribute.create(PROTECTION_SHRED_ID).setTracked(true)
	);

	public static final Identifier RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("resistance_shred");
	public static final RegistryEntry<EntityAttribute> RESISTANCE_SHRED = registerAttribute(
			RESISTANCE_SHRED_ID,
			DynamicEntityAttribute.create(RESISTANCE_SHRED_ID).setTracked(true)
	);

	public static final Identifier MAGIC_RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("magic_resistance_shred");
	public static final RegistryEntry<EntityAttribute> MAGIC_RESISTANCE_SHRED = registerAttribute(
			MAGIC_RESISTANCE_SHRED_ID,
			DynamicEntityAttribute.create(MAGIC_RESISTANCE_SHRED_ID).setTracked(true)
	);

	public static final Identifier MELEE_RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("melee_resistance_shred");
	public static final RegistryEntry<EntityAttribute> MELEE_RESISTANCE_SHRED = registerAttribute(
			MELEE_RESISTANCE_SHRED_ID,
			DynamicEntityAttribute.create(MELEE_RESISTANCE_SHRED_ID).setTracked(true)
	);

	public static final Identifier RANGED_RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("ranged_resistance_shred");
	public static final RegistryEntry<EntityAttribute> RANGED_RESISTANCE_SHRED = registerAttribute(
			RANGED_RESISTANCE_SHRED_ID,
			DynamicEntityAttribute.create(RANGED_RESISTANCE_SHRED_ID).setTracked(true)
	);

	public static final Identifier NATURAL_REGENERATION_ID
			= AttributesMod.createIdentifier("natural_regeneration");
	public static final RegistryEntry<EntityAttribute> NATURAL_REGENERATION = registerAttribute(
			NATURAL_REGENERATION_ID,
			DynamicEntityAttribute.create(NATURAL_REGENERATION_ID).setTracked(true)
	);

	public static final Identifier STEALTH_ID
			= AttributesMod.createIdentifier("stealth");
	public static final RegistryEntry<EntityAttribute> STEALTH = registerAttribute(
			STEALTH_ID,
			DynamicEntityAttribute.create(STEALTH_ID).setTracked(true)
	);

	public static final Identifier LIFE_STEAL_ID
			= AttributesMod.createIdentifier("life_steal");
	public static final RegistryEntry<EntityAttribute> LIFE_STEAL = registerAttribute(
			LIFE_STEAL_ID,
			DynamicEntityAttribute.create(LIFE_STEAL_ID).setTracked(true)
	);

	public static final Identifier FALL_REDUCTION_ID
			= AttributesMod.createIdentifier("fall_reduction");
	public static final RegistryEntry<EntityAttribute> FALL_REDUCTION = registerAttribute(
			FALL_REDUCTION_ID,
			DynamicEntityAttribute.create(FALL_REDUCTION_ID).setTracked(true)
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

	public static RegistryEntry<EntityAttribute> registerAttribute(Identifier id, EntityAttribute attribute) {
		return Platform.INSTANCE.registerReference(Registries.ATTRIBUTE, id, attribute);
	}

	// This method is left for backward compatibility in case someone uses it.
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
