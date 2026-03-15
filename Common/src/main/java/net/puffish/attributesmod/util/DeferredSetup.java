package net.puffish.attributesmod.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.puffish.attributesmod.api.DynamicAttribute;

public class DeferredSetup {
	public static final String MOD_ID = "puffish_attributes";

	public static Identifier createIdentifier(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static Attribute createRangedAttribute(Identifier id, double fallback, double min, double max) {
		return new RangedAttribute(
				id.toLanguageKey("attribute"),
				fallback,
				min,
				max
		);
	}

	public static Attribute createDynamicAttribute(Identifier id) {
		return DynamicAttribute.create(id);
	}

	public static Holder<Attribute> registerAttribute(Identifier id, Attribute attribute) {
		return Platform.INSTANCE.registerForHolder(BuiltInRegistries.ATTRIBUTE, id, attribute);
	}

}
