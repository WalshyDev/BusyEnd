package dev.walshy.busyend.items;

import dev.walshy.busyend.Items;
import dev.walshy.busyend.machines.CursedCraftingTable;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

// TODO: Allow the user to fly if this in in hotbar. Use magic every X seconds of use.
public class SwiftwolfsRendingGale extends SlimefunItem {

    public SwiftwolfsRendingGale() {
        super(Items.BUSY_END_CATEGORY, Items.SWIFTWOLFS_RENDERING_GALE, CursedCraftingTable.CURSED_CRAFTING_RECIPE,
            new ItemStack[] {
                Items.DRAGON_ESSENCE, new ItemStack(Material.FEATHER), Items.DRAGON_ESSENCE,
                new ItemStack(Material.FEATHER), new ItemStack(Material.COMPASS), new ItemStack(Material.FEATHER),
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.FEATHER), SlimefunItems.ESSENCE_OF_AFTERLIFE
            });
    }
}
