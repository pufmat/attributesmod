package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AnvilMenu.class, priority = 1100)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
	private AnvilMenuMixin(MenuType<?> type, int syncId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition definition) {
		super(type, syncId, inventory, access, definition);
	}

	@ModifyExpressionValue(
			method = {
					"createResult", // Fabric
					"createResultInternal"
			},
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/Mth;clamp(JJJ)J"
			)
	)
	private long modifyExpressionValueAtClamp(long value) {
		return Math.max(1, Math.round(DynamicModification.create()
				.withPositive(PuffishAttributes.REPAIR_COST, player)
				.applyTo(value)));
	}
}
