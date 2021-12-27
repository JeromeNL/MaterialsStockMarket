package me.joram.materialsstockmarket;


import org.bukkit.Material;

public class DataController {

    MaterialsStockMarket main;

    public DataController(MaterialsStockMarket main){
        this.main = main;
    }



    public void getAllItems(){

    }

    public void addItemToList(Material mat, int buyPrice, int sellPrice){
        main.getConfig().set("Materials." + mat.name(), "buy:. " + "sell:");
        main.saveConfig();
    }

}
