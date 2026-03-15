package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TargetingConditions.class)
public class TargetingConditionsMixin {

	@ModifyArg(method = "test", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D"), index = 0)
	private double modifyArgAtMax(double distance, @Local(argsOnly = true, ordinal = 1) LivingEntity targetEntity) {
		return DynamicModification.create()
				.withNegative(PuffishAttributes.STEALTH, targetEntity)
				.applyTo(distance);
	}

}
