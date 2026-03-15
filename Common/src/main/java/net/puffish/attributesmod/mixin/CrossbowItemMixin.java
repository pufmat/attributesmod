package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

	@ModifyArg(
			method = "shootProjectile",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/projectile/Projectile;shoot(DDDFF)V"
			),
			index = 3
	)
	private float modifyArgAtSetVelocity(float speed, @Local(argsOnly = true, ordinal = 0) LivingEntity user) {
		return DynamicModification.create()
				.withPositive(PuffishAttributes.CROSSBOW_PROJECTILE_SPEED, user)
				.applyTo(speed);
	}

}
