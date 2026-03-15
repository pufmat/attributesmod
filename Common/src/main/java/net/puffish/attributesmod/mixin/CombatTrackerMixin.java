package net.puffish.attributesmod.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CombatTracker.class)
public class CombatTrackerMixin {

	@Shadow
	@Final
	private LivingEntity mob;

	@Inject(
			method = "recordDamage",
			at = @At("HEAD")
	)
	private void injectAtAttack(DamageSource damageSource, float damage, CallbackInfo ci) {
		if (damageSource.getEntity() instanceof LivingEntity attacker) {
			var lifeSteal = DynamicModification.create()
					.withPositive(PuffishAttributes.LIFE_STEAL, attacker)
					.relativeTo(damage);

			if (lifeSteal > 0) {
				attacker.heal(lifeSteal);
			}

			if (!damageSource.is(DamageTypes.THORNS)) {
				var reflection = DynamicModification.create()
						.withPositive(PuffishAttributes.DAMAGE_REFLECTION, mob)
						.relativeTo(damage);

				if (reflection > 0 && attacker.level() instanceof ServerLevel world) {
					attacker.hurtServer(world, world.damageSources().thorns(mob), reflection);
				}
			}
		}
	}

}
