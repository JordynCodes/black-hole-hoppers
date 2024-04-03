package me.jordyn.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveCommand implements CommandExecutor{
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player player)){
            return true;
        }

        ItemStack hopper = new ItemStack(Material.HOPPER);
        ItemMeta hopperMeta = hopper.getItemMeta();

        return true;
    }

}
