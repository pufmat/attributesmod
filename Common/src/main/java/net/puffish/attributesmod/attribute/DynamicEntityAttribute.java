package net.puffish.attributesmod.attribute;

import net.minecraft.entity.attribute.EntityAttribute;

public class DynamicEntityAttribute extends EntityAttribute {
	public DynamicEntityAttribute(String translationKey) {
		super(translationKey, Double.NaN);
	}
}
