package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.util.DamageKind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LivingEntity.class, priority = 1100)
public abstract class LivingEntityMixin {

	@ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
	private static DefaultAttributeContainer.Builder modifyReturnValueAtCreateLivingAttributes(DefaultAttributeContainer.Builder builder) {
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
				.add(AttributesMod.STEALTH)
				.add(AttributesMod.LIFE_STEAL);
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

			damage = dm.applyTo(damage);
		}

		return damage;
	}

	@WrapOperation(
			method = "applyArmorToDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(Lnet/minecraft/entity/LivingEntity;FLnet/minecraft/entity/damage/DamageSource;FF)F")
	)
	private float wrapOperationAtApplyArmorToDamage(LivingEntity entity, float damage, DamageSource source, float armor, float toughness, Operation<Float> operation) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			armor = Math.max(0.0f, DynamicModification.create()
					.withNegative(AttributesMod.ARMOR_SHRED, attacker)
					.applyTo(armor));
			toughness = Math.max(0.0f, DynamicModification.create()
					.withNegative(AttributesMod.TOUGHNESS_SHRED, attacker)
					.applyTo(toughness));
		}

		return operation.call(entity, damage, source, armor, toughness);
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
			at = @At("STORE"),
			ordinal = 2
	)
	private float modifyVariableAtComputeFallDamage(float reduction) {
		return reduction + (DynamicModification.create()
				.withPositive(AttributesMod.JUMP, ((LivingEntity) (Object) this))
				.relativeTo(1.0f) * 10.0f);
	}

	@ModifyReturnValue(
			method = "modifyAppliedDamage",
			at = @At("TAIL")
	)
	private float injectAtModifyAppliedDamage(float damage, @Local(argsOnly = true) DamageSource source) {
		if (damage < Float.MAX_VALUE / 3.0f) {
			var entity = ((LivingEntity) (Object) this);

			var dm = DynamicModification.create();

			dm.withNegative(AttributesMod.RESISTANCE, entity);
			var kind = DamageKind.of(source);
			if (kind.isMagic()) {
				dm.withNegative(AttributesMod.MAGIC_RESISTANCE, entity);
			} else {
				if (kind.isProjectile()) {
					dm.withNegative(AttributesMod.RANGED_RESISTANCE, entity);
				}
				if (kind.isMelee()) {
					dm.withNegative(AttributesMod.MELEE_RESISTANCE, entity);
				}
			}

			return Math.max(0.0f, dm.applyTo(damage));
		}
		return damage;
	}
}
