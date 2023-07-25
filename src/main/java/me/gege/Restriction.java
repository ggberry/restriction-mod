package me.gege;

import me.gege.block.ModBlocks;
import me.gege.block.entity.ModBlockEntities;
import me.gege.group.ModGroups;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Restriction implements ModInitializer {
	public static final String MOD_ID = "restriction";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean doRandomTP = false;
	public static final List<Item> BANNED_ITEMS = List.of(new Item[]{
			Items.TNT_MINECART,
			Items.RESPAWN_ANCHOR,
			Items.ENDER_CHEST,
			Items.TIPPED_ARROW});

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModGroups.registerModGroup();
		ModGroups.registerGroupItems();

		LOGGER.info("Initialized restrictions mod!");
	}
}