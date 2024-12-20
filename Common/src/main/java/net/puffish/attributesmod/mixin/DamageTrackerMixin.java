package net.puffish.attributesmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {

	@Inject(
			method = "onDamage",
			at = @At("HEAD")
	)
	private void injectAtAttack(DamageSource damageSource, float damage, CallbackInfo ci) {
		if (damageSource.getAttacker() instanceof LivingEntity attacker) {
			var lifeSteal = DynamicModification.create()
					.withPositive(AttributesMod.LIFE_STEAL, attacker)
					.relativeTo(damage);

			if (lifeSteal > 0) {
				attacker.heal(lifeSteal);
			}
		}
	}

}
