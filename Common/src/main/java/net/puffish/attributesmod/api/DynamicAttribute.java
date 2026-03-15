package net.puffish.attributesmod.api;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class DynamicAttribute extends Attribute {

	public static DynamicAttribute create(Identifier id) {
		return new DynamicAttribute(
				id.toLanguageKey("attribute")
		);
	}

	public DynamicAttribute(String translationKey) {
		super(translationKey, Double.NaN);
	}

}
