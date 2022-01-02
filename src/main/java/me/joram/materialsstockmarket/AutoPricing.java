package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AutoPricing {


    private Double general;
    private Double metal;
    private Double wood;
    private Double rock;
    private Double food;
    private DataController dataController;
    private String[] categories;
    private Main main;
    private NumberFormat nf;


    public AutoPricing(Main main){
        this.main = main;
        categories = new String[5];
        dataController = new DataController(main);
    }

    public void runAutoPricing(){
        generateNewGeneral();
        assignNewValues();
        sendBroadCastMessage();
        fillCategories();
        addNewValues();
        sendBroadCastMessage();
    }

    // Returns a double (with 2 decimals) between 0.89 & 1.14
    public void generateNewGeneral(){
        Random rand = new Random();
        int newGeneralInt =  rand.nextInt(25);
        Double newGeneral = ((100 + Double.valueOf(newGeneralInt) - 11) / 100);
        general = newGeneral;
        //return newGeneral;
    }

    public void assignNewValues(){
        // general x 2,5
        Bukkit.broadcastMessage("assign general: " + general);
        metal = percentageToDecimal((decimalToPercentage(general) * 2.5));
        Bukkit.broadcastMessage("metal: " + metal + "display: " + decimalToPercentage(metal));
        // general * negative
        wood = percentageToDecimal(decimalToPercentage(general) * -1.00);
        // general * 0.5
        rock = percentageToDecimal(decimalToPercentage(general) * 0.500);
        // general
        food = general;
    }

    public void fillCategories(){
        categories[0] = "GENERAL";
        categories[1] = "METAL";
        categories[2] = "WOOD";
        categories[3] = "ROCK";
        categories[4] = "FOOD";
    }

    public void addNewValues(){
        for(int index = 0; index < 5; index ++) {

            List<String> stringList = dataController.getItemsOfCategory(categories[index].toUpperCase());

            for (int i = 0; i < stringList.size(); i++) {
                Double buyPrice = dataController.getBuyPrice(Material.valueOf(stringList.get(i)), 1);
                Double sellPrice = dataController.getSellPrice(Material.valueOf(stringList.get(i)), 1);

                Double differenceAmount = itemDifference();
                switch(i){
                    case 0:
                        buyPrice = (buyPrice * (general * differenceAmount));
                        sellPrice =  (sellPrice * (general * differenceAmount));
                        break;
                    case 1:
                        buyPrice = (buyPrice * (metal * differenceAmount));
                        sellPrice = (sellPrice * (metal * differenceAmount));
                        break;
                    case 2:
                        buyPrice = (buyPrice * (wood * differenceAmount));
                        sellPrice = (sellPrice * (wood * differenceAmount));
                        break;
                    case 3:
                        buyPrice = (buyPrice * (rock * differenceAmount));
                        sellPrice = (sellPrice * (rock * differenceAmount));
                        break;
                    case 4:
                        buyPrice = (buyPrice * (food * differenceAmount));
                        sellPrice = (sellPrice * (food * differenceAmount));
                        break;
                }
                dataController.addToPrices(Material.valueOf(stringList.get(i)), buyPrice, sellPrice);

            }
        }
    }

    // returns value between 0.95 and 1.05;
    public Double itemDifference(){
        Random rand = new Random();
        int difference = rand.nextInt(10);
        Double differenceD = ((100 + (Double.valueOf(difference) - 5)) /100);
        return differenceD;
    }
    // 0.89 & 1.14

    public Double decimalToPercentage(Double decimal){
        Double percentage =  ((decimal * 100) - 100);
        return percentage;
    }

    public Double percentageToDecimal(Double percentage){
        Double decimal = ((100 + percentage) /100);
        return decimal;
    }

    public void sendBroadCastMessage(){

        nf = new DecimalFormat(
                "################################################.##");

        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "------------==()==------------");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "The stock market has risen on average by " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(general)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Metal: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(metal)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Wood: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(wood)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Rock: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(rock)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Food: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(food)) + "%.");

        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "------------==()==------------");
    }
}
