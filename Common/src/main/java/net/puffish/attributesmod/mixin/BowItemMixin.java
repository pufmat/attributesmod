package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

	@ModifyArg(
			method = "onStoppedUsing",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"
			),
			index = 4
	)
	private float modifyArgAtSetVelocity(float speed, @Local(argsOnly = true) LivingEntity user) {
		return DynamicModification.create()
				.withPositive(AttributesMod.BOW_PROJECTILE_SPEED, user)
				.applyTo(speed);
	}

}
