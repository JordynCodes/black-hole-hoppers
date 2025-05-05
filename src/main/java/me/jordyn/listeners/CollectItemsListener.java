package me.jordyn.listeners;

import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.util.BoundingBox;

import me.jordyn.configs.HopperData;
import me.jordyn.BlackHoleHoppers;

public class CollectItemsListener implements Listener {

    @EventHandler
    public void onHopperSearch(HopperInventorySearchEvent e){

        ConfigurationSection hoppersSection = HopperData.getHopperDataFile().getConfigurationSection("hoppers");
        if (hoppersSection == null) {
            return;
        }

        Hopper hopper = (Hopper) e.getBlock().getState();
        Location hopperLocation = hopper.getBlock().getLocation();
        if (!(BlackHoleHoppers.getPlugin().isBlackHoleHopper(hopperLocation))){
            return;
        }

        Double range = 4.0;
        BoundingBox box = hopper.getBlock().getBoundingBox().expand(range);
        Collection<Entity> nearbyEntities = hopper.getWorld().getNearbyEntities(box);

        for (Entity entity : nearbyEntities){

            if (!(entity instanceof Item item)){
                continue;
            }

            Bukkit.getServer().broadcastMessage("nearby entity " + item);
            item.remove();
            hopper.getInventory().addItem(item.getItemStack());
            return;
            
        }
    }
}
