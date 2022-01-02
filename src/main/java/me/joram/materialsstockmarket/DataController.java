package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
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
        Double currentBuyPrices = getBuyPrice(mat, 1);
        Double currentSellPrices = getSellPrice(mat, 1);
        main.getConfig().set("buyprices." + mat.name(), newAmountBuy);
        main.getConfig().set("sellprices." + mat.name(), newAmountSell);
        main.saveDefaultConfig();
    }

    public List<String> getAllItems() {
        String[] stringArray = new String[27];
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
}
