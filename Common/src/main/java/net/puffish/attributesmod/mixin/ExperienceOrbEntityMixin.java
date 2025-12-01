package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	@ModifyArg(
			method = "onPlayerCollision",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"
			),
			index = 0
	)
	private int modifyArgAtAddExperience(int experience, @Local(argsOnly = true) PlayerEntity player) {
		return Math.round(DynamicModification.create()
				.withPositive(PuffishAttributes.EXPERIENCE, player)
				.applyTo(experience));
	}

}
