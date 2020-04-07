package dev.walshy.busyend.items;

import dev.walshy.busyend.Items;
import dev.walshy.busyend.machines.CursedCraftingTable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class KleinStar extends SlimefunItem {

    private final Tier tier;

    public KleinStar(Tier tier) {
        super(Items.BUSY_END_CATEGORY, tier.slimefunItem,
            tier == Tier.EIN ? RecipeType.ANCIENT_ALTAR : CursedCraftingTable.CURSED_CRAFTING_RECIPE, tier.recipe);

        this.tier = tier;
    }

    public Tier getTier() {
        return tier;
    }

    public enum Tier {

        EIN(1000, Items.KLEIN_STAR, new ItemStack[] {
            SlimefunItems.ESSENCE_OF_AFTERLIFE, Items.DRAGON_ESSENCE, SlimefunItems.ESSENCE_OF_AFTERLIFE,
            SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.ESSENCE_OF_AFTERLIFE,
            SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.RUNE_ENDER, SlimefunItems.ESSENCE_OF_AFTERLIFE
        }),
        ZWEI(4000, Items.KLEIN_STAR_ZWEI, Items.KLEIN_STAR),
        DREI(12000, Items.KLEIN_STAR_DREI, Items.KLEIN_STAR_ZWEI),
        VIER(48000, Items.KLEIN_STAR_VIER, Items.KLEIN_STAR_DREI),
        SPHERE(100_000, Items.KLEIN_STAR_SPHERE, Items.KLEIN_STAR_VIER),
        OMEGA(250_000, Items.KLEIN_STAR_OMEGA, Items.KLEIN_STAR_SPHERE);

        private final int capacity;
        private final SlimefunItemStack slimefunItem;
        private final ItemStack[] recipe;

        Tier(int capacity, @Nonnull SlimefunItemStack itemStack, @Nonnull ItemStack[] recipe) {
            this.capacity = capacity;
            this.slimefunItem = itemStack;
            this.recipe = recipe;
        }

        Tier(int capacity, @Nonnull SlimefunItemStack itemStack, @Nonnull SlimefunItemStack item) {
            this(capacity, itemStack, new ItemStack[] {
                item, item, null,
                item, item, null,
                null, null, null
            });
        }

        public SlimefunItemStack getItem() {
            return this.slimefunItem;
        }

        public int getCapacity() {
            return capacity;
        }
    }
}
