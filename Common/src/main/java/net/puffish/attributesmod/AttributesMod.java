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
			= AttributesMod.createAttributeIdentifier("player", "stamina");
	public static final RegistryEntry<EntityAttribute> STAMINA = registerAttribute(
			STAMINA_ID,
			createClampedAttribute(
					STAMINA_ID,
					4.0,
					0.0,
					1024.0
			).setTracked(true)
	);

	public static final Identifier MELEE_DAMAGE_ID
			= AttributesMod.createAttributeIdentifier("player", "melee_damage");
	public static final RegistryEntry<EntityAttribute> MELEE_DAMAGE = registerAttribute(
			MELEE_DAMAGE_ID,
			createDynamicAttribute(MELEE_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier RANGED_DAMAGE_ID
			= AttributesMod.createAttributeIdentifier("player", "ranged_damage");
	public static final RegistryEntry<EntityAttribute> RANGED_DAMAGE = registerAttribute(
			RANGED_DAMAGE_ID,
			createDynamicAttribute(RANGED_DAMAGE_ID).setTracked(true)
	);

	public static final Identifier FORTUNE_ID
			= AttributesMod.createAttributeIdentifier("player", "fortune");
	public static final RegistryEntry<EntityAttribute> FORTUNE = registerAttribute(
			FORTUNE_ID,
			createDynamicAttribute(FORTUNE_ID).setTracked(true)
	);

	public static final Identifier HEALING_ID
			= AttributesMod.createAttributeIdentifier("player", "healing");
	public static final RegistryEntry<EntityAttribute> HEALING = registerAttribute(
			HEALING_ID,
			createDynamicAttribute(HEALING_ID).setTracked(true)
	);

	public static final Identifier JUMP_ID
			= AttributesMod.createAttributeIdentifier("player", "jump");
	public static final RegistryEntry<EntityAttribute> JUMP = registerAttribute(
			JUMP_ID,
			createDynamicAttribute(JUMP_ID).setTracked(true)
	);

	public static final Identifier RESISTANCE_ID
			= AttributesMod.createAttributeIdentifier("player", "resistance");
	public static final RegistryEntry<EntityAttribute> RESISTANCE = registerAttribute(
			RESISTANCE_ID,
			createDynamicAttribute(RESISTANCE_ID).setTracked(true)
	);

	public static final Identifier MINING_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "mining_speed");
	public static final RegistryEntry<EntityAttribute> MINING_SPEED = registerAttribute(
			MINING_SPEED_ID,
			createDynamicAttribute(MINING_SPEED_ID).setTracked(true)
	);

	public static final Identifier PICKAXE_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "pickaxe_speed");
	public static final RegistryEntry<EntityAttribute> PICKAXE_SPEED = registerAttribute(
			PICKAXE_SPEED_ID,
			createDynamicAttribute(PICKAXE_SPEED_ID).setTracked(true)
	);

	public static final Identifier AXE_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "axe_speed");
	public static final RegistryEntry<EntityAttribute> AXE_SPEED = registerAttribute(
			AXE_SPEED_ID,
			createDynamicAttribute(AXE_SPEED_ID).setTracked(true)
	);

	public static final Identifier SHOVEL_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "shovel_speed");
	public static final RegistryEntry<EntityAttribute> SHOVEL_SPEED = registerAttribute(
			SHOVEL_SPEED_ID,
			createDynamicAttribute(SHOVEL_SPEED_ID).setTracked(true)
	);

	public static final Identifier SPRINTING_SPEED_ID
			= AttributesMod.createAttributeIdentifier("player", "sprinting_speed");
	public static final RegistryEntry<EntityAttribute> SPRINTING_SPEED = registerAttribute(
			SPRINTING_SPEED_ID,
			createDynamicAttribute(SPRINTING_SPEED_ID).setTracked(true)
	);

	public static final Identifier KNOCKBACK_ID
			= AttributesMod.createAttributeIdentifier("player", "knockback");
	public static final RegistryEntry<EntityAttribute> KNOCKBACK = registerAttribute(
			KNOCKBACK_ID,
			createDynamicAttribute(KNOCKBACK_ID).setTracked(true)
	);

	public static final Identifier REPAIR_COST_ID
			= AttributesMod.createAttributeIdentifier("player", "repair_cost");
	public static final RegistryEntry<EntityAttribute> REPAIR_COST = registerAttribute(
			REPAIR_COST_ID,
			createDynamicAttribute(REPAIR_COST_ID).setTracked(true)
	);

	public static void setup() {

	}

	public static Identifier createIdentifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static Identifier createAttributeIdentifier(String type, String name) {
		return createIdentifier(type + "." + name);
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
		return Platform.INSTANCE.registerReference(Registries.ATTRIBUTE, id, attribute);
	}

	@SafeVarargs
	public static double applyAttributeModifiers(
			double initial,
			Signed<EntityAttributeInstance>... attributes
	) {
		for (var signedAttribute : attributes) {
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
			result = signedAttribute.value().getAttribute().value().clamp(result);
		}
		return result;
	}

}
