package me.jordyn.listeners;

import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

import me.jordyn.configs.HopperData;

public class ColllectItemsListener implements Listener {

    @EventHandler
    public void onItemDrop(ItemSpawnEvent e){

        System.out.println("event triggered");

        Location itemLocation = e.getLocation();

        ConfigurationSection hoppersSection = HopperData.getHopperDataFile().getConfigurationSection("hoppers");
        if (hoppersSection == null) {
            return;
        }

        Integer range = 4;

        for (String key : hoppersSection.getKeys(false)){
            Location hopperLocation = hoppersSection.getLocation(key);
            if(itemLocation.distanceSquared(hopperLocation) <= range * range){
                System.out.println("this item is in range of the hopper at " + hopperLocation);
                Hopper hopper = (Hopper) hopperLocation.getBlock().getState();
                return;
            }
        }

    }
    
}
