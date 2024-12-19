package net.puffish.attributesmod.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.puffish.attributesmod.util.DynamicModificationImpl;

public interface DynamicModification {
	static DynamicModification create() {
		return new DynamicModificationImpl();
	}

	DynamicModification withPositive(EntityAttribute attribute, LivingEntity entity);
	DynamicModification withNegative(EntityAttribute attribute, LivingEntity entity);

	double applyTo(double value);
	float applyTo(float value);

	double relativeTo(double value);
	float relativeTo(float value);
}
