package me.joram.materialsstockmarket;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Main extends JavaPlugin {

    private Main main;

    public Main(){
        this.main = this;
    }

    @Override
    public void onEnable() {

        // Config.yml configuration
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Registration listener
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        // Registration Commands
        this.getCommand("mm").setExecutor(new PlayerCommands(this));

        runnable();
    }

    @Override
    public void onDisable() {
        // Empty
    }

    // AutoPrice
    public void runnable() {

        DataController dataController = new DataController(main);
        if(dataController.getAutoPriceStatus() == false){
            return;
        }

        int ticks = dataController.getIntervalAutoPrice();

        new BukkitRunnable() {
            @Override
            public void run() {
                AutoPricing auto = new AutoPricing(main);
                auto.runAutoPricing();
            }
        }.runTaskTimerAsynchronously(this, 0, (ticks * 20));
    };
}
