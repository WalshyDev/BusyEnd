package dev.walshy.busyend.items;

import dev.walshy.busyend.Items;
import dev.walshy.busyend.machines.CursedCraftingTable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class KleinStar extends SlimefunItem {

    public KleinStar(Tier tier) {
        super(Items.BUSY_END_CATEGORY, tier.slimefunItem,
            tier == Tier.EIN ? RecipeType.ANCIENT_ALTAR : CursedCraftingTable.CURSED_CRAFTING_RECIPE, tier.recipe);
    }

    public enum Tier {

        EIN(Items.KLEIN_STAR, new ItemStack[] {
            SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.ENDER_LUMP_3, SlimefunItems.ESSENCE_OF_AFTERLIFE,
            SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.ESSENCE_OF_AFTERLIFE,
            SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.RUNE_ENDER, SlimefunItems.ESSENCE_OF_AFTERLIFE
        }),
        ZWEI(Items.KLEIN_STAR_ZWEI, new ItemStack[] {
            Items.KLEIN_STAR, Items.KLEIN_STAR, null,
            Items.KLEIN_STAR, Items.KLEIN_STAR, null,
            null, null, null
        }),
        DREI(Items.KLEIN_STAR_DREI, new ItemStack[] {
            Items.KLEIN_STAR_ZWEI, Items.KLEIN_STAR_ZWEI, null,
            Items.KLEIN_STAR_ZWEI, Items.KLEIN_STAR_ZWEI, null,
            null, null, null
        }),
        VIER(Items.KLEIN_STAR_VIER, new ItemStack[] {
            Items.KLEIN_STAR_DREI, Items.KLEIN_STAR_DREI, null,
            Items.KLEIN_STAR_DREI, Items.KLEIN_STAR_DREI, null,
            null, null, null
        }),
        SPHERE(Items.KLEIN_STAR_SPHERE, new ItemStack[] {
            Items.KLEIN_STAR_VIER, Items.KLEIN_STAR_VIER, null,
            Items.KLEIN_STAR_VIER, Items.KLEIN_STAR_VIER, null,
            null, null, null
        }),
        OMEGA(Items.KLEIN_STAR_OMEGA, new ItemStack[] {
            Items.KLEIN_STAR_SPHERE, Items.KLEIN_STAR_SPHERE, null,
            Items.KLEIN_STAR_SPHERE, Items.KLEIN_STAR_SPHERE, null,
            null, null, null
        });

        private final SlimefunItemStack slimefunItem;
        private final ItemStack[] recipe;

        Tier(SlimefunItemStack itemStack, ItemStack[] recipe) {
            this.slimefunItem = itemStack;
            this.recipe = recipe;
        }
    }
}
