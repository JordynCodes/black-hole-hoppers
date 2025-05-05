package me.jordyn.listeners;

import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.util.BoundingBox;

public class CollectItemsListener implements Listener {

    @EventHandler
    public void onHopperSearch(HopperInventorySearchEvent e){

        Double range = 4.0;
        Hopper hopper = (Hopper) e.getBlock().getState();
        BoundingBox box = hopper.getBlock().getBoundingBox().expand(range);
        Collection<Entity> nearbyEntities = hopper.getWorld().getNearbyEntities(box);

        for (Entity entity : nearbyEntities){

            if (entity instanceof Item item){

                Bukkit.getServer().broadcastMessage("nearby entity " + item);
                item.remove();
                hopper.getInventory().addItem(item.getItemStack());

            }
        }
    }
}
