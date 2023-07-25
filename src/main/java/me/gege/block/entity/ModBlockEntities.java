package me.gege.block.entity;

import me.gege.Restriction;
import me.gege.block.ModBlocks;
import me.gege.block.entity.block.CoreBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<CoreBlockEntity> CORE_ENTITY;

    public static void registerBlockEntities() {
        CORE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Restriction.MOD_ID, "core"),
                FabricBlockEntityTypeBuilder.create(CoreBlockEntity::new , ModBlocks.CORE).build(null));
    }
}
