package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.MathHelper;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerEntity.class, priority = 1100)
public abstract class PlayerEntityMixin {

	private static final double VANILLA_KNOCKBACK = 0.4;

	@ModifyExpressionValue(
			method = "createPlayerAttributes",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;")
	)
	private static DefaultAttributeContainer.Builder modifyExpressionValueAtCreateLivingAttributes(DefaultAttributeContainer.Builder builder) {
		return builder
				.add(PuffishAttributes.STAMINA)
				.add(PuffishAttributes.FORTUNE)
				.add(PuffishAttributes.MINING_SPEED)
				.add(PuffishAttributes.BREAKING_SPEED)
				.add(PuffishAttributes.PICKAXE_SPEED)
				.add(PuffishAttributes.AXE_SPEED)
				.add(PuffishAttributes.SHOVEL_SPEED)
				.add(PuffishAttributes.SPRINTING_SPEED)
				.add(PuffishAttributes.MOUNT_SPEED)
				.add(PuffishAttributes.CONSUMING_SPEED)
				.add(PuffishAttributes.KNOCKBACK)
				.add(PuffishAttributes.REPAIR_COST)
				.add(PuffishAttributes.NATURAL_REGENERATION)
				.add(PuffishAttributes.TAMED_DAMAGE)
				.add(PuffishAttributes.TAMED_RESISTANCE)
				.add(PuffishAttributes.EXPERIENCE);
	}

	@Inject(
			method = "attack",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)V"
			)
	)
	private void injectAtAttack(Entity target, CallbackInfo ci) {
		var player = (PlayerEntity) (Object) this;

		var knockback = DynamicModification.create()
				.withPositive(PuffishAttributes.KNOCKBACK, player)
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
				.withPositive(PuffishAttributes.SPRINTING_SPEED, player)
				.applyTo(speed);
	}

	@WrapOperation(
			method = {
					"getBlockBreakingSpeed", // Fabric
					"getDestroySpeed" // NeoForge
			},
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;getMiningSpeedMultiplier(Lnet/minecraft/block/BlockState;)F"
			)
	)
	private float wrapOperationAtGetMiningSpeedMultiplier(ItemStack itemStack, BlockState blockState, Operation<Float> operation) {
		var speed = operation.call(itemStack, blockState);

		// This check is required to not break vanilla enchantments behavior
		if (speed <= 1.0f) {
			return speed;
		}

		var player = (PlayerEntity) (Object) this;

		var dm = DynamicModification.create();

		if (itemStack.isIn(ItemTags.PICKAXES)) {
			dm.withPositive(PuffishAttributes.PICKAXE_SPEED, player);
		}
		if (itemStack.isIn(ItemTags.AXES)) {
			dm.withPositive(PuffishAttributes.AXE_SPEED, player);
		}
		if (itemStack.isIn(ItemTags.SHOVELS)) {
			dm.withPositive(PuffishAttributes.SHOVEL_SPEED, player);
		}
		dm.withPositive(PuffishAttributes.MINING_SPEED, player);

		return dm.applyTo(speed);
	}

	@Inject(
			method = {
					"getBlockBreakingSpeed", // Fabric
					"getDestroySpeed" // NeoForge
			},
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/effect/StatusEffectUtil;hasHaste(Lnet/minecraft/entity/LivingEntity;)Z"
			)
	)
	private void injectAtGetBlockBreakingSpeed(CallbackInfoReturnable<Float> cir, @Local LocalFloatRef speed) {
		var player = (PlayerEntity) (Object) this;

		speed.set(DynamicModification.create()
				.withPositive(PuffishAttributes.BREAKING_SPEED, player)
				.applyTo(speed.get()));
	}

}
