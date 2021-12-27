package me.joram.materialsstockmarket;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MaterialsStockMarket extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        // Registration Commands

        FileConfiguration config = getConfig();

        this.getCommand("mm").setExecutor(new PlayerCommands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
