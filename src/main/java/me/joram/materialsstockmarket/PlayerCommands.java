package me.joram.materialsstockmarket;

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

    public PlayerCommands(Main main){
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }




        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("mm")){
            if(args.length > 0) {
                if (args[0].equalsIgnoreCase("open")) {
                    player.sendMessage("You tried to open the inventory Overview");
                    PlayerGUI pGUI = new PlayerGUI(main);
                    Inventory overViewInv = pGUI.generateOverViewGUI();
                    player.openInventory(overViewInv);
                    player.sendMessage("You tried to open the inventory Overview");
                }
                if(args[0].equalsIgnoreCase("test")) {

                    String[] stringArray = new String[27];
                    List<String> stringList = Arrays.asList(stringArray);

                    stringList = main.getConfig().getStringList("items");

                    for(int i = 0; i < stringList.size(); i++){
                        player.sendMessage("Item: " + stringList.get(i));
                    }

                }
            }
        }
        return false;
    }

}
