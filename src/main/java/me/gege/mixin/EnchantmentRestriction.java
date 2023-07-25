package me.gege.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class EnchantmentRestriction extends ForgingScreenHandler {
    public EnchantmentRestriction(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(at = @At("HEAD"), method = "updateResult", cancellable = true)
    private void enchantmentRestriction(CallbackInfo ci) {
        ItemStack item1 = this.input.getStack(0);
        ItemStack item2 = this.input.getStack(1);

        boolean isItem1Book = item1.isOf(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantmentNbt(item1).isEmpty();
        boolean isItem2Book = item2.isOf(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantmentNbt(item2).isEmpty();

        if ((item1.hasEnchantments() && isItem2Book) ||
                (isItem1Book && isItem2Book) ||
                (item1.hasEnchantments() && item2.hasEnchantments())) {
            ci.cancel();
        }
    }
}
