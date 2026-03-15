package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.puffish.attributesmod.api.DynamicAttribute;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AttributeInstance.class)
public class AttributeInstanceMixin {
	@Shadow
	@Final
	private Holder<Attribute> attribute;

	@Inject(
			method = "getBaseValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtGetBaseValue(CallbackInfoReturnable<Double> cir) {
		if (attribute.value() instanceof DynamicAttribute) {
			cir.setReturnValue(Double.NaN);
		}
	}

	@Inject(
			method = "calculateValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtComputeValue(CallbackInfoReturnable<Double> cir) {
		if (attribute.value() instanceof DynamicAttribute) {
			cir.setReturnValue(Double.NaN);
		}
	}

	@Inject(
			method = "setBaseValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtSetBaseValue(double baseValue, CallbackInfo ci) {
		if (attribute.value() instanceof DynamicAttribute) {
			ci.cancel();
		}
	}

	@ModifyExpressionValue(
			method = "pack",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;baseValue:D",
					opcode = Opcodes.GETFIELD
			)
	)
	private double modifyExpressionValueAtBaseValue(double original) {
		return attribute.value() instanceof DynamicAttribute ? 0 : original;
	}
}
