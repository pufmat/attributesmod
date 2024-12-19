package net.puffish.attributesmod.api;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;

public class DynamicEntityAttribute extends EntityAttribute {

	public static DynamicEntityAttribute create(Identifier id) {
		return new DynamicEntityAttribute(
				id.toTranslationKey("attribute")
		);
	}

	public DynamicEntityAttribute(String translationKey) {
		super(translationKey, Double.NaN);
	}

}
