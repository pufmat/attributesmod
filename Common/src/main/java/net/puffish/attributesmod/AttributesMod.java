package net.puffish.attributesmod;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.puffish.attributesmod.api.DynamicEntityAttribute;
import net.puffish.attributesmod.util.DynamicModificationImpl;
import net.puffish.attributesmod.util.Registrar;
import net.puffish.attributesmod.util.Signed;

public class AttributesMod {
	public static final String MOD_ID = "puffish_attributes";

	public static final Identifier STAMINA_ID
			= AttributesMod.createIdentifier("stamina");
	public static final EntityAttribute STAMINA = createClampedAttribute(
			STAMINA_ID,
			4.0,
			0.0,
			1024.0
	).setTracked(true);

	public static final Identifier MAGIC_DAMAGE_ID
			= AttributesMod.createIdentifier("magic_damage");
	public static final EntityAttribute MAGIC_DAMAGE = DynamicEntityAttribute.create(
			MAGIC_DAMAGE_ID
	).setTracked(true);

	public static final Identifier MELEE_DAMAGE_ID
			= AttributesMod.createIdentifier("melee_damage");
	public static final EntityAttribute MELEE_DAMAGE = DynamicEntityAttribute.create(
			MELEE_DAMAGE_ID
	).setTracked(true);

	public static final Identifier RANGED_DAMAGE_ID
			= AttributesMod.createIdentifier("ranged_damage");
	public static final EntityAttribute RANGED_DAMAGE = DynamicEntityAttribute.create(
			RANGED_DAMAGE_ID
	).setTracked(true);

	public static final Identifier TAMED_DAMAGE_ID
			= AttributesMod.createIdentifier("tamed_damage");
	public static final EntityAttribute TAMED_DAMAGE = DynamicEntityAttribute.create(
			TAMED_DAMAGE_ID
	).setTracked(true);

	public static final Identifier FORTUNE_ID
			= AttributesMod.createIdentifier("fortune");
	public static final EntityAttribute FORTUNE = DynamicEntityAttribute.create(
			FORTUNE_ID
	).setTracked(true);

	public static final Identifier HEALING_ID
			= AttributesMod.createIdentifier("healing");
	public static final EntityAttribute HEALING = DynamicEntityAttribute.create(
			HEALING_ID
	).setTracked(true);

	public static final Identifier JUMP_ID
			= AttributesMod.createIdentifier("jump");
	public static final EntityAttribute JUMP = DynamicEntityAttribute.create(
			JUMP_ID
	).setTracked(true);

	public static final Identifier RESISTANCE_ID
			= AttributesMod.createIdentifier("resistance");
	public static final EntityAttribute RESISTANCE = DynamicEntityAttribute.create(
			RESISTANCE_ID
	).setTracked(true);

	public static final Identifier MAGIC_RESISTANCE_ID
			= AttributesMod.createIdentifier("magic_resistance");
	public static final EntityAttribute MAGIC_RESISTANCE = DynamicEntityAttribute.create(
			MAGIC_RESISTANCE_ID
	).setTracked(true);

	public static final Identifier MELEE_RESISTANCE_ID
			= AttributesMod.createIdentifier("melee_resistance");
	public static final EntityAttribute MELEE_RESISTANCE = DynamicEntityAttribute.create(
			MELEE_RESISTANCE_ID
	).setTracked(true);

	public static final Identifier RANGED_RESISTANCE_ID
			= AttributesMod.createIdentifier("ranged_resistance");
	public static final EntityAttribute RANGED_RESISTANCE = DynamicEntityAttribute.create(
			RANGED_RESISTANCE_ID
	).setTracked(true);

	public static final Identifier TAMED_RESISTANCE_ID
			= AttributesMod.createIdentifier("tamed_resistance");
	public static final EntityAttribute TAMED_RESISTANCE = DynamicEntityAttribute.create(
			TAMED_RESISTANCE_ID
	).setTracked(true);

	public static final Identifier MINING_SPEED_ID
			= AttributesMod.createIdentifier("mining_speed");
	public static final EntityAttribute MINING_SPEED = DynamicEntityAttribute.create(
			MINING_SPEED_ID
	).setTracked(true);

	public static final Identifier PICKAXE_SPEED_ID
			= AttributesMod.createIdentifier("pickaxe_speed");
	public static final EntityAttribute PICKAXE_SPEED = DynamicEntityAttribute.create(
			PICKAXE_SPEED_ID
	).setTracked(true);

