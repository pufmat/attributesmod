package net.puffish.attributesmod.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.puffish.attributesmod.api.DynamicEntityAttribute;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.mixin.EntityAttributeInstanceInvoker;

import java.util.ArrayList;
import java.util.List;

public class DynamicModificationImpl implements DynamicModification {
	private final List<Signed<EntityAttributeInstance>> attributes = new ArrayList<>();

	@Override
	public DynamicModification withPositive(EntityAttribute attribute, LivingEntity entity) {
		return with(Sign.POSITIVE, attribute, entity);
	}

	@Override
	public DynamicModification withNegative(EntityAttribute attribute, LivingEntity entity) {
		return with(Sign.NEGATIVE, attribute, entity);
	}

	public DynamicModification with(Sign sign, EntityAttribute attribute, LivingEntity entity) {
		if (!(attribute instanceof DynamicEntityAttribute)) {
			throw new IllegalArgumentException();
		}

		return with(sign.wrap(entity.getAttributeInstance(attribute)));
	}

	public DynamicModification with(Signed<EntityAttributeInstance> signed) {
		attributes.add(signed);
		return this;
	}

	@Override
	public double applyTo(double initial) {
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((EntityAttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.ADDITION)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> initial += modifier.getValue();
					case NEGATIVE -> initial -= modifier.getValue();
					default -> throw new IllegalStateException();
				}
			}
		}
		double result = initial;
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((EntityAttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.MULTIPLY_BASE)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result += initial * modifier.getValue();
					case NEGATIVE -> result -= initial * modifier.getValue();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((EntityAttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result *= 1.0 + modifier.getValue();
					case NEGATIVE -> result *= 1.0 - modifier.getValue();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			result = signedAttribute.value().getAttribute().clamp(result);
		}
		return result;
	}

	@Override
	public float applyTo(float initial) {
		return (float) applyTo((double) initial);
	}

	@Override
	public double relativeTo(double initial) {
		return applyTo(initial) - initial;
	}

	@Override
	public float relativeTo(float initial) {
		return (float) relativeTo((double) initial);
	}
}
