package com.example.indestructiblehands.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void preventDamage(int amount, Random random, LivingEntity user, CallbackInfo ci) {
        if (user == null) return;
        
        ItemStack self = (ItemStack) (Object) this;
        
        // Verifica se o item que está sofrendo dano é o mesmo que está na mão principal ou secundária
        if (user.getMainHandStack() == self || user.getOffHandStack() == self) {
            ci.cancel(); // Cancela o dano, tornando o item indestrutível enquanto estiver na mão
        }
    }
}
