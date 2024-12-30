package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = HungerManager.class, priority = 1100)
public abstract class HungerManagerMixin {

	@WrapOperation(
			method = "update",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/player/PlayerEntity;heal(F)V"
			)
	)
	private void wrapOperationAtHeal(PlayerEntity player, float amount, Operation<Void> operation) {
		operation.call(player, Math.max(0.0f, DynamicModification.create()
				.withPositive(AttributesMod.NATURAL_REGENERATION, player)
				.applyTo(amount)));
	}

	@ModifyConstant(
			method = "update",
			constant = @Constant(floatValue = 4.0f, ordinal = 0),
			require = 0
	)
	private float modifyConstant0AtUpdate(float value, PlayerEntity player) {
		return getStamina(player);
	}

	@ModifyConstant(
			method = "update",
			constant = @Constant(floatValue = 4.0f, ordinal = 1),
			require = 0
	)
	private float modifyConstant1AtUpdate(float value, PlayerEntity player) {
		return getStamina(player);
	}

	@Unique
	private float getStamina(PlayerEntity player) {
		return (float) player.getAttributeValue(AttributesMod.STAMINA);
	}
}