	public static final Identifier AXE_SPEED_ID
			= AttributesMod.createIdentifier("axe_speed");
	public static final EntityAttribute AXE_SPEED = DynamicEntityAttribute.create(
			AXE_SPEED_ID
	).setTracked(true);

	public static final Identifier SHOVEL_SPEED_ID
			= AttributesMod.createIdentifier("shovel_speed");
	public static final EntityAttribute SHOVEL_SPEED = DynamicEntityAttribute.create(
			SHOVEL_SPEED_ID
	).setTracked(true);

	public static final Identifier SPRINTING_SPEED_ID
			= AttributesMod.createIdentifier("sprinting_speed");
	public static final EntityAttribute SPRINTING_SPEED = DynamicEntityAttribute.create(
			SPRINTING_SPEED_ID
	).setTracked(true);

	public static final Identifier KNOCKBACK_ID
			= AttributesMod.createIdentifier("knockback");
	public static final EntityAttribute KNOCKBACK = DynamicEntityAttribute.create(
			KNOCKBACK_ID
	).setTracked(true);

	public static final Identifier REPAIR_COST_ID
			= AttributesMod.createIdentifier("repair_cost");
	public static final EntityAttribute REPAIR_COST = DynamicEntityAttribute.create(
			REPAIR_COST_ID
	).setTracked(true);

	public static final Identifier ARMOR_SHRED_ID
			= AttributesMod.createIdentifier("armor_shred");
	public static final EntityAttribute ARMOR_SHRED = DynamicEntityAttribute.create(
			ARMOR_SHRED_ID
	).setTracked(true);

	public static final Identifier TOUGHNESS_SHRED_ID
			= AttributesMod.createIdentifier("toughness_shred");
	public static final EntityAttribute TOUGHNESS_SHRED = DynamicEntityAttribute.create(
			TOUGHNESS_SHRED_ID
	).setTracked(true);

	public static final Identifier PROTECTION_SHRED_ID
			= AttributesMod.createIdentifier("protection_shred");
	public static final EntityAttribute PROTECTION_SHRED = DynamicEntityAttribute.create(
			PROTECTION_SHRED_ID
	).setTracked(true);

	public static final Identifier RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("resistance_shred");
	public static final EntityAttribute RESISTANCE_SHRED = DynamicEntityAttribute.create(
			RESISTANCE_SHRED_ID
	).setTracked(true);

	public static final Identifier MAGIC_RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("magic_resistance_shred");
	public static final EntityAttribute MAGIC_RESISTANCE_SHRED = DynamicEntityAttribute.create(
			MAGIC_RESISTANCE_SHRED_ID
	).setTracked(true);

	public static final Identifier MELEE_RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("melee_resistance_shred");
	public static final EntityAttribute MELEE_RESISTANCE_SHRED = DynamicEntityAttribute.create(
			MELEE_RESISTANCE_SHRED_ID
	).setTracked(true);

	public static final Identifier RANGED_RESISTANCE_SHRED_ID
			= AttributesMod.createIdentifier("ranged_resistance_shred");
	public static final EntityAttribute RANGED_RESISTANCE_SHRED = DynamicEntityAttribute.create(
			RANGED_RESISTANCE_SHRED_ID
	).setTracked(true);

	public static final Identifier NATURAL_REGENERATION_ID
			= AttributesMod.createIdentifier("natural_regeneration");
	public static final EntityAttribute NATURAL_REGENERATION = DynamicEntityAttribute.create(
			NATURAL_REGENERATION_ID
	).setTracked(true);

	public static final Identifier STEALTH_ID
			= AttributesMod.createIdentifier("stealth");
	public static final EntityAttribute STEALTH = DynamicEntityAttribute.create(
			STEALTH_ID
	).setTracked(true);

	public static final Identifier LIFE_STEAL_ID
			= AttributesMod.createIdentifier("life_steal");
	public static final EntityAttribute LIFE_STEAL = DynamicEntityAttribute.create(
			LIFE_STEAL_ID
	).setTracked(true);

	public static final Identifier FALL_REDUCTION_ID
			= AttributesMod.createIdentifier("fall_reduction");
	public static final EntityAttribute FALL_REDUCTION = DynamicEntityAttribute.create(
			FALL_REDUCTION_ID
	).setTracked(true);

	public static final Identifier BOW_PROJECTILE_SPEED_ID
			= AttributesMod.createIdentifier("bow_projectile_speed");
	public static final EntityAttribute BOW_PROJECTILE_SPEED = DynamicEntityAttribute.create(
			BOW_PROJECTILE_SPEED_ID
	).setTracked(true);

