package me.joram.materialsstockmarket;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class PlayerCommands implements CommandExecutor {
    private Main main;

    public PlayerCommands(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("mm")) {
            if (!(player.hasPermission("MaterialsStockMarket.open"))) {
                player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " You do not have permission to use this command!");
                return false;
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("open")) {
                    PlayerGUI pGUI = new PlayerGUI(main);
                    Inventory overViewInv = pGUI.generateOverViewGUI();
                    player.openInventory(overViewInv);
                } else if (args[0].equalsIgnoreCase("test")) {
                    AutoPricing auto = new AutoPricing(main);
                    auto.runAutoPricing();

                } else if(args[0].equalsIgnoreCase("help")) {

                    player.sendMessage(ChatColor.AQUA + "Use /mm open to open the Market Overview!");
                }
                  else {
                    player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " This command does not exist!");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " This command does not exist!");
            }
        }
        return false;
    }
}

