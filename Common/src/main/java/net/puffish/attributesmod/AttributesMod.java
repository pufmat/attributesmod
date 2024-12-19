package net.puffish.attributesmod;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.puffish.attributesmod.attribute.DynamicEntityAttribute;
import net.puffish.attributesmod.mixin.EntityAttributeInstanceInvoker;
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
	public static final EntityAttribute MAGIC_DAMAGE = createDynamicAttribute(
			MAGIC_DAMAGE_ID
	).setTracked(true);

	public static final Identifier MELEE_DAMAGE_ID
			= AttributesMod.createIdentifier("melee_damage");
	public static final EntityAttribute MELEE_DAMAGE = createDynamicAttribute(
			MELEE_DAMAGE_ID
	).setTracked(true);

	public static final Identifier RANGED_DAMAGE_ID
			= AttributesMod.createIdentifier("ranged_damage");
	public static final EntityAttribute RANGED_DAMAGE = createDynamicAttribute(
			RANGED_DAMAGE_ID
	).setTracked(true);

	public static final Identifier FORTUNE_ID
			= AttributesMod.createIdentifier("fortune");
	public static final EntityAttribute FORTUNE = createDynamicAttribute(
			FORTUNE_ID
	).setTracked(true);

	public static final Identifier HEALING_ID
			= AttributesMod.createIdentifier("healing");
	public static final EntityAttribute HEALING = createDynamicAttribute(
			HEALING_ID
	).setTracked(true);

	public static final Identifier JUMP_ID
			= AttributesMod.createIdentifier("jump");
	public static final EntityAttribute JUMP = createDynamicAttribute(
			JUMP_ID
	).setTracked(true);

	public static final Identifier RESISTANCE_ID
			= AttributesMod.createIdentifier("resistance");
	public static final EntityAttribute RESISTANCE = createDynamicAttribute(
			RESISTANCE_ID
	).setTracked(true);

	public static final Identifier MAGIC_RESISTANCE_ID
			= AttributesMod.createIdentifier("magic_resistance");
	public static final EntityAttribute MAGIC_RESISTANCE = createDynamicAttribute(
			MAGIC_RESISTANCE_ID
	).setTracked(true);

	public static final Identifier MELEE_RESISTANCE_ID
			= AttributesMod.createIdentifier("melee_resistance");
	public static final EntityAttribute MELEE_RESISTANCE = createDynamicAttribute(
			MELEE_RESISTANCE_ID
	).setTracked(true);

	public static final Identifier RANGED_RESISTANCE_ID
			= AttributesMod.createIdentifier("ranged_resistance");
	public static final EntityAttribute RANGED_RESISTANCE = createDynamicAttribute(
			RANGED_RESISTANCE_ID
	).setTracked(true);

	public static final Identifier MINING_SPEED_ID
			= AttributesMod.createIdentifier("mining_speed");
	public static final EntityAttribute MINING_SPEED = createDynamicAttribute(
			MINING_SPEED_ID
	).setTracked(true);

	public static final Identifier PICKAXE_SPEED_ID
			= AttributesMod.createIdentifier("pickaxe_speed");
	public static final EntityAttribute PICKAXE_SPEED = createDynamicAttribute(
			PICKAXE_SPEED_ID
	).setTracked(true);

	public static final Identifier AXE_SPEED_ID
			= AttributesMod.createIdentifier("axe_speed");
	public static final EntityAttribute AXE_SPEED = createDynamicAttribute(
			AXE_SPEED_ID
	).setTracked(true);

	public static final Identifier SHOVEL_SPEED_ID
			= AttributesMod.createIdentifier("shovel_speed");
	public static final EntityAttribute SHOVEL_SPEED = createDynamicAttribute(
			SHOVEL_SPEED_ID
	).setTracked(true);

	public static final Identifier SPRINTING_SPEED_ID
			= AttributesMod.createIdentifier("sprinting_speed");
	public static final EntityAttribute SPRINTING_SPEED = createDynamicAttribute(
			SPRINTING_SPEED_ID
	).setTracked(true);

	public static final Identifier KNOCKBACK_ID
			= AttributesMod.createIdentifier("knockback");
	public static final EntityAttribute KNOCKBACK = createDynamicAttribute(
			KNOCKBACK_ID
	).setTracked(true);

	public static final Identifier REPAIR_COST_ID
			= AttributesMod.createIdentifier("repair_cost");
	public static final EntityAttribute REPAIR_COST = createDynamicAttribute(
			REPAIR_COST_ID
	).setTracked(true);

	public static final Identifier ARMOR_SHRED_ID
			= AttributesMod.createIdentifier("armor_shred");
	public static final EntityAttribute ARMOR_SHRED = createDynamicAttribute(
			ARMOR_SHRED_ID
	).setTracked(true);

	public static final Identifier TOUGHNESS_SHRED_ID
			= AttributesMod.createIdentifier("toughness_shred");
	public static final EntityAttribute TOUGHNESS_SHRED = createDynamicAttribute(
			TOUGHNESS_SHRED_ID
	).setTracked(true);

	public static final Identifier PROTECTION_SHRED_ID
			= AttributesMod.createIdentifier("protection_shred");
	public static final EntityAttribute PROTECTION_SHRED = createDynamicAttribute(
			PROTECTION_SHRED_ID
	).setTracked(true);

	public static final Identifier NATURAL_REGENERATION_ID
			= AttributesMod.createIdentifier("natural_regeneration");
	public static final EntityAttribute NATURAL_REGENERATION = createDynamicAttribute(
			NATURAL_REGENERATION_ID
	).setTracked(true);

	public static final Identifier STEALTH_ID
			= AttributesMod.createIdentifier("stealth");
	public static final EntityAttribute STEALTH = createDynamicAttribute(
			STEALTH_ID
	).setTracked(true);


	public static void setup(Registrar registrar) {
		registerAttribute(registrar, STAMINA_ID, STAMINA);
		registerAttribute(registrar, MAGIC_DAMAGE_ID, MAGIC_DAMAGE);
		registerAttribute(registrar, MELEE_DAMAGE_ID, MELEE_DAMAGE);
		registerAttribute(registrar, RANGED_DAMAGE_ID, RANGED_DAMAGE);
		registerAttribute(registrar, FORTUNE_ID, FORTUNE);
		registerAttribute(registrar, HEALING_ID, HEALING);
		registerAttribute(registrar, JUMP_ID, JUMP);
		registerAttribute(registrar, RESISTANCE_ID, RESISTANCE);
		registerAttribute(registrar, MAGIC_RESISTANCE_ID, MAGIC_RESISTANCE);
		registerAttribute(registrar, MELEE_RESISTANCE_ID, MELEE_RESISTANCE);
		registerAttribute(registrar, RANGED_RESISTANCE_ID, RANGED_RESISTANCE);
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
		registerAttribute(registrar, NATURAL_REGENERATION_ID, NATURAL_REGENERATION);
		registerAttribute(registrar, STEALTH_ID, STEALTH);
	}

	public static Identifier createIdentifier(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static EntityAttribute createClampedAttribute(Identifier id, double fallback, double min, double max) {
		return new ClampedEntityAttribute(
				"attribute." + id.getNamespace() + "." + id.getPath(),
				fallback,
				min,
				max
		);
	}

	public static EntityAttribute createDynamicAttribute(Identifier id) {
		return new DynamicEntityAttribute(
				"attribute." + id.getNamespace() + "." + id.getPath()
		);
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
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.ADDITION)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> initial += modifier.getValue();
					case NEGATIVE -> initial -= modifier.getValue();
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
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.MULTIPLY_BASE)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result += initial * modifier.getValue();
					case NEGATIVE -> result -= initial * modifier.getValue();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((EntityAttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result *= 1.0 + modifier.getValue();
					case NEGATIVE -> result *= 1.0 - modifier.getValue();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			result = signedAttribute.value().getAttribute().clamp(result);
		}
		return result;
	}

	private static void registerAttribute(Registrar registrar, Identifier id, EntityAttribute attribute) {
		registrar.register(Registry.ATTRIBUTE, id, attribute);
		registrar.registerAlias(Registry.ATTRIBUTE, new Identifier(id.getNamespace(), "player." + id.getPath()), id);
	}

}
