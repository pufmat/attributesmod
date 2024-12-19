package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.DamageKind;
import net.puffish.attributesmod.util.Sign;
import net.puffish.attributesmod.util.Signed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;

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
				.add(AttributesMod.STEALTH);
	}

	@SuppressWarnings("unchecked")
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
			var attributes = new ArrayList<Signed<EntityAttributeInstance>>();

			var kind = DamageKind.of(source);
			if (kind.isMagic()) {
				attributes.add(Sign.POSITIVE.wrap(attacker.getAttributeInstance(AttributesMod.MAGIC_DAMAGE)));
			} else {
				if (kind.isProjectile()) {
					attributes.add(Sign.POSITIVE.wrap(attacker.getAttributeInstance(AttributesMod.RANGED_DAMAGE)));
				}
				if (kind.isMelee()) {
					attributes.add(Sign.POSITIVE.wrap(attacker.getAttributeInstance(AttributesMod.MELEE_DAMAGE)));
				}
			}

			damage = (float) AttributesMod.applyAttributeModifiers(
					damage,
					attributes.toArray(Signed[]::new)
			);
		}

		return damage;
	}

	@WrapOperation(
			method = "applyArmorToDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(FFF)F")
	)
	private float wrapOperationAtApplyArmorToDamage(float damage, float armor, float toughness, Operation<Float> operation, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			armor = Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					armor,
					Sign.NEGATIVE.wrap(attacker.getAttributeInstance(AttributesMod.ARMOR_SHRED))
			));
			toughness = Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					toughness,
					Sign.NEGATIVE.wrap(attacker.getAttributeInstance(AttributesMod.TOUGHNESS_SHRED))
			));
		}

		return operation.call(damage, armor, toughness);
	}

	@WrapOperation(
			method = "applyEnchantmentsToDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getInflictedDamage(FF)F")
	)
	private float wrapOperationAtApplyEnchantmentsToDamage(float damageDealt, float protection, Operation<Float> original, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			protection = Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					protection,
					Sign.NEGATIVE.wrap(attacker.getAttributeInstance(AttributesMod.PROTECTION_SHRED))
			));
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

		return (float) AttributesMod.applyAttributeModifiers(
				amount,
				Sign.POSITIVE.wrap(((LivingEntity) (Object) this).getAttributeInstance(AttributesMod.HEALING))
		);
	}

	@ModifyReturnValue(
			method = "getJumpVelocity",
			at = @At("RETURN")
	)
	private float injectAtGetJumpVelocity(float jump) {
		return (float) AttributesMod.applyAttributeModifiers(
				jump,
				Sign.POSITIVE.wrap(((LivingEntity) (Object) this).getAttributeInstance(AttributesMod.JUMP))
		);
	}

	@ModifyVariable(
			method = "computeFallDamage",
			at = @At("STORE"),
			ordinal = 2
	)
	private float modifyVariableAtComputeFallDamage(float reduction) {
		return reduction + ((((float) AttributesMod.applyAttributeModifiers(
				1.0f,
				Sign.POSITIVE.wrap(((LivingEntity) (Object) this).getAttributeInstance(AttributesMod.JUMP))
		)) - 1.0f) * 10.0f);
	}

	@ModifyReturnValue(
			method = "applyEnchantmentsToDamage",
			at = @At("TAIL")
	)
	private float injectAtApplyEnchantmentsToDamage(float damage, @Local(argsOnly = true) DamageSource source) {
		if (damage < Float.MAX_VALUE / 3.0f) {
			var entity = ((LivingEntity) (Object) this);
			var attributes = new ArrayList<Signed<EntityAttributeInstance>>();

			attributes.add(Sign.NEGATIVE.wrap(entity.getAttributeInstance(AttributesMod.RESISTANCE)));
			var kind = DamageKind.of(source);
			if (kind.isMagic()) {
				attributes.add(Sign.NEGATIVE.wrap(entity.getAttributeInstance(AttributesMod.MAGIC_RESISTANCE)));
			} else {
				if (kind.isProjectile()) {
					attributes.add(Sign.NEGATIVE.wrap(entity.getAttributeInstance(AttributesMod.RANGED_RESISTANCE)));
				}
				if (kind.isMelee()) {
					attributes.add(Sign.NEGATIVE.wrap(entity.getAttributeInstance(AttributesMod.MELEE_RESISTANCE)));
				}
			}

			return Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					damage,
					attributes.toArray(Signed[]::new)
			));
		}
		return damage;
	}
}
