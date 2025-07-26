package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.api.DynamicModification;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerEntity.class, priority = 1100)
public abstract class PlayerEntityMixin {

	private static final double VANILLA_KNOCKBACK = 0.4;

	@ModifyExpressionValue(
			method = "createPlayerAttributes",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;")
	)
	private static DefaultAttributeContainer.Builder modifyExpressionValueAtCreateLivingAttributes(DefaultAttributeContainer.Builder builder) {
		return builder
				.add(AttributesMod.STAMINA)
				.add(AttributesMod.FORTUNE)
				.add(AttributesMod.MINING_SPEED)
				.add(AttributesMod.PICKAXE_SPEED)
				.add(AttributesMod.AXE_SPEED)
				.add(AttributesMod.SHOVEL_SPEED)
				.add(AttributesMod.SPRINTING_SPEED)
				.add(AttributesMod.KNOCKBACK)
				.add(AttributesMod.REPAIR_COST)
				.add(AttributesMod.NATURAL_REGENERATION)
				.add(AttributesMod.TAMED_DAMAGE)
				.add(AttributesMod.TAMED_RESISTANCE)
				.add(AttributesMod.EXPERIENCE);
	}

	@Inject(
			method = "attack",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"
			)
	)
	private void injectAtAttack(Entity target, CallbackInfo ci) {
		var player = (PlayerEntity) (Object) this;

		var knockback = DynamicModification.create()
				.withPositive(AttributesMod.KNOCKBACK, player)
				.applyTo(VANILLA_KNOCKBACK) - VANILLA_KNOCKBACK;

		var yaw = player.getYaw() * MathHelper.RADIANS_PER_DEGREE;
		var sin = MathHelper.sin(yaw);
		var cos = MathHelper.cos(yaw);

		if (target instanceof LivingEntity livingEntity) {
			livingEntity.takeKnockback(knockback, sin, -cos);
		} else {
			target.addVelocity(-sin * knockback, 0, cos * knockback);
		}
	}

	@ModifyReturnValue(method = "getMovementSpeed()F", at = @At("RETURN"))
	private float injectAtGetMovementSpeed(float speed) {
		var player = (PlayerEntity) (Object) this;

		if (!player.isSprinting()) {
			return speed;
		}

		return DynamicModification.create()
				.withPositive(AttributesMod.SPRINTING_SPEED, player)
				.applyTo(speed);
	}

}
