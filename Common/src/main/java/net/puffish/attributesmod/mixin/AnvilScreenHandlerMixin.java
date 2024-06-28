package net.puffish.attributesmod.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.Sign;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	private AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@ModifyArg(
			method = "updateResult",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/screen/Property;set(I)V",
					ordinal = 5
			)
	)
	private int modifyArgAtSet(int value) {
		return (int) Math.max(1, Math.round(AttributesMod.applyAttributeModifiers(
				value,
				Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.REPAIR_COST))
		)));
	}
}
