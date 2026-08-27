package com.example.indestructiblehands.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(
            method = "damage(ILjava/util/Random;Lnet/minecraft/entity/LivingEntity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preventDamage(int amount, Random random, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity == null) return;
        
        ItemStack self = (ItemStack) (Object) this;
        
        // Verifica se o item está na mão principal ou secundária (escudo)
        if (entity.getMainHandStack() == self || entity.getOffHandStack() == self) {
            cir.setReturnValue(false); // Cancela o dano e informa que o item não quebrou
        }
    }
}
