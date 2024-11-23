package net.puffish.attributesmod.mixin;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import net.puffish.attributesmod.util.RegistryEntryReferenceWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AttributeContainer.class)
public class AttributeContainerMixin {

	@ModifyVariable(method = {
			"getCustomInstance",
			"hasAttribute",
			"hasModifierForAttribute",
			"getValue",
			"getBaseValue",
			"getModifierValue"
	}, at = @At("HEAD"), ordinal = 0)
	private RegistryEntry<EntityAttribute> modifyVariableAt(RegistryEntry<EntityAttribute> attribute) {
		if (attribute instanceof RegistryEntryReferenceWrapper<EntityAttribute> wrapper) {
			return wrapper.getOriginal();
		}
		return attribute;
	}

}
