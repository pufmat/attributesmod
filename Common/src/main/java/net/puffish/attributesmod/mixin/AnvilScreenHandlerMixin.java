package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AnvilScreenHandler.class, priority = 1100)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	private AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@WrapOperation(
			method = "updateResult",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/screen/Property;set(I)V",
					ordinal = 5
			)
	)
	private void wrapOperationAtSet(Property property, int value, Operation<Void> operation) {
		operation.call(property, Math.max(1, Math.round(DynamicModification.create()
				.withPositive(AttributesMod.REPAIR_COST, player)
				.applyTo(value))));
	}
}
