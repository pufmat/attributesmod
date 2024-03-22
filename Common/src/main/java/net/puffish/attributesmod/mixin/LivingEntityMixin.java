package net.puffish.attributesmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.Sign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
			if (source instanceof ProjectileDamageSource) {
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

	@Inject(
			method = "getJumpVelocity",
			at = @At("RETURN"),
			cancellable = true
	)
	private void injectAtGetJumpVelocity(CallbackInfoReturnable<Float> cir) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player) {
			cir.setReturnValue((float) AttributesMod.applyAttributeModifiers(
					cir.getReturnValueF(),
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.JUMP))
			));
		}
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

	@Inject(
			method = "applyEnchantmentsToDamage",
			at = @At("TAIL"),
			cancellable = true
	)
	private void injectAtApplyEnchantmentsToDamage(CallbackInfoReturnable<Float> cir) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player && cir.getReturnValueF() < Float.MAX_VALUE / 3.0f) {
			cir.setReturnValue(Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					cir.getReturnValueF(),
					Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.RESISTANCE))
			)));
		}
	}
}
