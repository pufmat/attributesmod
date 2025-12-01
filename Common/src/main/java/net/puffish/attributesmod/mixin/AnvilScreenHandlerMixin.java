package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AnvilScreenHandler.class, priority = 1100)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	private AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
		super(type, syncId, playerInventory, context, forgingSlotsManager);
	}

	@ModifyExpressionValue(
			method = {
					"updateResult", // Fabric
					"createResultInternal" // NeoForge
			},
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/math/MathHelper;clamp(JJJ)J"
			)
	)
	private long modifyExpressionValueAtClamp(long value) {
		return Math.max(1, Math.round(DynamicModification.create()
				.withPositive(PuffishAttributes.REPAIR_COST, player)
				.applyTo(value)));
	}
}
