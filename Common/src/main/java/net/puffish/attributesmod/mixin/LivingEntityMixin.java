package net.puffish.attributesmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.puffish.attributesmod.AttributesMod;
import net.puffish.attributesmod.util.DamageKind;
import net.puffish.attributesmod.util.Sign;
import net.puffish.attributesmod.util.Signed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@SuppressWarnings("unchecked")
	@ModifyVariable(
			method = "damage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyVariableAtDamage(float damage, DamageSource source) {
		if (damage < 0) {
			return damage;
		}

		if (source.getAttacker() instanceof PlayerEntity player) {
			var attributes = new ArrayList<Signed<EntityAttributeInstance>>();

			var kind = DamageKind.of(source);
			if (kind.isMagic()) {
				attributes.add(Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.MAGIC_DAMAGE)));
			}
			if (kind.isProjectile()) {
				attributes.add(Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.RANGED_DAMAGE)));
			}
			if (kind.isMelee()) {
				attributes.add(Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.MELEE_DAMAGE)));
			}

			damage = (float) AttributesMod.applyAttributeModifiers(
					damage,
					attributes.toArray(Signed[]::new)
			);
		}

		return damage;
	}

	@ModifyArg(
			method = "applyArmorToDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(Lnet/minecraft/entity/LivingEntity;FLnet/minecraft/entity/damage/DamageSource;FF)F"),
			index = 3
	)
	private float modifyArgAtApplyArmorToDamage1(float armor, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof PlayerEntity player) {
			armor = Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					armor,
					Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.ARMOR_SHRED))
			));
		}

		return armor;
	}

	@ModifyArg(
			method = "applyArmorToDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(Lnet/minecraft/entity/LivingEntity;FLnet/minecraft/entity/damage/DamageSource;FF)F"),
			index = 4
	)
	private float modifyArgAtApplyArmorToDamage2(float toughness, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof PlayerEntity player) {
			toughness = Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					toughness,
					Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.TOUGHNESS_SHRED))
			));
		}

		return toughness;
	}

	@ModifyArg(
			method = "modifyAppliedDamage",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getInflictedDamage(FF)F"),
			index = 1
	)
	private float modifyArgAtModifyAppliedDamage(float protection, @Local(argsOnly = true) DamageSource source) {
		if (source.getAttacker() instanceof PlayerEntity player) {
			protection = Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					protection,
					Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.PROTECTION_SHRED))
			));
		}

		return protection;
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

		if (((LivingEntity) (Object) this) instanceof PlayerEntity player) {
			amount = (float) AttributesMod.applyAttributeModifiers(
					amount,
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.HEALING))
			);
		}

		return amount;
	}

	@ModifyReturnValue(
			method = "getJumpVelocity",
			at = @At("RETURN")
	)
	private float injectAtGetJumpVelocity(float jump) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player) {
			return  (float) AttributesMod.applyAttributeModifiers(
					jump,
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.JUMP))
			);
		}
		return jump;
	}

	@ModifyVariable(
			method = "computeFallDamage",
			at = @At("STORE"),
			ordinal = 2
	)
	private float modifyVariableAtComputeFallDamage(float reduction) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player) {
			reduction += ((((float) AttributesMod.applyAttributeModifiers(
					1.0f,
					Sign.POSITIVE.wrap(player.getAttributeInstance(AttributesMod.JUMP))
			)) - 1.0f) * 10.0f);
		}
		return reduction;
	}

	@ModifyReturnValue(
			method = "modifyAppliedDamage",
			at = @At("TAIL")
	)
	private float injectAtModifyAppliedDamage(float damage, @Local(argsOnly = true) DamageSource source) {
		if (((LivingEntity) (Object) this) instanceof PlayerEntity player && damage < Float.MAX_VALUE / 3.0f) {
			var attributes = new ArrayList<Signed<EntityAttributeInstance>>();

			attributes.add(Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.RESISTANCE)));
			var kind = DamageKind.of(source);
			if (kind.isMagic()) {
				attributes.add(Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.MAGIC_RESISTANCE)));
			}
			if (kind.isProjectile()) {
				attributes.add(Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.RANGED_RESISTANCE)));
			}
			if (kind.isMelee()) {
				attributes.add(Sign.NEGATIVE.wrap(player.getAttributeInstance(AttributesMod.MELEE_RESISTANCE)));
			}

			return Math.max(0.0f, (float) AttributesMod.applyAttributeModifiers(
					damage,
					attributes.toArray(Signed[]::new)
			));
		}
		return damage;
	}
}
