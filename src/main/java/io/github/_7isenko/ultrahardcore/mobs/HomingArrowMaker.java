package io.github._7isenko.ultrahardcore.mobs;

import io.github._7isenko.ultrahardcore.UltraHardcore;
import io.github._7isenko.ultrahardcore.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class HomingArrowMaker implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onShot(EntityShootBowEvent event) {
        final AbstractArrow arrow = (AbstractArrow) event.getProjectile();
        if (event.getEntity() instanceof Player)
            return;
        new HomingProjectile(arrow).runTaskTimer(UltraHardcore.plugin, 5, 3);
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectile(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player)
            return;
        new HomingProjectile(event.getEntity()).runTaskTimer(UltraHardcore.plugin, 5, 3);
    }

    // Explosions are here too
    @EventHandler(ignoreCancelled = true)
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() == event.getHitEntity()) {
            if (event.getEntity() instanceof AbstractArrow) {
                ((AbstractArrow) event.getEntity()).setDamage(100);
                if (event.getEntity().getShooter() instanceof Player)
                    ChatUtils.sendTitle((Player) event.getEntity().getShooter(), "HEADSHOT", ChatColor.RED);
            }
        }
        if (event.getEntity() instanceof AbstractArrow)
            event.getEntity().remove();
        if (!(event.getEntity().getShooter() instanceof Player) && event.getHitBlock() != null) {
            Location location = event.getHitBlock().getLocation();
            location.getWorld().createExplosion(location, 1.5F, false, true);
        }
    }

    private static class HomingProjectile extends BukkitRunnable {
        Projectile projectile;

        public HomingProjectile(Projectile projectile) {
            this.projectile = projectile;
        }

        public void run() {
            if (projectile.isOnGround() || projectile.isDead()) {
                cancel();
                return;
            }
            I_hate_this_nesting_level:
            for (double i = 0.5; i < 12; i += 0.5) {
                for (Entity e : projectile.getNearbyEntities(i, 3, i)) {
                    if (e.getType() == EntityType.PLAYER && ((Player) e).getGameMode() == GameMode.SURVIVAL) {
                        Location from = projectile.getLocation();
                        Location to = e.getLocation().add(0, 0.6, 0);
                        Vector direction = to.toVector().subtract(from.toVector()).normalize();
                        projectile.setVelocity(direction);
                        break I_hate_this_nesting_level;
                    }
                }
            }
        }
    }
}
