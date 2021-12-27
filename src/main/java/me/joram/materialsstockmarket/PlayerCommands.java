package me.joram.materialsstockmarket;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }


        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("mm")){
            if(args.length > 0) {
                if (args[0].equalsIgnoreCase("open")) {
                    player.sendMessage("You tried to open the inventory Overview");
                    PlayerGUI pGUI = new PlayerGUI();
                    Inventory overViewInv = pGUI.generateOverViewGUI();
                    player.openInventory(overViewInv);
                    player.sendMessage("You tried to open the inventory Overview");
                }
                if(args[0].equalsIgnoreCase("test")){
                    player.sendMessage("You tried to open the inventory Overview");
                    PlayerGUI pGUI = new PlayerGUI();
                    Inventory inv = pGUI.createNewItemStock(Material.DIRT);
                    player.openInventory(inv);
                    player.sendMessage("You tried to open the inventory Overview");
                }
            }
        }
        return false;
    }

}
