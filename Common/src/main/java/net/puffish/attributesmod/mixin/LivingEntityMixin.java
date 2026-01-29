package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MaceItem;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;
import net.puffish.attributesmod.util.DamageKind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LivingEntity.class, priority = 1100)
public abstract class LivingEntityMixin {

	@ModifyExpressionValue(
			method = "createLivingAttributes",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer;builder()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;")
	)
	private static DefaultAttributeContainer.Builder modifyExpressionValueAtBuilder(DefaultAttributeContainer.Builder builder) {
		return builder
				.add(PuffishAttributes.MAGIC_DAMAGE)
				.add(PuffishAttributes.MELEE_DAMAGE)
				.add(PuffishAttributes.RANGED_DAMAGE)
				.add(PuffishAttributes.SWORD_DAMAGE)
				.add(PuffishAttributes.AXE_DAMAGE)
				.add(PuffishAttributes.TRIDENT_DAMAGE)
				.add(PuffishAttributes.MACE_DAMAGE)
				.add(PuffishAttributes.HEALING)
				.add(PuffishAttributes.JUMP)
				.add(PuffishAttributes.RESISTANCE)
				.add(PuffishAttributes.MAGIC_RESISTANCE)
				.add(PuffishAttributes.MELEE_RESISTANCE)
				.add(PuffishAttributes.RANGED_RESISTANCE)
				.add(PuffishAttributes.ARMOR_SHRED)
				.add(PuffishAttributes.TOUGHNESS_SHRED)
				.add(PuffishAttributes.PROTECTION_SHRED)
				.add(PuffishAttributes.RESISTANCE_SHRED)
				.add(PuffishAttributes.MAGIC_RESISTANCE_SHRED)
				.add(PuffishAttributes.MELEE_RESISTANCE_SHRED)
				.add(PuffishAttributes.RANGED_RESISTANCE_SHRED)
				.add(PuffishAttributes.DAMAGE_REFLECTION)
				.add(PuffishAttributes.STEALTH)
				.add(PuffishAttributes.LIFE_STEAL)
				.add(PuffishAttributes.FALL_REDUCTION)
				.add(PuffishAttributes.BOW_PROJECTILE_SPEED)
				.add(PuffishAttributes.CROSSBOW_PROJECTILE_SPEED);
	}

	@ModifyVariable(
			method = "damage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyVariableAtDamage(float damage, ServerWorld world, DamageSource source) {
		if (damage < 0) {
			return damage;
		}

		if (source.getAttacker() instanceof LivingEntity attacker) {
			var dm = DynamicModification.create();

			var itemStack = attacker.getMainHandStack();
			var item = itemStack.getItem();
			if (itemStack.isIn(ItemTags.SWORDS)) {
				dm.withPositive(PuffishAttributes.SWORD_DAMAGE, attacker);
			}
			if (itemStack.isIn(ItemTags.AXES)) {
				dm.withPositive(PuffishAttributes.AXE_DAMAGE, attacker);
			}
			if (item instanceof TridentItem) {
				dm.withPositive(PuffishAttributes.TRIDENT_DAMAGE, attacker);
			}
			if (item instanceof MaceItem) {
				dm.withPositive(PuffishAttributes.MACE_DAMAGE, attacker);
			}

			var kind = DamageKind.of(source);
			if (kind.isMagic()) {
				dm.withPositive(PuffishAttributes.MAGIC_DAMAGE, attacker);
			} else {
				if (kind.isProjectile()) {
					dm.withPositive(PuffishAttributes.RANGED_DAMAGE, attacker);
				}
				if (kind.isMelee()) {
					dm.withPositive(PuffishAttributes.MELEE_DAMAGE, attacker);
				}
			}

			if (attacker instanceof Tameable tameable) {
				var owner = tameable.getOwner();
				if (owner != null) {
					dm.withPositive(PuffishAttributes.TAMED_DAMAGE, owner);
				}
			}

			damage = dm.applyTo(damage);
		}

		return damage;
	}

