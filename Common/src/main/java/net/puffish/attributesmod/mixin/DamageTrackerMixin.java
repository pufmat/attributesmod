package net.puffish.attributesmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.world.ServerWorld;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {

	@Shadow
	@Final
	private LivingEntity entity;

	@Inject(
			method = "onDamage",
			at = @At("HEAD")
	)
	private void injectAtAttack(DamageSource damageSource, float damage, CallbackInfo ci) {
		if (damageSource.getAttacker() instanceof LivingEntity attacker) {
			var lifeSteal = DynamicModification.create()
					.withPositive(PuffishAttributes.LIFE_STEAL, attacker)
					.relativeTo(damage);

			if (lifeSteal > 0) {
				attacker.heal(lifeSteal);
			}

			if (!damageSource.isOf(DamageTypes.THORNS)) {
				var reflection = DynamicModification.create()
						.withPositive(PuffishAttributes.DAMAGE_REFLECTION, entity)
						.relativeTo(damage);

				if (reflection > 0 && attacker.getWorld() instanceof ServerWorld world) {
					attacker.damage(world, world.getDamageSources().thorns(entity), reflection);
				}
			}
		}
	}

}
