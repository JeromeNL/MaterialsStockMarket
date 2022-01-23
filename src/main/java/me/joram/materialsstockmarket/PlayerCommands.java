package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class PlayerCommands implements CommandExecutor {
    private Main main;
    private DataController dataController;

    public PlayerCommands(Main main) {
        this.main = main;
        dataController = new DataController(main);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("mm")) {
            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("open")) {
                    if (!(player.hasPermission("MaterialsStockMarket.open"))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " You do not have permission to use this command!");
                        return false;
                    }
                    PlayerGUI pGUI = new PlayerGUI(main);
                    Inventory overViewInv = pGUI.generateOverViewGUI();
                    player.openInventory(overViewInv);

                } else if (args[0].equalsIgnoreCase("adddays")) {
                    if (!(player.hasPermission("MaterialsStockMarket.adddays"))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " You do not have permission to use this command!");
                        return false;
                    }
                    if (!(args.length == 2)) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "Use: /mm adddays <amountOfDays>");
                    } else {
                        try {
                            int amount = Integer.valueOf(args[1]);

                            for (int i = 0; i < amount; i++) {
                                AutoPricing auto = new AutoPricing(main);
                                auto.runAutoPricing();
                            }
                            player.sendMessage(ChatColor.DARK_GREEN + "Added " + ChatColor.GREEN + amount + ChatColor.DARK_GREEN + " days to the stockmarket!");
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.DARK_RED + "ERROR!");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("depression")) {
                    if (!(player.hasPermission("MaterialsStockMarket.depression"))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " You do not have permission to use this command!");
                        return false;
                    }
                    int percentage = 1;
                    if (args.length != 2) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "Use: /mm depression <percentage without decimals/- sign>");
                    } else {
                        try {
                            percentage = Integer.valueOf(args[1]);
                            if ((percentage > 0 && percentage < 100)) {
                                AutoPricing auto = new AutoPricing(main);
                                auto.changeAllPrices(percentage * -1);
                                player.sendMessage(ChatColor.DARK_RED + "Depression added of: -" + ChatColor.RED + percentage + ChatColor.DARK_RED + "%");
                                Bukkit.broadcastMessage("Due to poor economic conditions, the stock market has fallen by " + percentage + "%");
                            } else {
                                player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "Use a value between 1 and 99 percent!");
                            }
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "Use a value between 1 and 99!");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("help")) {

                    if (!(player.hasPermission("MaterialsStockMarket.help"))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " You do not have permission to use this command!");
                        return false;
                    }
                    player.sendMessage(ChatColor.DARK_PURPLE + "MaterialsStockMarket Help");
                    player.sendMessage(ChatColor.DARK_PURPLE + "--------------==()==--------------");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Use /mm open to open the Market Overview!");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Use /mm adddays <amount> to add new days to the stockmarket!");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Use /mm depression <percentage> to lower all stocks");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Use /mm changebuyprice <item> <amount> to change the buy price of an item.");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Use /mm changesellprice <item> <amount> to change the sell price of an item.");
                    player.sendMessage(ChatColor.DARK_PURPLE + "--------------==()==--------------");
                }
                // /changebuyprice ITEM NEWPRICE

                else if (args[0].equalsIgnoreCase("changebuyprice")) {
                    if (!(player.hasPermission("MaterialsStockMarket.changebuyprice"))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " You do not have permission to use this command!");
                        return false;
                    }

                    if (args.length != 3) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " Use: /mm changebuyprice <item> <new price>");
                        return false;
                    }

                    if (!(dataController.itemExists(args[1]))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " This item is not part of the stock market!");
                        return false;
                    }

                    Double amount = 0.0;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "This is not a valid amount!");
                    }

                    if (!(amount > 0 && amount < 100000)) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "Use a amount between 1 and 100.000");
                        return false;
                    }
                    dataController.addToPrices(Material.valueOf(args[1].toUpperCase()), amount, dataController.getSellPrice(Material.valueOf(args[1].toUpperCase()), 1));
                    player.sendMessage(ChatColor.GREEN + "You have changed the buy price of " + args[1] + " to " + amount);
                } else if (args[0].equalsIgnoreCase("changesellprice")) {
                    if (!(player.hasPermission("MaterialsStockMarket.changesellprice"))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " You do not have permission to use this command!");
                        return false;
                    }

                    if (args.length != 3) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " Use: /mm changesellprice <item> <new price>");
                        return false;
                    }

                    if (!(dataController.itemExists(args[1]))) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " This item is not part of the stock market!");
                        return false;
                    }

                    Double amount = 0.0;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "This is not a valid amount!");
                    }

                    if (!(amount > 0 && amount < 100000)) {
                        player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "Use a amount between 1 and 100.000");
                        return false;
                    }
                    dataController.addToPrices(Material.valueOf(args[1].toUpperCase()), dataController.getBuyPrice(Material.valueOf(args[1].toUpperCase()), 1), amount);
                    player.sendMessage(ChatColor.GREEN + "You have changed the sell price of " + args[1] + " to " + amount);
                }
                else{
                    player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " This command does not exist!");
                }

            } else {
                player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " This command does not exist!");
            }
        } else {
            player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + " This command does not exist!");
        }
        return false;
    }
}

