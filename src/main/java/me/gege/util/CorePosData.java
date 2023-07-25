package me.gege.util;

import net.minecraft.nbt.NbtCompound;

public class CorePosData {
    public static int[] setCorePos(IEntityDataSaver player, int[] blockCordArray) {
        NbtCompound nbt = player.getPersistentData();

        nbt.putIntArray("corePos", blockCordArray);
        return blockCordArray;
    }

    public static int[] clearCorePos(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        int[] emptyCord = {};
        nbt.putIntArray("corePos", emptyCord);
        return emptyCord;
    }
}
