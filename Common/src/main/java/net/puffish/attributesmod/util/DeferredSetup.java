package net.puffish.attributesmod.util;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.puffish.attributesmod.api.DynamicEntityAttribute;

public class DeferredSetup {
	public static final String MOD_ID = "puffish_attributes";

	public static Identifier createIdentifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static EntityAttribute createClampedAttribute(Identifier id, double fallback, double min, double max) {
		return new ClampedEntityAttribute(
				id.toTranslationKey("attribute"),
				fallback,
				min,
				max
		);
	}

	public static EntityAttribute createDynamicAttribute(Identifier id) {
		return DynamicEntityAttribute.create(id);
	}

	public static RegistryEntry<EntityAttribute> registerAttribute(Identifier id, EntityAttribute attribute) {
		return Platform.INSTANCE.registerReference(Registries.ATTRIBUTE, id, attribute);
	}

}