	@WrapOperation(
			method = "applyArmorToDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(Lnet/minecraft/entity/LivingEntity;FLnet/minecraft/entity/damage/DamageSource;FF)F")
	)
	private float wrapOperationAtApplyArmorToDamage(LivingEntity entity, float damage, DamageSource source, float armor, float toughness, Operation<Float> operation) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			armor = Math.max(0.0f, DynamicModification.create()
					.withNegative(PuffishAttributes.ARMOR_SHRED, attacker)
					.applyTo(armor));
			toughness = Math.max(0.0f, DynamicModification.create()
					.withNegative(PuffishAttributes.TOUGHNESS_SHRED, attacker)
					.applyTo(toughness));
		}

		return operation.call(entity, damage, source, armor, toughness);
	}

	@WrapOperation(
			method = "modifyAppliedDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getInflictedDamage(FF)F")
	)
	private float wrapOperationAtModifyAppliedDamage(float damageDealt, float protection, Operation<Float> original, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			protection = Math.max(0.0f, DynamicModification.create()
					.withNegative(PuffishAttributes.PROTECTION_SHRED, attacker)
					.applyTo(protection));
		}

		return original.call(damageDealt, protection);
	}

	@ModifyExpressionValue(
			method = "travelControlled",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/LivingEntity;getSaddledSpeed(Lnet/minecraft/entity/player/PlayerEntity;)F"
			)
	)
	private float modifyExpressionValueAtGetSaddledSpeed(
			float speed,
			@Local(argsOnly = true) PlayerEntity controllingPlayer
	) {
		return DynamicModification.create()
				.withPositive(PuffishAttributes.MOUNT_SPEED, controllingPlayer)
				.applyTo(speed);
	}

	@ModifyVariable(
			method = "heal",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyVariableAtHeal(float amount) {
		if (amount < 0) {
			return amount;
		}

		return DynamicModification.create()
				.withPositive(PuffishAttributes.HEALING, ((LivingEntity) (Object) this))
				.applyTo(amount);
	}

	@ModifyReturnValue(
			method = "getJumpVelocity",
			at = @At("RETURN")
	)
	private float injectAtGetJumpVelocity(float jump) {
		return DynamicModification.create()
				.withPositive(PuffishAttributes.JUMP, ((LivingEntity) (Object) this))
				.applyTo(jump);
	}

	@ModifyVariable(
			method = "computeFallDamage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private double modifyVariableAtComputeFallDamage(double fallDistance) {
		return DynamicModification.create()
				.withNegative(PuffishAttributes.FALL_REDUCTION, ((LivingEntity) (Object) this))
				.applyTo(fallDistance)
				- DynamicModification.create()
				.withPositive(PuffishAttributes.JUMP, ((LivingEntity) (Object) this))
				.relativeTo(1.0f) * 10.0f;
	}

	@WrapMethod(
			method = "applyArmorToDamage"
	)
	private float wrapMethodApplyArmorToDamage(DamageSource source, float amount, Operation<Float> original) {
		var damage = original.call(source, amount);

		if (source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
			return damage;
		}
		if (damage > Float.MAX_VALUE / 3.0f) {
			return damage;
		}

		var entity = ((LivingEntity) (Object) this);
		var kind = DamageKind.of(source);

		var dmResistance = DynamicModification.create();
		dmResistance.withPositive(PuffishAttributes.RESISTANCE, entity);
		if (kind.isMagic()) {
			dmResistance.withPositive(PuffishAttributes.MAGIC_RESISTANCE, entity);
		} else {
			if (kind.isProjectile()) {
				dmResistance.withPositive(PuffishAttributes.RANGED_RESISTANCE, entity);
			}
			if (kind.isMelee()) {
				dmResistance.withPositive(PuffishAttributes.MELEE_RESISTANCE, entity);
			}
		}

		if (entity instanceof Tameable tameable) {
			var owner = tameable.getOwner();
			if (owner != null) {
				dmResistance.withPositive(PuffishAttributes.TAMED_RESISTANCE, owner);
			}
		}

		var resistance = dmResistance.relativeTo(damage);

		if (source.getAttacker() instanceof LivingEntity attacker) {
			var dmShred = DynamicModification.create();
			dmShred.withNegative(PuffishAttributes.RESISTANCE_SHRED, attacker);
			if (kind.isMagic()) {
				dmShred.withNegative(PuffishAttributes.MAGIC_RESISTANCE_SHRED, attacker);
			} else {
				if (kind.isProjectile()) {
					dmShred.withNegative(PuffishAttributes.RANGED_RESISTANCE_SHRED, attacker);
				}
				if (kind.isMelee()) {
					dmShred.withNegative(PuffishAttributes.MELEE_RESISTANCE_SHRED, attacker);
				}
			}

			var shred = dmShred.relativeTo(resistance);

			// shred cannot be greater than resistance
			shred = Math.min(shred, resistance);

			resistance -= shred;
		}

		return Math.max(0.0f, damage - resistance);
	}
}
