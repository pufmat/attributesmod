package net.puffish.attributesmod.util;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.puffish.attributesmod.api.DynamicAttribute;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.mixin.AttributeInstanceInvoker;

import java.util.ArrayList;
import java.util.List;

public class DynamicModificationImpl implements DynamicModification {
	private final List<Signed<AttributeInstance>> attributes = new ArrayList<>();

	@Override
	public DynamicModification withPositive(Holder<Attribute> attribute, LivingEntity entity) {
		return with(Sign.POSITIVE, attribute, entity);
	}

	@Override
	public DynamicModification withNegative(Holder<Attribute> attribute, LivingEntity entity) {
		return with(Sign.NEGATIVE, attribute, entity);
	}

	public DynamicModification with(Sign sign, Holder<Attribute> attribute, LivingEntity entity) {
		if (!(attribute.value() instanceof DynamicAttribute)) {
			throw new IllegalArgumentException();
		}

		return with(sign.wrap(entity.getAttribute(attribute)));
	}

	public DynamicModification with(Signed<AttributeInstance> signed) {
		attributes.add(signed);
		return this;
	}

	@Override
	public double applyTo(double initial) {
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((AttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(AttributeModifier.Operation.ADD_VALUE)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> initial += modifier.amount();
					case NEGATIVE -> initial -= modifier.amount();
					default -> throw new IllegalStateException();
				}
			}
		}
		double result = initial;
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((AttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result += initial * modifier.amount();
					case NEGATIVE -> result -= initial * modifier.amount();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			for (var modifier : ((AttributeInstanceInvoker) signedAttribute.value())
					.invokeGetModifiersByOperation(AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
			) {
				switch (signedAttribute.sign()) {
					case POSITIVE -> result *= 1.0 + modifier.amount();
					case NEGATIVE -> result *= 1.0 - modifier.amount();
					default -> throw new IllegalStateException();
				}
			}
		}
		for (var signedAttribute : attributes) {
			if (signedAttribute.value() == null) {
				continue;
			}
			result = signedAttribute.value().getAttribute().value().sanitizeValue(result);
		}
		return result;
	}

	@Override
	public float applyTo(float initial) {
		return (float) applyTo((double) initial);
	}

	@Override
	public double applyToReciprocal(double value) {
		return 1.0 / applyTo(1.0 / value);
	}

	@Override
	public float applyToReciprocal(float value) {
		return (float) applyToReciprocal((double) value);
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
