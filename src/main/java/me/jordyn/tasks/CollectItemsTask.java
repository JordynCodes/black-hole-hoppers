package me.jordyn.tasks;

import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CollectItemsTask extends BukkitRunnable{

    Plugin plugin;

    public CollectItemsTask(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {

    }

    public int numEmptySlots(Hopper hopper){
        Inventory hopperInventory = hopper.getInventory();
        
        int emptySlots = 0;
        for (ItemStack item : hopperInventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                emptySlots++;
            }
        }
        
        return emptySlots;       
    }

}
