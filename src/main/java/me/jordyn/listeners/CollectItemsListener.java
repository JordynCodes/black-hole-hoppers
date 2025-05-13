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
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import me.jordyn.configs.HopperData;
import me.jordyn.BlackHoleHoppers;

public class CollectItemsListener implements Listener {

    @EventHandler
    public void onHopperSearch(HopperInventorySearchEvent e){

        // check if hopper is full
        Hopper hopper = (Hopper) e.getBlock().getState();
        if (BlackHoleHoppers.getPlugin().isHopperFull(hopper))
            return;

        // check if the hopper config is empty
        ConfigurationSection hoppersSection = HopperData.getHopperDataFile().getConfigurationSection("hoppers");
        if (hoppersSection == null) 
            return;

        // check if the hopper is a vanilla or plugin hopper and return if vanilla
        Location hopperLocation = hopper.getBlock().getLocation();
        if (!(BlackHoleHoppers.getPlugin().isBlackHoleHopper(hopperLocation)))
            return;

        // add each item in range of hopper to hopper inventory
        Double radius = 4.0;
        BoundingBox box = hopper.getBlock().getBoundingBox().expand(radius);
        Collection<Entity> nearbyEntities = hopper.getWorld().getNearbyEntities(box);

        for (Entity entity : nearbyEntities){
            if (!(entity instanceof Item item))
                continue;
        
            ItemStack itemStack = item.getItemStack().clone();
        
            if (!BlackHoleHoppers.getPlugin().canHopperAcceptItem(hopper, itemStack))
                continue;
        
            // Try to insert the item
            Map<Integer, ItemStack> leftovers = hopper.getInventory().addItem(itemStack);
        
            if (leftovers.isEmpty()) {
                item.remove();
            } else {
                ItemStack remaining = leftovers.values().iterator().next();
                item.setItemStack(remaining);
            }
        
            return;
        }
    }
}
