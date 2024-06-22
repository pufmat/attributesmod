package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.Sign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

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

		if (source.getAttacker() instanceof PlayerEntity player) {
			if (source.isIn(DamageTypeTags.IS_PROJECTILE)) {
				damage = (float) AttributesMod.applyAttributeModifiers(
						damage,
						Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.RANGED_DAMAGE))
				);
			} else {
				damage = (float) AttributesMod.applyAttributeModifiers(
						damage,
						Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.MELEE_DAMAGE))
				);
			}
		}

		return damage;
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

		if (((LivingEntity) (Object) this) instanceof PlayerEntity player) {
			amount = (float) AttributesMod.applyAttributeModifiers(
					amount,
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.HEALING))
			);
		}

		return amount;
	}

	@ModifyReturnValue(
			method = "getJumpVelocity",
			at = @At("RETURN")
	)
	private float injectAtGetJumpVelocity(float jump) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player) {
			return  (float) AttributesMod.applyAttributeModifiers(
					jump,
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.JUMP))
			);
		}
		return jump;
	}

	@ModifyVariable(
			method = "computeFallDamage",
			at = @At("STORE"),
			ordinal = 2
	)
	private float modifyVariableAtComputeFallDamage(float reduction) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player) {
			reduction += ((((float) AttributesMod.applyAttributeModifiers(
					1.0f,
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.JUMP))
			)) - 1.0f) * 10.0f);
		}
		return reduction;
	}

	@ModifyReturnValue(
			method = "modifyAppliedDamage",
			at = @At("TAIL")
	)
	private float injectAtModifyAppliedDamage(float damage) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player && damage < Float.MAX_VALUE / 3.0f) {
			return Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					damage,
					Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.RESISTANCE))
			));
		}
		return damage;
	}
}
