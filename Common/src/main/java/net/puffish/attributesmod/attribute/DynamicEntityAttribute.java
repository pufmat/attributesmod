package net.puffish.attributesmod.attribute;

import net.minecraft.entity.attribute.ClampedEntityAttribute;

public class DynamicEntityAttribute extends ClampedEntityAttribute {
	public DynamicEntityAttribute(String translationKey, double min, double max) {
		super(translationKey, Double.NaN, min, max);
	}
}
