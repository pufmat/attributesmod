package net.puffish.attributesmod.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.registry.entry.RegistryEntry;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.Sign;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ApplyBonusLootFunction.class)
public abstract class ApplyBonusLootFunctionMixin {

	@Shadow
	@Final
	private RegistryEntry<Enchantment> enchantment;

	@ModifyVariable(
			method = "process",
			at = @At("STORE"),
			ordinal = 0
	)
	private int modifyVariableAtProcess(int value, ItemStack itemStack, LootContext context) {
		if (enchantment.value() == Enchantments.FORTUNE && context.get(LootContextParameters.THIS_ENTITY) instanceof PlayerEntity player) {
			var fortune = AttributesMod.applyAttributeModifiers(
					value,
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.FORTUNE))
			);

			value = (int) fortune;
			if (context.getRandom().nextFloat() < fortune - value) {
				value++;
			}
		}
		return value;
	}
}
