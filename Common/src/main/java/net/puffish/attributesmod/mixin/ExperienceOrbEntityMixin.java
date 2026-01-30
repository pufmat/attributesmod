package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	@WrapMethod(method = "onPlayerCollision")
	private void wrapMethodOnPlayerCollision(PlayerEntity player, Operation<Integer> original) {
		// workaround for mods that inject and cancel
		var previousExperience = player.totalExperience;
		original.call(player);
		player.addExperience(Math.round(DynamicModification.create()
				.withPositive(PuffishAttributes.EXPERIENCE, player)
				.relativeTo(player.totalExperience - previousExperience)
		));
	}

}
