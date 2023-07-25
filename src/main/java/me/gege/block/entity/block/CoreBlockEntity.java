package me.gege.block.entity.block;

import me.gege.block.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoreBlockEntity extends BlockEntity {
    private String owner = "";
    public CoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CORE_ENTITY, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("core.owner", owner);
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        owner = nbt.getString("core.owner");
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String newOwner) {
        owner = newOwner;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CoreBlockEntity entity) {
    }

}
