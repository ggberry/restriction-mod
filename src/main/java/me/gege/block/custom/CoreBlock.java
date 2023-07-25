package me.gege.block.custom;

import me.gege.block.ModBlocks;
import me.gege.block.entity.ModBlockEntities;
import me.gege.block.entity.block.CoreBlockEntity;
import me.gege.util.CorePosData;
import me.gege.util.IEntityDataSaver;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;

public class CoreBlock extends BlockWithEntity implements BlockEntityProvider {
    public CoreBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /* BLOCK ENTITY */

    private boolean isValidTarget(LivingEntity player) {
        return player != null && player.isPlayer();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CoreBlockEntity && isValidTarget(placer)) {
            // Adding owner NBT to the block
            ((CoreBlockEntity) blockEntity).setOwner(placer.getUuidAsString());

            // Adding block coordinate NBT to the player
            int[] blockCord = {pos.getX(), pos.getY(), pos.getZ()};
            CorePosData.setCorePos(((IEntityDataSaver) placer), blockCord);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        Text message = null;
        if (blockEntity instanceof CoreBlockEntity && isValidTarget(player)) {
            try {
                PlayerEntity owner = world.getPlayerByUuid(UUID.fromString(((CoreBlockEntity) blockEntity).getOwner()));
                if (owner != null) {
                    if (player == owner) {
                        message = Text.literal("You are now vulnerable to capture.");
                    } else {
                        message = Text.literal("You've broken " + player.getName().getString() + "'s core! They are now vulnerable to capture.");
                    }

                    CorePosData.clearCorePos(((IEntityDataSaver) owner));
                }
            } catch (Exception e) {
                System.out.println(((CoreBlockEntity) blockEntity).getOwner());
            }
        }

        if (message != null) {
            player.sendMessage(message, true);
        }

        super.onBreak(world, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoreBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.CORE_ENTITY, CoreBlockEntity::tick);
    }
}
