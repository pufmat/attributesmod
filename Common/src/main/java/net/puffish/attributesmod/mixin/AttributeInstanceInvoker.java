package net.puffish.attributesmod.mixin;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Collection;

@Mixin(AttributeInstance.class)
public interface AttributeInstanceInvoker {
	@Invoker("getModifiersOrEmpty")
	Collection<AttributeModifier> invokeGetModifiersByOperation(
			AttributeModifier.Operation operation
	);
}
