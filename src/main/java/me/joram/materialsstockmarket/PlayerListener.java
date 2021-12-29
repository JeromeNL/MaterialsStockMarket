package me.joram.materialsstockmarket;

import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private ItemStack clickedItem;
    private Inventory mainmenuInv;
    private  BuySell buySell;
    private Main main;




    public PlayerListener(Main main) {

         this.main = main;
        buySell = new BuySell(main);


    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent e) throws UserDoesNotExistException {

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
                PlayerGUI pGUI = new PlayerGUI(main);
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

            e.setCancelled(true);

            if (e.getSlot() == 0)
                e.getWhoClicked().openInventory(mainmenuInv);

            switch (e.getSlot()) {
                case 9:
                    buySell.buyItem(clickedItem.getType(), 1, e.getWhoClicked());
                    break;
                case 10:
                    buySell.buyItem(clickedItem.getType(), 2,  e.getWhoClicked());
                    break;
                case 11:
                    buySell.buyItem(clickedItem.getType(), 4, e.getWhoClicked());
                    break;
                case 12:
                    buySell.buyItem(clickedItem.getType(), 8, e.getWhoClicked());
                    break;
                case 13:
                    buySell.buyItem(clickedItem.getType(), 16,  e.getWhoClicked());
                    break;
                case 14:
                    buySell.buyItem(clickedItem.getType(), 32, e.getWhoClicked());
                    break;
                case 15:
                    buySell.buyItem(clickedItem.getType(), 64,  e.getWhoClicked());
                    break;
                case 16:
                    buySell.buyItem(clickedItem.getType(), 128,  e.getWhoClicked());
                    break;
                case 17:
                    buySell.buyItem(clickedItem.getType(), 256, e.getWhoClicked());
                    break;


                case 18:
                    buySell.sellItem(clickedItem.getType(), 1,  e.getWhoClicked());
                    break;
                case 19:
                    buySell.sellItem(clickedItem.getType(), 2,  e.getWhoClicked());
                    break;
                case 20:
                    buySell.sellItem(clickedItem.getType(), 4,  e.getWhoClicked());
                    break;
                case 21:
                    buySell.sellItem(clickedItem.getType(), 8,  e.getWhoClicked());
                    break;
                case 22:
                    buySell.sellItem(clickedItem.getType(), 16,  e.getWhoClicked());
                    break;
                case 23:
                    buySell.sellItem(clickedItem.getType(), 32,  e.getWhoClicked());
                    break;
                case 24:
                    buySell.sellItem(clickedItem.getType(), 64,  e.getWhoClicked());
                    break;
                case 25:
                    buySell.sellItem(clickedItem.getType(), 128,  e.getWhoClicked());
                    break;
                case 26:
                    buySell.sellItem(clickedItem.getType(), 256,  e.getWhoClicked());
                    break;
            }
        }
    }
}


