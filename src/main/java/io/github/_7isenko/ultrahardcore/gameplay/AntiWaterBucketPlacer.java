package io.github._7isenko.ultrahardcore.gameplay;

import io.github._7isenko.ultrahardcore.UltraHardcore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class AntiWaterBucketPlacer implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEmpty(final PlayerBucketEmptyEvent event) {
        final Block block = event.getBlock();
        int time = 10;
        if (event.getBucket() == Material.LAVA_BUCKET)
            time = 40;
        Bukkit.getScheduler().runTaskLater(UltraHardcore.plugin, () -> {
            if (block.getBlockData() instanceof Levelled) {
                Levelled data = ((Levelled) block.getBlockData().clone());
                data.setLevel(8);
                block.setBlockData(data);
            } else if (block.getBlockData() instanceof Waterlogged) {
                Waterlogged data = ((Waterlogged) block.getBlockData().clone());
                data.setWaterlogged(false);
                block.setBlockData(data);
            }
        }, time);
    }
}
