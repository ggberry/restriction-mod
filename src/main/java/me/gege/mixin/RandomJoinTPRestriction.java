package me.gege.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.netty.util.internal.ThreadLocalRandom;
import me.gege.Restriction;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.util.ArrayList;

@Mixin(PlayerManager.class)
public abstract class RandomJoinTPRestriction {
    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    public void randomJoinTpRestriction(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) throws IOException {
        if (Restriction.doRandomTP) {
            JsonParser jsonParser = new JsonParser();

            try {
                FileReader fileReader = new FileReader("oldPlayers.json");
            } catch (IOException ignored) {
                File fileCreator = new File("oldPlayers.json");
            }

            FileReader fileReader = new FileReader("oldPlayers.json");

            Object obj = jsonParser.parse(fileReader);

            JsonArray playersList = (JsonArray) obj;
            ArrayList<String> oldPlayers = new ArrayList<>();

            for (JsonElement playerInfo : playersList) {
                oldPlayers.add(playerInfo.getAsString());
            }

            if (!(oldPlayers.contains(player.getName().getString()))) {
                oldPlayers.add(player.getName().getString());
                int min = 0;
                int max = 1000;

                int randomX = ThreadLocalRandom.current().nextInt(min, max + 1);
                int randomZ = ThreadLocalRandom.current().nextInt(min, max + 1);
                int groundY = -60;

                int index = 320;

                while (index > -60) {
                    BlockState current = player.getWorld().getBlockState(new BlockPos(randomX, index, randomZ));
                    BlockState above = player.getWorld().getBlockState(new BlockPos(randomX, index + 1, randomZ));
                    if (!(current.getBlock() == Blocks.AIR) && above.getBlock() == Blocks.AIR) {
                        groundY = index + 2;
                        break;
                    }

                    index -= 1;
                }

                player.teleport(randomX, groundY, randomZ);
                player.setSpawnPoint(World.OVERWORLD, new BlockPos(randomX, groundY, randomZ), 0, true, true);
                FileWriter fileWriter = new FileWriter("oldPlayers.json", false);

                fileWriter.write(String.valueOf(oldPlayers).replace("[", "[\"").replace("]", "\"]").replace(", ", "\", \""));
                fileWriter.close();
            }
        }
    }
}
