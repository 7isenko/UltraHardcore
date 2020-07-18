package io.github._7isenko.ultrahardcore.mobs;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

public class MobTargetingRunnable extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getGameMode() != GameMode.SURVIVAL)
                return;
            player.getNearbyEntities(20, 20, 20).forEach(entity -> {
                if (entity instanceof Mob) {
                    Mob mob = (Mob) entity;
                    if (mob.getTarget() == null || mob.getTarget().getType() != EntityType.PLAYER)
                        mob.setTarget(player);
                }
            });
        });
    }
}