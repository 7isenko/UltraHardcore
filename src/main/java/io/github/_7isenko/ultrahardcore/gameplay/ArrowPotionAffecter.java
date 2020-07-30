package io.github._7isenko.ultrahardcore.gameplay;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArrowPotionAffecter implements Listener {
    private static List<PotionEffectType> goodPotions = Arrays.asList(PotionEffectType.SPEED, PotionEffectType.ABSORPTION, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INCREASE_DAMAGE, PotionEffectType.INVISIBILITY, PotionEffectType.REGENERATION);
    private static List<PotionEffectType> badPotions = Arrays.asList(PotionEffectType.WITHER, PotionEffectType.WEAKNESS, PotionEffectType.SLOW_DIGGING, PotionEffectType.POISON, PotionEffectType.LEVITATION, PotionEffectType.GLOWING, PotionEffectType.CONFUSION, PotionEffectType.BLINDNESS, PotionEffectType.BAD_OMEN, PotionEffectType.HUNGER);


    @EventHandler(ignoreCancelled = true)
    public void onShot(EntityShootBowEvent event) {
        if (event.getProjectile() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getProjectile();
            if (event.getEntity() instanceof Player)
                addGoodPotionEffect(arrow);
            else addBadPotionEffect(arrow);
        }
    }

    private static void addGoodPotionEffect(Arrow a) {
        a.addCustomEffect(getRandomEffect(getGoodPotionEffectType()), false);
    }

    private static void addBadPotionEffect(Arrow a) {
        a.addCustomEffect(getRandomEffect(getBadPotionEffectType()), false);
    }

    private static PotionEffect getRandomEffect(PotionEffectType type) {
        Random r = new Random();
        return type.createEffect(r.nextInt(1200), r.nextInt(2));
    }

    public static PotionEffectType getGoodPotionEffectType() {
        Random r = new Random();
        return goodPotions.get(r.nextInt(goodPotions.size()));
    }

    public static PotionEffectType getBadPotionEffectType() {
        Random r = new Random();
        return badPotions.get(r.nextInt(badPotions.size()));
    }
}
