package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

	@ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At("RETURN"))
	private float injectAtGetBlockBreakingSpeed(float speed) {
		// This check is required to not break vanilla enchantments behavior
		if (speed <= 1.0f) {
			return speed;
		}

		var inventory = ((PlayerInventory) (Object) this);
		var player = inventory.player;
		var item = inventory.getMainHandStack().getItem();

		var dm = DynamicModification.create();
		if (item instanceof PickaxeItem) {
			dm.withPositive(PuffishAttributes.PICKAXE_SPEED, player);
		}
		if (item instanceof AxeItem) {
			dm.withPositive(PuffishAttributes.AXE_SPEED, player);
		}
		if (item instanceof ShovelItem) {
			dm.withPositive(PuffishAttributes.SHOVEL_SPEED, player);
		}
		dm.withPositive(PuffishAttributes.MINING_SPEED, player);

		return dm.applyTo(speed);
	}

}
