package me.joram.materialsstockmarket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
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
    private HashMap<String, Double> oldBuyPricesList;
    private HashMap<String, Double> oldSellPricesList;
    private Double currentBiggestGainer;
    private String currentBiggestGainerName;
    private Double currentBiggestLoser;
    private String currentBiggestLoserName;

    public AutoPricing(Main main) {
        this.main = main;
        categories = new String[5];
        dataController = new DataController(main);
        oldBuyPricesList = new HashMap<String, Double>();
        oldSellPricesList = new HashMap<String, Double>();
        currentBiggestGainer = 0.420;
        currentBiggestGainerName = "N/A";
        currentBiggestLoserName = "N/A";
    }

    public void runAutoPricing() {
        oldBuyPricesList = getOldBuyPrices();
        generateNewGeneral();
        assignNewValues();
        fillCategories();
        addNewValues();
        biggestGainer();
        biggestLoser();
        sendBroadCastMessage();
    }

    // Returns a double (with 2 decimals) between 0.895 & 1.145
    public void generateNewGeneral() {
        Random rand = new Random();
        int newGeneralInt = rand.nextInt(25);
        Double newGeneral = ((100 + Double.valueOf(newGeneralInt) - 10.5) / 100);
        general = newGeneral;
        //return newGeneral;
    }

    public void assignNewValues() {
        // general * 1.05
        metal = percentageToDecimal((decimalToPercentage(general) * 1.05));

        // general * 0.94 + 0.091
        Double halfWood = percentageToDecimal(decimalToPercentage(positiveToNegative(general)));
        wood = (halfWood * 0.94 +  0.091 );

        // general * 0.55
        rock = percentageToDecimal(decimalToPercentage(general) * 0.550);

        // general * 0.78
        food = percentageToDecimal(decimalToPercentage(general) * 0.78);;
    }

    public void fillCategories() {
        categories[0] = "GENERAL";
        categories[1] = "METAL";
        categories[2] = "WOOD";
        categories[3] = "ROCK";
        categories[4] = "FOOD";
    }

    public void addNewValues() {
        for (int index = 0; index < 5; index++) {

            List<String> stringList = dataController.getItemsOfCategory(categories[index].toUpperCase());

            for (int i = 0; i < stringList.size(); i++) {
                Double buyPrice = dataController.getBuyPrice(Material.valueOf(stringList.get(i)), 1);
                Double sellPrice = dataController.getSellPrice(Material.valueOf(stringList.get(i)), 1);

                Double differenceAmount = itemDifference();
                switch (index) {
                    case 0:
                        buyPrice = (buyPrice * (general * differenceAmount));
                        sellPrice = (sellPrice * (general * differenceAmount));
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

    // returns value between 0.975 and 1.025;
    public Double itemDifference() {
        Random rand = new Random();
        int difference = rand.nextInt(5);
        Double differenceD = ((100 + (Double.valueOf(difference) - 2.5)) / 100);
        return differenceD;
    }


    public Double decimalToPercentage(Double decimal) {
        Double percentage = ((decimal * 100) - 100);
        return percentage;
    }

    public Double percentageToDecimal(Double percentage) {
        Double decimal = ((100 + percentage) / 100);
        return decimal;
    }

    public void sendBroadCastMessage() {

        nf = new DecimalFormat(
                "################################################.##");

        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "------------==()==------------");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "The stock market has risen on average by " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(general)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Metal: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(metal)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Wood: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(wood)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Rock: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(rock)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Food: " + ChatColor.LIGHT_PURPLE + nf.format(decimalToPercentage(food)) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "The biggest gainer is: " + currentBiggestGainerName + " by " + nf.format(currentBiggestGainer) + "%." );
        Bukkit.broadcastMessage(ChatColor.DARK_RED   + "The biggest loser is: "  + currentBiggestLoserName  + " by " + nf.format(currentBiggestLoser) + "%.");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "------------==()==------------");
    }

    public HashMap<String, Double> getOldBuyPrices(){
        oldBuyPricesList = dataController.getOldBuyPrices();
        return oldBuyPricesList;
    }

    public Double compareBuyPrice(String material){

           Double oldPrice = oldBuyPricesList.get(material);
           Double newPrice = dataController.getBuyPrice(Material.valueOf(material), 1);
           Double priceChange = ((newPrice - oldPrice) / oldPrice) * 100;
           return priceChange;
        }

    public String biggestGainer(){
        List<String> list = dataController.getAllItems();
        String biggestGainer = "nulllll";
        for(int i = 0; i < (list.size()); i++){
            if(list.size() == 0){
                break;
            }
            if(i == 0){
                biggestGainer = list.get(i);
            }
            else if(compareBuyPrice(list.get(i)) > compareBuyPrice(biggestGainer)){
                biggestGainer = list.get(i);
            }
        }
        currentBiggestGainer = compareBuyPrice(biggestGainer);
        currentBiggestGainerName = biggestGainer;
        return biggestGainer;
    }
    public String biggestLoser(){
        List<String> list = dataController.getAllItems();
        String biggestLoser = "nulllll";
        for(int i = 0; i < (list.size()); i++){
            if(list.size() == 0){
               break;
            }
            if(i == 0){
                biggestLoser = list.get(i);
            }
            else if(compareBuyPrice(list.get(i)) < compareBuyPrice(biggestLoser)){
                biggestLoser = list.get(i);
            }
        }
        currentBiggestLoser = compareBuyPrice(biggestLoser);
        currentBiggestLoserName = biggestLoser;
        return biggestLoser;
    }

    public void changeAllPrices(int percentage){
        for(int i = 0; i < dataController.getAllItems().size(); i++){
            Double amount = percentageToDecimal(Double.valueOf(percentage));

           Double buyPrice = dataController.getBuyPrice(Material.valueOf(dataController.getAllItems().get(i)), 1);
           Double sellPrice = dataController.getSellPrice(Material.valueOf(dataController.getAllItems().get(i)), 1);
           dataController.addToPrices(Material.valueOf(dataController.getAllItems().get(i)), (buyPrice * amount), (sellPrice * amount));
        }
    }
    public Double positiveToNegative(Double pos){
        Double newValue = 1 - (pos - 1);
        return newValue;
    }
}



