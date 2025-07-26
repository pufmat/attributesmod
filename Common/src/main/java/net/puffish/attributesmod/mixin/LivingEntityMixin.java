package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.util.DamageKind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LivingEntity.class, priority = 1100)
public abstract class LivingEntityMixin {

	@ModifyExpressionValue(
			method = "createLivingAttributes",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer;builder()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;")
	)
	private static DefaultAttributeContainer.Builder modifyExpressionValueAtBuilder(DefaultAttributeContainer.Builder builder) {
		return builder
				.add(AttributesMod.MAGIC_DAMAGE)
				.add(AttributesMod.MELEE_DAMAGE)
				.add(AttributesMod.RANGED_DAMAGE)
				.add(AttributesMod.HEALING)
				.add(AttributesMod.JUMP)
				.add(AttributesMod.RESISTANCE)
				.add(AttributesMod.MAGIC_RESISTANCE)
				.add(AttributesMod.MELEE_RESISTANCE)
				.add(AttributesMod.RANGED_RESISTANCE)
				.add(AttributesMod.ARMOR_SHRED)
				.add(AttributesMod.TOUGHNESS_SHRED)
				.add(AttributesMod.PROTECTION_SHRED)
				.add(AttributesMod.RESISTANCE_SHRED)
				.add(AttributesMod.MAGIC_RESISTANCE_SHRED)
				.add(AttributesMod.MELEE_RESISTANCE_SHRED)
				.add(AttributesMod.RANGED_RESISTANCE_SHRED)
				.add(AttributesMod.STEALTH)
				.add(AttributesMod.LIFE_STEAL)
				.add(AttributesMod.FALL_REDUCTION)
				.add(AttributesMod.BOW_PROJECTILE_SPEED)
				.add(AttributesMod.CROSSBOW_PROJECTILE_SPEED);
	}

	@ModifyVariable(
			method = "damage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyVariableAtDamage(float damage, DamageSource source) {
		if (damage < 0) {
			return damage;
		}

		if (source.getAttacker() instanceof LivingEntity attacker) {
			var dm = DynamicModification.create();

			var kind = DamageKind.of(source);
			if (kind.isMagic()) {
				dm.withPositive(AttributesMod.MAGIC_DAMAGE, attacker);
			} else {
				if (kind.isProjectile()) {
					dm.withPositive(AttributesMod.RANGED_DAMAGE, attacker);
				}
				if (kind.isMelee()) {
					dm.withPositive(AttributesMod.MELEE_DAMAGE, attacker);
				}
			}

			if (attacker instanceof Tameable tameable) {
				var owner = tameable.getOwner();
				if (owner != null) {
					dm.withPositive(AttributesMod.TAMED_DAMAGE, owner);
				}
			}

			damage = dm.applyTo(damage);
		}

		return damage;
	}

	@WrapOperation(
			method = "applyArmorToDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(FFF)F")
	)
	private float wrapOperationAtApplyArmorToDamage(float damage, float armor, float toughness, Operation<Float> operation, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			armor = Math.max(0.0f, DynamicModification.create()
					.withNegative(AttributesMod.ARMOR_SHRED, attacker)
					.applyTo(armor));
			toughness = Math.max(0.0f, DynamicModification.create()
					.withNegative(AttributesMod.TOUGHNESS_SHRED, attacker)
					.applyTo(toughness));
		}

		return operation.call(damage, armor, toughness);
	}

	@WrapOperation(
			method = "modifyAppliedDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getInflictedDamage(FF)F")
	)
	private float wrapOperationAtModifyAppliedDamage(float damageDealt, float protection, Operation<Float> original, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			protection = Math.max(0.0f, DynamicModification.create()
					.withNegative(AttributesMod.PROTECTION_SHRED, attacker)
					.applyTo(protection));
		}

		return original.call(damageDealt, protection);
	}

	@ModifyVariable(
			method = "heal",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyVariableAtHeal(float amount) {
		if (amount < 0) {
			return amount;
		}

		return DynamicModification.create()
				.withPositive(AttributesMod.HEALING, ((LivingEntity) (Object) this))
				.applyTo(amount);
	}

	@ModifyReturnValue(
			method = "getJumpVelocity",
			at = @At("RETURN")
	)
	private float injectAtGetJumpVelocity(float jump) {
		return DynamicModification.create()
				.withPositive(AttributesMod.JUMP, ((LivingEntity) (Object) this))
				.applyTo(jump);
	}

	@ModifyVariable(
			method = "computeFallDamage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyVariableAtComputeFallDamage(float fallDistance) {
		return DynamicModification.create()
				.withNegative(AttributesMod.FALL_REDUCTION, ((LivingEntity) (Object) this))
				.applyTo(fallDistance)
				- DynamicModification.create()
				.withPositive(AttributesMod.JUMP, ((LivingEntity) (Object) this))
				.relativeTo(1.0f) * 10.0f;
	}

	@ModifyReturnValue(
			method = "applyArmorToDamage",
			at = @At("TAIL")
	)
	private float injectAtModifyAppliedDamage(float damage, @Local(argsOnly = true) DamageSource source) {
		if (source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
			return damage;
		}
		if (damage > Float.MAX_VALUE / 3.0f) {
			return damage;
		}

		var entity = ((LivingEntity) (Object) this);
		var kind = DamageKind.of(source);

		var dmResistance = DynamicModification.create();
		dmResistance.withNegative(AttributesMod.RESISTANCE, entity);
		if (kind.isMagic()) {
			dmResistance.withNegative(AttributesMod.MAGIC_RESISTANCE, entity);
		} else {
			if (kind.isProjectile()) {
				dmResistance.withNegative(AttributesMod.RANGED_RESISTANCE, entity);
			}
			if (kind.isMelee()) {
				dmResistance.withNegative(AttributesMod.MELEE_RESISTANCE, entity);
			}
		}

		if (entity instanceof Tameable tameable) {
			var owner = tameable.getOwner();
			if (owner != null) {
				dmResistance.withNegative(AttributesMod.TAMED_RESISTANCE, owner);
			}
		}

		var resistance = damage - dmResistance.applyTo(damage);

		if (source.getAttacker() instanceof LivingEntity attacker) {
			var dmShred = DynamicModification.create();
			dmShred.withNegative(AttributesMod.RESISTANCE_SHRED, attacker);
			if (kind.isMagic()) {
				dmShred.withNegative(AttributesMod.MAGIC_RESISTANCE_SHRED, attacker);
			} else {
				if (kind.isProjectile()) {
					dmShred.withNegative(AttributesMod.RANGED_RESISTANCE_SHRED, attacker);
				}
				if (kind.isMelee()) {
					dmShred.withNegative(AttributesMod.MELEE_RESISTANCE_SHRED, attacker);
				}
			}

			resistance = Math.max(0.0f, dmShred.applyTo(resistance));
		}

		return Math.max(0.0f, damage - resistance);
	}
}