	public static final Identifier CROSSBOW_PROJECTILE_SPEED_ID
			= AttributesMod.createIdentifier("crossbow_projectile_speed");
	public static final EntityAttribute CROSSBOW_PROJECTILE_SPEED = DynamicEntityAttribute.create(
			CROSSBOW_PROJECTILE_SPEED_ID
	).setTracked(true);

	public static final Identifier EXPERIENCE_ID
			= AttributesMod.createIdentifier("experience");
	public static final EntityAttribute EXPERIENCE = DynamicEntityAttribute.create(
			EXPERIENCE_ID
	).setTracked(true);


	public static void setup(Registrar registrar) {
		registerAttribute(registrar, STAMINA_ID, STAMINA);
		registerAttribute(registrar, MAGIC_DAMAGE_ID, MAGIC_DAMAGE);
		registerAttribute(registrar, MELEE_DAMAGE_ID, MELEE_DAMAGE);
		registerAttribute(registrar, RANGED_DAMAGE_ID, RANGED_DAMAGE);
		registerAttribute(registrar, TAMED_DAMAGE_ID, TAMED_DAMAGE);
		registerAttribute(registrar, FORTUNE_ID, FORTUNE);
		registerAttribute(registrar, HEALING_ID, HEALING);
		registerAttribute(registrar, JUMP_ID, JUMP);
		registerAttribute(registrar, RESISTANCE_ID, RESISTANCE);
		registerAttribute(registrar, MAGIC_RESISTANCE_ID, MAGIC_RESISTANCE);
		registerAttribute(registrar, MELEE_RESISTANCE_ID, MELEE_RESISTANCE);
		registerAttribute(registrar, RANGED_RESISTANCE_ID, RANGED_RESISTANCE);
		registerAttribute(registrar, TAMED_RESISTANCE_ID, TAMED_RESISTANCE);
		registerAttribute(registrar, MINING_SPEED_ID, MINING_SPEED);
		registerAttribute(registrar, PICKAXE_SPEED_ID, PICKAXE_SPEED);
		registerAttribute(registrar, AXE_SPEED_ID, AXE_SPEED);
		registerAttribute(registrar, SHOVEL_SPEED_ID, SHOVEL_SPEED);
		registerAttribute(registrar, SPRINTING_SPEED_ID, SPRINTING_SPEED);
		registerAttribute(registrar, KNOCKBACK_ID, KNOCKBACK);
		registerAttribute(registrar, REPAIR_COST_ID, REPAIR_COST);
		registerAttribute(registrar, ARMOR_SHRED_ID, ARMOR_SHRED);
		registerAttribute(registrar, TOUGHNESS_SHRED_ID, TOUGHNESS_SHRED);
		registerAttribute(registrar, PROTECTION_SHRED_ID, PROTECTION_SHRED);
		registerAttribute(registrar, RESISTANCE_SHRED_ID, RESISTANCE_SHRED);
		registerAttribute(registrar, MAGIC_RESISTANCE_SHRED_ID, MAGIC_RESISTANCE_SHRED);
		registerAttribute(registrar, MELEE_RESISTANCE_SHRED_ID, MELEE_RESISTANCE_SHRED);
		registerAttribute(registrar, RANGED_RESISTANCE_SHRED_ID, RANGED_RESISTANCE_SHRED);
		registerAttribute(registrar, NATURAL_REGENERATION_ID, NATURAL_REGENERATION);
		registerAttribute(registrar, STEALTH_ID, STEALTH);
		registerAttribute(registrar, LIFE_STEAL_ID, LIFE_STEAL);
		registerAttribute(registrar, FALL_REDUCTION_ID, FALL_REDUCTION);
		registerAttribute(registrar, BOW_PROJECTILE_SPEED_ID, BOW_PROJECTILE_SPEED);
		registerAttribute(registrar, CROSSBOW_PROJECTILE_SPEED_ID, CROSSBOW_PROJECTILE_SPEED);
		registerAttribute(registrar, EXPERIENCE_ID, EXPERIENCE);
	}

	public static Identifier createIdentifier(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static EntityAttribute createClampedAttribute(Identifier id, double fallback, double min, double max) {
		return new ClampedEntityAttribute(
				id.toTranslationKey("attribute"),
				fallback,
				min,
				max
		);
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

	private static void registerAttribute(Registrar registrar, Identifier id, EntityAttribute attribute) {
		registrar.register(Registries.ATTRIBUTE, id, attribute);
		registrar.registerAlias(Registries.ATTRIBUTE, new Identifier(id.getNamespace(), "player." + id.getPath()), id);
	}

}
