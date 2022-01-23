package me.joram.materialsstockmarket;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PlayerGUI {

    private final int inventorySize;
    private final String title;
    private ItemStack[] materialList;
    private Main main;
    private DataController dataController;


    public PlayerGUI(Main main) {
        title = "Market Overview";
        this.main = main;
        dataController = new DataController(main);
        inventorySize = (dataController.getAmountOfSpaces());
        materialList = new ItemStack[inventorySize];
        addAllItems();
    }

    public Inventory generateOverViewGUI() {
        Inventory newInv = Bukkit.createInventory(null, inventorySize, "Market Overview");

        // Quit Button
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitItemMeta = exit.getItemMeta();
        exitItemMeta.setDisplayName(ChatColor.DARK_RED + "Quit");
        exit.setItemMeta(exitItemMeta);
        newInv.setItem(0, exit);

        // View Item
        ItemStack paperItem = new ItemStack(Material.PAPER);
        ItemMeta exampleItemMeta = paperItem.getItemMeta();
        exampleItemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Market Overview");

        ArrayList<String> loreText = new ArrayList<String>();
        loreText.add(ChatColor.LIGHT_PURPLE + "Welcome to the Market Overview!");
        loreText.add(ChatColor.LIGHT_PURPLE + "Prices can change any moment.");

        exampleItemMeta.setLore(loreText);
        paperItem.setItemMeta(exampleItemMeta);
        newInv.setItem(4, paperItem);

        for (int i = 9; i < inventorySize; i++) {
            if ((materialList[i - 9] != null)) {
                ItemStack newItem = materialList[i - 9];
                newInv.setItem(i, newItem);
            }
        }
        return newInv;
    }

    public Inventory createNewItemStock(Material mat) {
        Inventory subInventory = Bukkit.createInventory(null, 27, "Overview " + mat.name());
        ItemStack subItem = new ItemStack(mat);
        ItemMeta subItemMeta = subItem.getItemMeta();
        subItemMeta.setDisplayName(ChatColor.DARK_PURPLE + subItem.getType().name());
        subItem.setItemMeta(subItemMeta);
        subInventory.setItem(4, subItem);

        // Green Buy Button
        int amount = 1;
        for (int i = 0; i < 9; i++) {
            ItemStack greenButton = new ItemStack(Material.GREEN_WOOL);
            ItemMeta greenButtonMeta = greenButton.getItemMeta();

            ItemStack redButton = new ItemStack(Material.RED_WOOL);
            ItemMeta redButtonMeta = redButton.getItemMeta();

            if (i > 0) amount = amount * 2;

            Double buyPrice = dataController.getBuyPrice(mat, amount);
            greenButtonMeta.setDisplayName(ChatColor.DARK_GREEN + "Buy " + ChatColor.GREEN + amount + "x" + ChatColor.DARK_GREEN + " for: $" + ChatColor.GREEN + buyPrice);

            Double sellPrice = dataController.getSellPrice(mat, amount);
            redButtonMeta.setDisplayName(ChatColor.DARK_RED + "Sell " + ChatColor.RED + amount + "x" + ChatColor.DARK_RED + " for: $" + ChatColor.RED + sellPrice);

            greenButton.setItemMeta(greenButtonMeta);
            redButton.setItemMeta(redButtonMeta);

            subInventory.setItem((i + 9), greenButton);
            subInventory.setItem((i + 18), redButton);
        }

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitItemMeta = exit.getItemMeta();
        exitItemMeta.setDisplayName(ChatColor.DARK_RED + "Back");
        exit.setItemMeta(exitItemMeta);
        subInventory.setItem(0, exit);

        return subInventory;
    }

    public void addAllItems() {
        List<String> stringList = dataController.getAllItems(inventorySize);

        for (int i = 0; i < stringList.size(); i++) {
            ItemStack newItem = new ItemStack(Material.valueOf(stringList.get(i)));
            materialList[i] = newItem;
        }
    }
}













