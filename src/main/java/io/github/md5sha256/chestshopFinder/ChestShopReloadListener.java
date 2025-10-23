package io.github.md5sha256.chestshopFinder;

import com.Acrobot.ChestShop.Events.ChestShopReloadEvent;
import io.github.md5sha256.chestshopFinder.util.UnsafeChestShopSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChestShopReloadListener implements Listener {

    @EventHandler
    public void onChestShopReload(ChestShopReloadEvent event) {
        UnsafeChestShopSign.init();
    }

}
