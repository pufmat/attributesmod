package net.puffish.attributesmod.mixin;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.Sign;
import net.puffish.attributesmod.util.Signed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

	@Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
	private void injectAtGetBlockBreakingSpeed(CallbackInfoReturnable<Float> cir) {
		if (cir.getReturnValueF() > 1.0f) { // This check is required to not break vanilla enchantments behavior
			var inventory = ((PlayerInventory) (Object) this);
			var player = inventory.player;

			cir.setReturnValue((float) AttributesMod.applyAttributeModifiers(
					cir.getReturnValueF(),
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.MINING_SPEED))
			));
		}
	}

}
