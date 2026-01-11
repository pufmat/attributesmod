package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.puffish.attributesmod.api.DynamicEntityAttribute;
import org.objectweb.asm.Opcodes;
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
	private RegistryEntry<EntityAttribute> type;

	@Inject(
			method = "getBaseValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtGetBaseValue(CallbackInfoReturnable<Double> cir) {
		if (type.value() instanceof DynamicEntityAttribute) {
			cir.setReturnValue(Double.NaN);
		}
	}

	@Inject(
			method = "computeValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtComputeValue(CallbackInfoReturnable<Double> cir) {
		if (type.value() instanceof DynamicEntityAttribute) {
			cir.setReturnValue(Double.NaN);
		}
	}

	@Inject(
			method = "setBaseValue",
			at = @At("HEAD"),
			cancellable = true
	)
	private void injectAtSetBaseValue(double baseValue, CallbackInfo ci) {
		if (type.value() instanceof DynamicEntityAttribute) {
			ci.cancel();
		}
	}

	@ModifyExpressionValue(
			method = "toNbt",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;baseValue:D",
					opcode = Opcodes.GETFIELD
			),
			require = 0
	)
	private double modifyExpressionValueAtBaseValue(double original) {
		return type.value() instanceof DynamicEntityAttribute ? 0 : original;
	}
}
