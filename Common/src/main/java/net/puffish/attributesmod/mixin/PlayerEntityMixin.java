package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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
				.add(AttributesMod.TAMED_RESISTANCE);
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
			dm.withPositive(AttributesMod.PICKAXE_SPEED, player);
		}
		if (itemStack.isIn(ItemTags.AXES)) {
			dm.withPositive(AttributesMod.AXE_SPEED, player);
		}
		if (itemStack.isIn(ItemTags.SHOVELS)) {
			dm.withPositive(AttributesMod.SHOVEL_SPEED, player);
		}
		dm.withPositive(AttributesMod.MINING_SPEED, player);

		return dm.applyTo(speed);
	}


}
