package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

	@ModifyArg(
			method = "shoot",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/projectile/ProjectileEntity;setVelocity(DDDFF)V"
			),
			index = 3
	)
	private static float modifyArgAtSetVelocity(float speed, @Local(argsOnly = true) LivingEntity user) {
		return DynamicModification.create()
				.withPositive(AttributesMod.CROSSBOW_PROJECTILE_SPEED, user)
				.applyTo(speed);
	}

}
