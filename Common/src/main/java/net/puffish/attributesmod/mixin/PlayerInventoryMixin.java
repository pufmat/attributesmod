package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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

import java.util.ArrayList;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

	@SuppressWarnings("unchecked")
	@ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At("RETURN"))
	private float injectAtGetBlockBreakingSpeed(float speed) {
		// This check is required to not break vanilla enchantments behavior
		if (speed <= 1.0f) {
			return speed;
		}

		var inventory = ((PlayerInventory) (Object) this);
		var player = inventory.player;
		var item = inventory.getMainHandStack().getItem();

		var attributes = new ArrayList<Signed<EntityAttributeInstance>>();

		if (item instanceof PickaxeItem) {
			attributes.add(Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.PICKAXE_SPEED)));
		}
		if (item instanceof AxeItem) {
			attributes.add(Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.AXE_SPEED)));
		}
		if (item instanceof ShovelItem) {
			attributes.add(Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.SHOVEL_SPEED)));
		}

		attributes.add(Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.MINING_SPEED)));

		return (float) AttributesMod.applyAttributeModifiers(
				speed,
				attributes.toArray(Signed[]::new)
		);
	}

}
