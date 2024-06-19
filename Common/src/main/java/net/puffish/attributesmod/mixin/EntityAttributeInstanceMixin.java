package net.puffish.attributesmod.mixin;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.puffish.attributesmod.attribute.DynamicEntityAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAttributeInstance.class)
public class EntityAttributeInstanceMixin {
	@Shadow
	@Final
	private EntityAttribute type;

	@Inject(
			method = "getBaseValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtGetBaseValue(CallbackInfoReturnable<Double> cir) {
		if (type instanceof DynamicEntityAttribute) {
			cir.setReturnValue(Double.NaN);
		}
	}

	@Inject(
			method = "computeValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtComputeValue(CallbackInfoReturnable<Double> cir) {
		if (type instanceof DynamicEntityAttribute) {
			cir.setReturnValue(Double.NaN);
		}
	}

	@Inject(
			method = "setBaseValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtSetBaseValue(double baseValue, CallbackInfo ci) {
		if (type instanceof DynamicEntityAttribute) {
			ci.cancel();
		}
	}
}
