package me.joram.materialsstockmarket;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        // Config.yml configuration
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Registration listener
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        // Registration Commands
        this.getCommand("mm").setExecutor(new PlayerCommands(this));
    }

    @Override
    public void onDisable() {
        // Empty
    }
}
