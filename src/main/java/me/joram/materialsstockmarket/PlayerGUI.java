package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerGUI {


    private int inventorySize;
    private final Inventory inventory;
    public ArrayList<ItemStack> materialList;


    public PlayerGUI(String title){
        inventorySize = 36;
        materialList = new ArrayList<ItemStack>();
        inventory = Bukkit.createInventory(null, inventorySize, title);
    }

    public void addItemToOverview(int loc, Material mat){
        ItemStack item = new ItemStack(mat);
        inventory.setItem(loc, item);
    }

    public void generateOverViewGUI() {
        for (int i = 8; i < 36; i++) {
            if(materialList.get(i - 8) == null) break;
            inventory.setItem(i, materialList.get(i - 8));
        }
    }



    public void createNewItemStock(int loc, Material mat){
        Inventory subInventory = Bukkit.createInventory(null, 27, "Overview " + mat.name());
        ItemStack subItem = new ItemStack(mat);
        subInventory.setItem(4, subItem);

        // Green Buy Button
        int amount = 1;
        for(int i = 0; i < 8; i++){
            ItemStack greenButton = new ItemStack(Material.GREEN_WOOL);
            ItemMeta greenButtonMeta = greenButton.getItemMeta();

            ItemStack redButton = new ItemStack(Material.RED_WOOL);
            ItemMeta redButtonMeta = redButton.getItemMeta();

            if(i > 0) amount = amount * 2;

            greenButtonMeta.setDisplayName("Buy " + amount);
            redButtonMeta.setDisplayName("Sell " + amount);

            subInventory.setItem((i+9), greenButton);
            subInventory.setItem((i+18), redButton);
        }
        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitItemMeta = exit.getItemMeta();
        exitItemMeta.setDisplayName("Back");


    }

    public boolean addMaterialToList(Material mat){
        if(materialList.size() > 27) return false;

        ItemStack newItem = new ItemStack(mat);
        materialList.add(newItem);
        return true;
    }




}
