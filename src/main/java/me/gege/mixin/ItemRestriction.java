package me.gege.mixin;

import me.gege.Restriction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ItemRestriction {
    @Shadow @Final private PlayerInventory inventory;

    @Inject(at = @At("HEAD"), method = "tick")
    private void itemRestriction(CallbackInfo ci) {
        for (int i = 0; i < 37; i++) {
            if (Restriction.BANNED_ITEMS.contains(this.inventory.getStack(i).getItem())) {
                this.inventory.removeStack(i);
            }
        }
    }
}
