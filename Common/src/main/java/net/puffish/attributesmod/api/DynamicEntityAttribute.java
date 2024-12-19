package net.puffish.attributesmod.api;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;

public class DynamicEntityAttribute extends EntityAttribute {

	public static DynamicEntityAttribute create(Identifier id) {
		return new DynamicEntityAttribute(
				"attribute." + id.getNamespace() + "." + id.getPath()
		);
	}

	public DynamicEntityAttribute(String translationKey) {
		super(translationKey, Double.NaN);
	}

}
