package me.joram.materialsstockmarket;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BuySell {
    private Double balance;

    public BuySell() {
        balance = 100.0;
    }

    public boolean buyItem(Material mat, int amount, HumanEntity human) {
        // BALANCE CHECK + REMOVE

        Player player = (Player) human;
        if (getFreeSpaceForItems(mat, player) < amount) {
            player.sendMessage(ChatColor.DARK_RED + "Error! " + ChatColor.RED + "You do not have enough space left in your inventory to buy this amount of items!");
            return false;
        } else {
            Inventory playerInv = player.getInventory();
            ItemStack newItem = new ItemStack(mat, amount);
            playerInv.addItem(newItem);
            player.sendMessage(ChatColor.GREEN + "You have just bought " + amount + "x " + mat.name() + " from the market!");
            return true;
        }

    }

    public boolean sellItem(Material mat, int amount, HumanEntity human){

        Player player = (Player) human;
        ItemStack sellItem = new ItemStack(mat);
        if(player.getInventory().containsAtLeast(sellItem, amount) == false){
            player.sendMessage(ChatColor.DARK_RED + "!ERROR " + ChatColor.RED + "You do not have enough items of this product!");
            return false;
        }

        player.sendMessage("balance: " + balance);
        player.sendMessage("Material: " + mat);
        player.sendMessage("Player: " + player);
        player.sendMessage("amount: " + amount);
        player.getInventory().removeItem(new ItemStack(mat, amount));

        // ADD BALANCE TO ACCOUNT
            return true;
    }

    public void changePrice(){

    }

    // Calculates the free space in Players inventory for an item
    public int getFreeSpaceForItems(Material mat, Player player){
        int freeSpace = 0;

        for(ItemStack i : player.getInventory().getStorageContents()){
            if(i == null){
                freeSpace = freeSpace + mat.getMaxStackSize();
                player.sendMessage("FreeSpace++" + freeSpace);
            }
            else if(i.getType() == mat) {
                freeSpace = freeSpace + (mat.getMaxStackSize() - i.getAmount());
                player.sendMessage("FreeSpace++ " + freeSpace);
            }
        }
        return freeSpace;
    }
}
