package dev.walshy.busyend.machines;

import dev.walshy.busyend.BusyEnd;
import dev.walshy.busyend.Items;
import dev.walshy.busyend.Utils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.multiblocks.MultiBlockMachine;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class CursedCraftingTable extends MultiBlockMachine {

    public static final RecipeType CURSED_CRAFTING_RECIPE = new RecipeType(
        new NamespacedKey(BusyEnd.getInstance(), "cursed_crafting"),
        Items.CURSED_CRAFTING_TABLE,
        "",
        "&5Used to create powerful items from end magic"
    );

    public CursedCraftingTable() {
        super(Items.BUSY_END_CATEGORY, Items.CURSED_CRAFTING_TABLE, new ItemStack[] {
            null, new ItemStack(Material.END_ROD), null,
            null, new ItemStack(Material.OBSIDIAN), null,
            null, new ItemStack(Material.DISPENSER), null
        }, new ItemStack[0], BlockFace.SELF);
    }

    @Override
    public void onInteract(@Nonnull Player p, @Nonnull Block b) {
        if (!Utils.isEnd(b.getWorld())) {
            p.sendMessage(ChatColor.GRAY + "The " + Items.CURSED_CRAFTING_TABLE.getItemMeta().getDisplayName() +
                ChatColor.GRAY + " can only be used in " + ChatColor.DARK_PURPLE + "The End");
            return;
        }

        Block dispenser = b.getRelative(BlockFace.DOWN);
        Dispenser disp = (Dispenser) dispenser.getState();
        Inventory inv = disp.getInventory();

        for (ItemStack[] input : RecipeType.getRecipeInputList(this)) {
            if (isCraftable(inv, input)) {
                ItemStack output = RecipeType.getRecipeOutputList(this, input).clone();
                if (Slimefun.hasUnlocked(p, output, true)) {
                    craft(inv, dispenser, p, b, output);
                }

                return;
            }
        }
        SlimefunPlugin.getLocal().sendMessage(p, "machines.pattern-not-found", true);
    }

    // Thanks for having all this for me Cookie <3 hehe
    @ParametersAreNonnullByDefault
    private void craft(Inventory inv, Block dispenser, Player p, Block b, ItemStack output) {
        Inventory fakeInv = createVirtualInventory(inv);
        Inventory outputInv = findOutputInventory(output, dispenser, inv, fakeInv);
        if (outputInv == null) {
            SlimefunPlugin.getLocal().sendMessage(p, "machines.full-inventory", true);
            return;
        }

        for (int j = 0; j < 9; j++) {
            ItemStack item = inv.getContents()[j];

            if (item != null && item.getType() != Material.AIR) {
                ItemUtils.consumeItem(item, true);
            }
        }
        p.getWorld().playSound(b.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

        final Color hotPink = Color.FUCHSIA;
        Location loc = dispenser.getLocation().clone();
        if (loc.getX() % 1 == 0)
            loc.add(.5, .5, .5);

        final double side = 0.5;
        Utils.drawParticleLine(Particle.END_ROD, 2, loc.clone(), loc.clone().add(0, 4, 0));

        Utils.drawParticleLine(hotPink, 2, loc.clone().add(side, -2, side), loc.clone().add(side, 2, side));
        Utils.drawParticleLine(hotPink, 2, loc.clone().add(-side, -2, side), loc.clone().add(-side, 2, side));
        Utils.drawParticleLine(hotPink, 2, loc.clone().add(side, -2, -side), loc.clone().add(side, 2, -side));
        Utils.drawParticleLine(hotPink, 2, loc.clone().add(-side, -2, -side), loc.clone().add(-side, 2, -side));

        outputInv.addItem(output);
    }

    private Inventory createVirtualInventory(@Nonnull Inventory inv) {
        Inventory fakeInv = Bukkit.createInventory(null, 9, "Fake Inventory");

        for (int j = 0; j < inv.getContents().length; j++) {
            ItemStack stack = inv.getContents()[j] != null && inv.getContents()[j].getAmount() > 1
                ? new CustomItem(inv.getContents()[j], inv.getContents()[j].getAmount() - 1)
                : null;
            fakeInv.setItem(j, stack);
        }

        return fakeInv;
    }

    private boolean isCraftable(@Nonnull Inventory inv, @Nonnull ItemStack[] recipe) {
        for (int j = 0; j < inv.getContents().length; j++) {
            if (!SlimefunUtils.isItemSimilar(inv.getContents()[j], recipe[j], true)) {
                return false;
            }
        }

        return true;
    }
}
