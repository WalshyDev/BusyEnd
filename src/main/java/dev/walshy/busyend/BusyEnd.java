package dev.walshy.busyend;

import dev.walshy.busyend.items.DragonEssence;
import dev.walshy.busyend.items.KleinStar;
import dev.walshy.busyend.listeners.EndEvents;
import dev.walshy.busyend.listeners.Events;
import dev.walshy.busyend.machines.CursedCraftingTable;
import dev.walshy.busyend.machines.EnderExtractor;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.plugin.java.JavaPlugin;

public class BusyEnd extends JavaPlugin implements SlimefunAddon {

    private static BusyEnd instance;

    public static BusyEnd getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new EndEvents(), this);

        // Category
        Items.BUSY_END_CATEGORY.register();

        // Machines
        new CursedCraftingTable().register(this);
        new EnderExtractor().register(this);

        // Items
        new DragonEssence().register(this);
        for (KleinStar.Tier tier : KleinStar.Tier.values()) {
            new KleinStar(tier).register(this);
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public String getBugTrackerURL() {
        return "https://github.com/WalshyDev/BusyEnd";
    }
}
