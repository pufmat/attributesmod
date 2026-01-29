package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public abstract class ItemMixin {

	@ModifyExpressionValue(
			method = "getMaxUseTime",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/component/type/ConsumableComponent;getConsumeTicks()I"
			)
	)
	private int modifyExpressionValueAtGetConsumeTicks(
			int ticks,
			@Local(argsOnly = true) LivingEntity user
	) {
		return Math.max(1, Math.round(DynamicModification.create()
				.withPositive(PuffishAttributes.CONSUMING_SPEED, user)
				.applyToReciprocal(ticks)));
	}

}
