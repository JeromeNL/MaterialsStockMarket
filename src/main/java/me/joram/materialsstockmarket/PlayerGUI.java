package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerGUI {

    private int inventorySize;
    private final String title;
    public ItemStack[] materialList;
    private DataController dc;
    private MaterialsStockMarket main;

    public PlayerGUI(){
        inventorySize = 36;
        materialList = new ItemStack[27];
        title = "Market Overview";

        addAllItems();

    }

    public Inventory generateOverViewGUI() {
        Inventory newInv = Bukkit.createInventory(null, inventorySize, "Market Overview");

        // Quit Button
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitItemMeta = exit.getItemMeta();
        exitItemMeta.setDisplayName("Quit");
        exit.setItemMeta(exitItemMeta);
        newInv.setItem(0, exit);

        // View Item
        ItemStack paperItem = new ItemStack(Material.PAPER);
        ItemMeta exampleItemMeta = paperItem.getItemMeta();
        exitItemMeta.setDisplayName("Welcome to the Market Overview");
        paperItem.setItemMeta(exampleItemMeta);
        newInv.setItem(4, paperItem);

        for (int i = 9; i < 36; i++) {
                if((materialList[i -  9] != null)) {
                    ItemStack newItem = materialList[i - 9];
                    newInv.setItem(i, newItem);
                }
        }
        return newInv;
    }

    public Inventory createNewItemStock(Material mat){
        Inventory subInventory = Bukkit.createInventory(null, 27, "Overview " + mat.name());
        ItemStack subItem = new ItemStack(mat);
        subInventory.setItem(4, subItem);

        // Green Buy Button
        int amount = 1;
        for(int i = 0; i < 9; i++){
            ItemStack greenButton = new ItemStack(Material.GREEN_WOOL);
            ItemMeta greenButtonMeta = greenButton.getItemMeta();

            ItemStack redButton = new ItemStack(Material.RED_WOOL);
            ItemMeta redButtonMeta = redButton.getItemMeta();

            if(i > 0) amount = amount * 2;

            greenButtonMeta.setDisplayName("Buy " + amount);
            redButtonMeta.setDisplayName("Sell " + amount);
            greenButton.setItemMeta(greenButtonMeta);
            redButton.setItemMeta(redButtonMeta);

            subInventory.setItem((i+9), greenButton);
            subInventory.setItem((i+18), redButton);
        }
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitItemMeta = exit.getItemMeta();
        exitItemMeta.setDisplayName("Back");
        exit.setItemMeta(exitItemMeta);
        subInventory.setItem(0, exit);

        return subInventory;

    }

    // Fills array with data
    public void addItemToOverview(Material mat){
        ItemStack item = new ItemStack(mat);

        boolean isFilled = true;
        int i = 0;
        while(isFilled){
            if(i > 27) break;
            if(materialList[i] == null){
                isFilled = false;
                materialList[i] = item;
            }
            i++;
        }
    }

    public void addAllItems(){
        addItemToOverview(Material.DIAMOND);
        addItemToOverview(Material.GOLD_INGOT);
        addItemToOverview(Material.IRON_INGOT);
        addItemToOverview(Material.EMERALD);
        addItemToOverview(Material.SAND);
        addItemToOverview(Material.DIRT);
        addItemToOverview(Material.COBBLESTONE);
        addItemToOverview(Material.GRAVEL);
        addItemToOverview(Material.OAK_LOG);
        addItemToOverview(Material.DARK_OAK_LOG);
        addItemToOverview(Material.SAND);
        addItemToOverview(Material.DIAMOND_CHESTPLATE);
    }
}











