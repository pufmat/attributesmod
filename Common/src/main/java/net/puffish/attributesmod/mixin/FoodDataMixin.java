package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = FoodData.class, priority = 1100)
public abstract class FoodDataMixin {

	@WrapOperation(
			method = "tick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/server/level/ServerPlayer;heal(F)V"
			)
	)
	private void wrapOperationAtHeal(ServerPlayer player, float amount, Operation<Void> operation) {
		operation.call(player, Math.max(0.0f, DynamicModification.create()
				.withPositive(PuffishAttributes.NATURAL_REGENERATION, player)
				.applyTo(amount)));
	}

	@ModifyConstant(
			method = "tick",
			constant = @Constant(floatValue = 4.0f, ordinal = 0),
			require = 0
	)
	private float modifyConstant0AtUpdate(float value, ServerPlayer player) {
		return getStamina(player);
	}

	@ModifyConstant(
			method = "tick",
			constant = @Constant(floatValue = 4.0f, ordinal = 1),
			require = 0
	)
	private float modifyConstant1AtUpdate(float value, ServerPlayer player) {
		return getStamina(player);
	}

	@Unique
	private float getStamina(Player player) {
		return (float) player.getAttributeValue(PuffishAttributes.STAMINA);
	}
}
