package io.github._7isenko.ultrahardcore.gameplay;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class PlayerDamageListener implements Listener {
    Random r = new Random();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        Player player = (Player) event.getEntity();
        if (r.nextDouble() > 0.5D) {
            dropRandomItemFromHotbar(player);
        }
        if (r.nextDouble() > 0.7D) {
            dropRandomItemFromInventory(player);
        }
    }

    private void dropRandomItemFromHotbar(Player p) {
        HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
        for (int i = 0; i < 9; i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (item != null)
                items.put(i, item);
        }
        if (items.size() == 0) return;

        int number = (Integer)items.keySet().toArray()[r.nextInt(items.keySet().size())];
        p.getWorld().dropItemNaturally(p.getLocation(), items.get(number)).setPickupDelay(45);
        p.getInventory().setItem(number, null);
    }

    // I duplicated this code cuz I don't want to make the dropRandomItemFromInventoryInSlotRange method.
    private void dropRandomItemFromInventory(Player p) {
        HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
        for (int i = 9; i < 41; i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (item != null)
                items.put(i, item);
        }
        if (items.size() == 0) return;

        int number = (Integer)items.keySet().toArray()[r.nextInt(items.keySet().size())];
        p.getWorld().dropItemNaturally(p.getLocation(), items.get(number)).setPickupDelay(45);
        p.getInventory().setItem(number, null);
    }
}
