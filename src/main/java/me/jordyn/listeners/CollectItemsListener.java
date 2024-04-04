package me.jordyn.listeners;

import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import me.jordyn.configs.HopperData;

public class CollectItemsListener implements Listener {

    @EventHandler
    public void onItemDrop(ItemSpawnEvent e){

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
                e.setCancelled(true);
                Hopper hopper = (Hopper) hopperLocation.getBlock().getState();
                ItemStack droppedItem = e.getEntity().getItemStack();
                hopper.getInventory().addItem(droppedItem);
                return;
            }
        }

    }

}
