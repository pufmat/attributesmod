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
			= AttributesMod.createAttributeIdentifier("player", "stamina");
	public static final EntityAttribute STAMINA = createClampedAttribute(
			STAMINA_ID,
			4.0,
			0.0,
			1024.0
	).setTracked(true);

	public static final Identifier MELEE_DAMAGE_ID
			= AttributesMod.createAttributeIdentifier("player", "melee_damage");
	public static final EntityAttribute MELEE_DAMAGE = createDynamicAttribute(
			MELEE_DAMAGE_ID,
			0.0,
			Double.MAX_VALUE
	);

	public static final Identifier RANGED_DAMAGE_ID
			= AttributesMod.createAttributeIdentifier("player", "ranged_damage");
	public static final EntityAttribute RANGED_DAMAGE = createDynamicAttribute(
			RANGED_DAMAGE_ID,
			0.0,
			Double.MAX_VALUE
	);

	public static final Identifier FORTUNE_ID
			= AttributesMod.createAttributeIdentifier("player", "fortune");
	public static final EntityAttribute FORTUNE = createDynamicAttribute(
			FORTUNE_ID,
			0.0,
			Double.MAX_VALUE
	);

	public static final Identifier HEALING_ID
			= AttributesMod.createAttributeIdentifier("player", "healing");
	public static final EntityAttribute HEALING = createDynamicAttribute(
			HEALING_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier JUMP_ID
			= AttributesMod.createAttributeIdentifier("player", "jump");
	public static final EntityAttribute JUMP = createDynamicAttribute(
			JUMP_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier RESISTANCE_ID
			= AttributesMod.createAttributeIdentifier("player", "resistance");
	public static final EntityAttribute RESISTANCE = createDynamicAttribute(
			RESISTANCE_ID,
			0.0,
			Double.MAX_VALUE
	);

	public static final Identifier MINING_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "mining_speed");
	public static final EntityAttribute MINING_SPEED = createDynamicAttribute(
			MINING_SPEED_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier PICKAXE_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "pickaxe_speed");
	public static final EntityAttribute PICKAXE_SPEED = createDynamicAttribute(
			PICKAXE_SPEED_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier AXE_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "axe_speed");
	public static final EntityAttribute AXE_SPEED = createDynamicAttribute(
			AXE_SPEED_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier SHOVEL_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "shovel_speed");
	public static final EntityAttribute SHOVEL_SPEED = createDynamicAttribute(
			SHOVEL_SPEED_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier SPRINTING_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "sprinting_speed");
	public static final EntityAttribute SPRINTING_SPEED = createDynamicAttribute(
			SPRINTING_SPEED_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier KNOCKBACK_ID
			= AttributesMod.createAttributeIdentifier("player", "knockback");
	public static final EntityAttribute KNOCKBACK = createDynamicAttribute(
			KNOCKBACK_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static final Identifier REPAIR_COST_ID
			= AttributesMod.createAttributeIdentifier("player", "repair_cost");
	public static final EntityAttribute REPAIR_COST = createDynamicAttribute(
			REPAIR_COST_ID,
			0.0,
			Double.MAX_VALUE
	).setTracked(true);

	public static void setup(Registrar registrar) {
		registrar.register(Registry.ATTRIBUTE, STAMINA_ID, STAMINA);
		registrar.register(Registry.ATTRIBUTE, MELEE_DAMAGE_ID, MELEE_DAMAGE);
		registrar.register(Registry.ATTRIBUTE, RANGED_DAMAGE_ID, RANGED_DAMAGE);
		registrar.register(Registry.ATTRIBUTE, FORTUNE_ID, FORTUNE);
		registrar.register(Registry.ATTRIBUTE, HEALING_ID, HEALING);
		registrar.register(Registry.ATTRIBUTE, JUMP_ID, JUMP);
		registrar.register(Registry.ATTRIBUTE, RESISTANCE_ID, RESISTANCE);
		registrar.register(Registry.ATTRIBUTE, MINING_SPEED_ID, MINING_SPEED);
		registrar.register(Registry.ATTRIBUTE, PICKAXE_SPEED_ID, PICKAXE_SPEED);
		registrar.register(Registry.ATTRIBUTE, AXE_SPEED_ID, AXE_SPEED);
		registrar.register(Registry.ATTRIBUTE, SHOVEL_SPEED_ID, SHOVEL_SPEED);
		registrar.register(Registry.ATTRIBUTE, SPRINTING_SPEED_ID, SPRINTING_SPEED);
		registrar.register(Registry.ATTRIBUTE, KNOCKBACK_ID, KNOCKBACK);
		registrar.register(Registry.ATTRIBUTE, REPAIR_COST_ID, REPAIR_COST);
	}

	public static Identifier createIdentifier(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static Identifier createAttributeIdentifier(String type, String name) {
		return createIdentifier(type + "." + name);
	}

	public static EntityAttribute createClampedAttribute(Identifier id, double fallback, double min, double max) {
		return new ClampedEntityAttribute(
				"attribute." + id.getNamespace() + "." + id.getPath(),
				fallback,
				min,
				max
		);
	}

	public static EntityAttribute createDynamicAttribute(Identifier id, double min, double max) {
		return new DynamicEntityAttribute(
				"attribute." + id.getNamespace() + "." + id.getPath(),
				min,
				max
		);
	}

	@SafeVarargs
	public static double applyAttributeModifiers(
			double initial,
			Signed<EntityAttributeInstance>... attributes
	) {
		for (var signedAttribute : attributes) {
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
			result = signedAttribute.value().getAttribute().clamp(result);
		}
		return result;
	}

}
