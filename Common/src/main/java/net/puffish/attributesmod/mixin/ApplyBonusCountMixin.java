package net.puffish.attributesmod.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ApplyBonusCount.class)
public abstract class ApplyBonusCountMixin {

	@Shadow
	@Final
	private Holder<Enchantment> enchantment;

	@ModifyVariable(
			method = "run",
			at = @At("STORE"),
			ordinal = 0
	)
	private int modifyVariableAtProcess(int value, ItemStack itemStack, LootContext context) {
		if (enchantment.is(Enchantments.FORTUNE) && context.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player player) {
			var fortune = DynamicModification.create()
					.withPositive(PuffishAttributes.FORTUNE, player)
					.applyTo(value);

			value = (int) fortune;
			if (context.getRandom().nextFloat() < fortune - value) {
				value++;
			}
		}
		return value;
	}
}
