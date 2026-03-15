package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

	@WrapMethod(method = "playerTouch")
	private void wrapMethodOnPlayerCollision(Player player, Operation<Integer> original) {
		// workaround for mods that inject and cancel
		var previousExperience = player.totalExperience;
		original.call(player);
		player.giveExperiencePoints(Math.round(DynamicModification.create()
				.withPositive(PuffishAttributes.EXPERIENCE, player)
				.relativeTo(player.totalExperience - previousExperience)
		));
	}

}
