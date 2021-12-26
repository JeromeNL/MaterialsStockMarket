package me.joram.materialsstockmarket;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuySell {
    private Double balance;

    public BuySell(){
        balance = 100.0;
    }

    public boolean buyItem(Material mat, int amount, HumanEntity human){
        // remove balance
        Player player = (Player) human;



           player.sendMessage("=============");
           player.sendMessage("balance: " + balance);
           player.sendMessage("Material: " + mat);
           player.sendMessage("Player: " + player);
           player.sendMessage("amount: " + amount);
           player.sendMessage("--------------");

           Inventory playerInv = player.getInventory();
           ItemStack newItem = new ItemStack(mat, amount);
           playerInv.addItem(newItem);
           return true;
       }


    public void sellItem(Material mat, int amount, HumanEntity human){
        // add balance
        Player player = (Player) human;


        player.sendMessage("balance: " + balance);
        player.sendMessage("Material: " + mat);
        player.sendMessage("Player: " + player);
        player.sendMessage("amount: " + amount);
        player.getInventory().removeItem(new ItemStack(mat, amount));



    }

    public void changePrice(){

    }
}
