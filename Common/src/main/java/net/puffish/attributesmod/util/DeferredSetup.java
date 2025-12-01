package net.puffish.attributesmod.util;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.puffish.attributesmod.api.DynamicEntityAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeferredSetup {
	public static final String MOD_ID = "puffish_attributes";

	private static final List<Map.Entry<Identifier, EntityAttribute>> ENTRIES = new ArrayList<>();

	public static Identifier createIdentifier(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static EntityAttribute createClampedAttribute(Identifier id, double fallback, double min, double max) {
		var attribute = new ClampedEntityAttribute(
				id.toTranslationKey("attribute"),
				fallback,
				min,
				max
		);
		ENTRIES.add(Map.entry(id, attribute));
		return attribute;
	}

	public static EntityAttribute createDynamicAttribute(Identifier id) {
		var attribute = DynamicEntityAttribute.create(id);
		ENTRIES.add(Map.entry(id, attribute));
		return attribute;
	}

	public static void setup(Registrar registrar) {
		for (var entry : ENTRIES) {
			var id = entry.getKey();
			var attribute = entry.getValue();

			registrar.register(Registry.ATTRIBUTE, id, attribute);
			registrar.registerAlias(Registry.ATTRIBUTE, new Identifier(id.getNamespace(), "player." + id.getPath()), id);
		}
	}

}
