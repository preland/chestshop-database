package io.github.md5sha256.chestshopFinder;

import io.github.md5sha256.chestshopFinder.util.UnsafeChestShopSign;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChestshopFinder extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        UnsafeChestShopSign.init();
        // getServer().getPluginManager().registerEvents(new ChunkListener(getLogger(), getServer(), null, null), this);
        getLogger().info("Plugin enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin disabled");
    }
}
