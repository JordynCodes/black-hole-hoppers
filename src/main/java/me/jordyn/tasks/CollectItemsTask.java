package me.jordyn.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.jordyn.configs.HopperData;

public class CollectItemsTask extends BukkitRunnable{

    Plugin plugin;

    public CollectItemsTask(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {

        ConfigurationSection hoppersSection = HopperData.getHopperDataFile().getConfigurationSection("hoppers");
        if (hoppersSection == null) {
            return;
        }
    
        Double range = 4.0;
        for (String key : hoppersSection.getKeys(false)){
            Location hopperLocation = hoppersSection.getLocation(key);
            Collection<Entity> nearbyEntities = hopperLocation.getWorld().getNearbyEntities(hopperLocation, range, range, range);
            List<Item> items = filterItems(nearbyEntities);
            for(Item item : items){
                if(item.getLocation().distanceSquared(hopperLocation) <= range * range){
                    item.remove();
                    Hopper hopper = (Hopper) hopperLocation.getBlock().getState();
                    ItemStack droppedItem = item.getItemStack();
                    hopper.getInventory().addItem(droppedItem);
                    return;
                }
            }
        }

    }

    public static List<Item> filterItems(Collection<Entity> entities) {
        List<Item> items = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Item) {
                items.add((Item) entity);
            }
        }
        return items;
    }

}
