package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DataController {

    Main main;

    public DataController(Main main){
        this.main = main;
    }

    public Double getBuyPrice(Material mat, int amount){
        Double buyPrice = (main.getConfig().getDouble("buyprices." + mat.name()) * amount);
        return buyPrice;
    }

    public Double getSellPrice(Material mat, int amount){
        Double sellPrice = (main.getConfig().getDouble("sellprices." + mat.name()) * amount);
        return sellPrice;
    }

    public void addToPrices(Material mat, Double newAmountBuy, Double newAmountSell){
        main.getConfig().set("buyprices." + mat.name(), newAmountBuy);
        main.getConfig().set("sellprices." + mat.name(), newAmountSell);
        main.saveConfig();

    }

    public List<String> getAllItems(int invSize) {
        String[] stringArray = new String[invSize];
        List<String> stringList = Arrays.asList(stringArray);

        stringList = main.getConfig().getStringList("items");
        return stringList;
    }

    public List<String> getItemsOfCategory(String category){
        String[] stringArray = new String[27];
        List<String> stringList = Arrays.asList(stringArray);

        stringList = main.getConfig().getStringList("categories." + category.toUpperCase());
        return stringList;
    }

    public boolean getAutoPriceStatus(){
        return main.getConfig().getBoolean("autoprice");
    }

    public void setAutoPriceStatus(boolean newStatus){
        main.getConfig().set("autoprice", newStatus);
        String message;
        if(newStatus == true){
            message = ChatColor.YELLOW + "WARN! The autoprice is" + ChatColor.YELLOW + ChatColor.UNDERLINE + "enabled!";
        }
        else{
            message = ChatColor.YELLOW + "WARN! The autoprice is " + ChatColor.YELLOW + ChatColor.UNDERLINE + "disabled!";
        }
        Bukkit.broadcastMessage(message);
    }

    public HashMap<String, Double> getOldBuyPrices(){
        HashMap<String, Double> oldBuyPrices = new HashMap<>();
        List<String> allItems = getAllItems(getAmountOfSpaces());

        for(int i = 0; i < allItems.size(); i++){
            oldBuyPrices.put(allItems.get(i), getBuyPrice(Material.valueOf(allItems.get(i)), 1));
        }
        return oldBuyPrices;
    }

    public boolean itemExists(String item){

        List<String> items = getAllItems(getAmountOfSpaces());
        for(int i = 0; i < items.size(); i++){
            if(item.equalsIgnoreCase(items.get(i))){
                return true;
            }
        }
        return false;
    }

    public int getAmountOfSpaces(){
        int spaces =  ((main.getConfig().getInt("inventoryrows") + 1) *9);
        return spaces;
    }
    public boolean setAmountOfRows(int amount){
        if(!(amount > 1 && amount < 6)){
            return false;
        }
        main.getConfig().set("inventoryrows", amount);
        return true;
    }

}
