package io.github._7isenko.ultrahardcore.gameplay;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FireRun implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        if (event.getPlayer().getFireTicks() <= 0)
            return;
        event.getPlayer().damage(1d);
    }
}
