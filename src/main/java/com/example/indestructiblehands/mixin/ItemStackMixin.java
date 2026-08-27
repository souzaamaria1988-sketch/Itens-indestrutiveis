package com.example.indestructiblehands.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(
            method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private <T extends LivingEntity> void preventDamage(int amount, T entity, Consumer<T> breakCallback, CallbackInfo ci) {
        if (entity == null) return;

        ItemStack self = (ItemStack) (Object) this;

        if (entity.getMainHandStack() == self || entity.getOffHandStack() == self) {
            ci.cancel();
        }
    }
}
