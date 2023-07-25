package me.gege.group;

import me.gege.Restriction;
import me.gege.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModGroups {
    public static final RegistryKey<ItemGroup> RESTRICTION_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Restriction.MOD_ID, "restrictionitems"));

    public static void registerModGroup() {
        Registry.register(Registries.ITEM_GROUP, RESTRICTION_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.CORE))
                .displayName(Text.translatable("restriction.group.restrictionitems"))
                .build());
    }

    public static void registerGroupItems() {
        ItemGroupEvents.modifyEntriesEvent(ModGroups.RESTRICTION_GROUP).register(itemGroup -> {
            itemGroup.add(ModBlocks.CORE);
        });
    }
}
