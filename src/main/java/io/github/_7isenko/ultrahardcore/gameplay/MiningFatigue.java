package io.github._7isenko.ultrahardcore.gameplay;

import io.github._7isenko.ultrahardcore.UltraHardcore;
import io.github._7isenko.ultrahardcore.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MiningFatigue implements Listener {
    HashMap<Player, Integer> players = new HashMap<>();

    public MiningFatigue() {
        Bukkit.getScheduler().runTaskTimer(UltraHardcore.plugin, () -> {
            Iterator<Map.Entry<Player, Integer>> iterator = players.entrySet().iterator();
            while (iterator.hasNext()) {
                HashMap.Entry<Player, Integer> entry = iterator.next();
                int blocks = entry.getValue() - 1;
                if (blocks <= 0) {
                    iterator.remove();
                }
                entry.setValue(blocks);
            }
        }, 0, 60);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL)
            return;

        if (!players.containsKey(player)) {
            players.put(player, 1);
            return;
        }

        int value = players.get(player) + 1;
        players.put(player, value);

        if (value > 63) {
            if (player.getPotionEffect(PotionEffectType.SLOW_DIGGING) == null || player.getPotionEffect(PotionEffectType.SLOW_DIGGING).getAmplifier() <= 1)
                ChatUtils.sendTitle(player, "You're tired as hell", ChatColor.DARK_RED);
            player.addPotionEffect(fatigue(2));
            return;
        }

        if (value > 47) {
            if (player.getPotionEffect(PotionEffectType.SLOW_DIGGING) == null || player.getPotionEffect(PotionEffectType.SLOW_DIGGING).getAmplifier() == 0)
                ChatUtils.sendTitle(player, "You're too much tired", ChatColor.DARK_GRAY);
            player.addPotionEffect(fatigue(1));
            return;
        }

        if (value > 31) {
            if (player.getPotionEffect(PotionEffectType.SLOW_DIGGING) == null)
                ChatUtils.sendTitle(player, "You're tired", ChatColor.GRAY);
            player.addPotionEffect(fatigue(0));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSleep(TimeSkipEvent event) {
        if (event.getSkipReason() != TimeSkipEvent.SkipReason.NIGHT_SKIP)
            return;
        Bukkit.getWorlds().get(0).getPlayers().forEach((player) -> {
            players.remove(player);
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            ChatUtils.sendTitle(player, "You woke up well rested", ChatColor.GREEN);
        });
    }

    private PotionEffect fatigue(int amplifier) {
        return new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, amplifier);
    }
}
