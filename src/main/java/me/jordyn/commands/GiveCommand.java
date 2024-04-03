package me.jordyn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.jordyn.BlackHoleHoppers;

public class GiveCommand implements CommandExecutor{
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player player)){
            return true;
        }

        player.getInventory().addItem(BlackHoleHoppers.getPlugin().getHopper());

        return true;
    }

}
