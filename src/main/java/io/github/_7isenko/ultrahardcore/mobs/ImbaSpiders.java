package io.github._7isenko.ultrahardcore.mobs;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ImbaSpiders implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onBite(EntityDamageByEntityEvent event) {
        if (isSpider(event.getDamager()) && event.getEntity() instanceof LivingEntity)
            ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));

    }
    @EventHandler(ignoreCancelled = true)
    public void onSpawn(CreatureSpawnEvent event) {
        if (isSpider(event.getEntity()))
            event.getEntity().addPassenger(event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.SKELETON));
    }

    private boolean isSpider(Entity entity) {
        return entity instanceof Spider && !(entity instanceof CaveSpider);
    }

}
