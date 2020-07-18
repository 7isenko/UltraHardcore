package io.github._7isenko.ultrahardcore;

import io.github._7isenko.ultrahardcore.gameplay.*;
import io.github._7isenko.ultrahardcore.mobs.HomingArrowMaker;
import io.github._7isenko.ultrahardcore.mobs.ImbaSpiders;
import io.github._7isenko.ultrahardcore.mobs.MobTargetingRunnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraHardcore extends JavaPlugin {
    // How to build: Maven/UltraHardcore/Lifecycle/package
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Homing enemy arrows
        getServer().getPluginManager().registerEvents(new HomingArrowMaker(), plugin);

        // Arrows with potions
        getServer().getPluginManager().registerEvents(new ArrowPotionAffecter(), plugin);

        // Drop items on damage
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), plugin);

        // No more infinite water from bucket
        getServer().getPluginManager().registerEvents(new AntiWaterBucketPlacer(), plugin);

        // Spiders are more deadly
        getServer().getPluginManager().registerEvents(new ImbaSpiders(), plugin);

        // Mining blocks is too... tiring process...
        getServer().getPluginManager().registerEvents(new MiningFatigue(), plugin);

        // Get more damage when you move
        getServer().getPluginManager().registerEvents(new FireRun(), plugin);

        new MobTargetingRunnable().runTaskTimer(plugin, 0, 40);
    }

    @Override
    public void onDisable() {
    }
}