package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private ItemStack clickedItem;
    private Inventory mainmenuInv;

    public PlayerListener() {


    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent e) {

        // Cancel pick Item
        if ((e.getView().getTitle().equalsIgnoreCase("Market Overview"))) {
            if (e.getCurrentItem() == null)
                return;
            if (e.getCurrentItem().getItemMeta() == null)
                return;
            if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
                return;

            e.setCancelled(true);

            // Decide click Action
            if (e.getSlot() == 0)
                e.getWhoClicked().closeInventory();
            else if (e.getSlot() >= 9 && e.getSlot() <= 35) {
                PlayerGUI pGUI = new PlayerGUI();
                Inventory inv = pGUI.createNewItemStock(e.getCurrentItem().getType());
                e.getWhoClicked().openInventory(inv);
                clickedItem = e.getCurrentItem();
                mainmenuInv = e.getInventory();
            }
        } else if ((e.getView().getTitle().equalsIgnoreCase("Overview " + clickedItem.getType()))) {
            if (e.getCurrentItem() == null)
                return;
            if (e.getCurrentItem().getItemMeta() == null)
                return;
            if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
                return;
            Bukkit.broadcastMessage("Test message ");
            e.setCancelled(true);

            if (e.getSlot() == 0)
                e.getWhoClicked().openInventory(mainmenuInv);

        }
    }
}


