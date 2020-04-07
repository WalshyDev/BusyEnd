package dev.walshy.busyend.machines;

import dev.walshy.busyend.BusyEnd;
import dev.walshy.busyend.Items;
import dev.walshy.busyend.Utils;
import dev.walshy.busyend.items.KleinStar;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class EnderExtractor extends SlimefunItem {

    private static final int MAGIC_PRODUCTION = 64;
    private static final int MAGIC_CAPACITY = 4096;
    private static final int MAGIC_OUTPUT = MAGIC_PRODUCTION / 2;
    private static final int EMPTY_SLOT = 14;
    private static final int MAGIC_ITEM = 12;

    private static final NamespacedKey MAGIC_KEY = new NamespacedKey(BusyEnd.getInstance(), "klein_magic");

    private final Set<Long> tasks = new HashSet<>();

    public EnderExtractor() {
        super(
            Items.BUSY_END_CATEGORY,
            Items.END_EXTRACTOR,
            CursedCraftingTable.CURSED_CRAFTING_RECIPE,
            new ItemStack[] {
                SlimefunItems.ENDER_LUMP_3, SlimefunItems.RUNE_ENDER, SlimefunItems.ENDER_LUMP_3,
                SlimefunItems.RUNE_ENDER, Items.DRAGON_ESSENCE, SlimefunItems.RUNE_ENDER,
                SlimefunItems.ENDER_LUMP_3, SlimefunItems.RUNE_ENDER, SlimefunItems.ENDER_LUMP_3,
            }
        );

        setupInterface();

        addItemHandler(onTick());
    }

    @Override
    public void onPlace(@Nonnull Player p, @Nonnull Block b) {
        if (!Utils.isEnd(b.getWorld())) {
            p.sendMessage(ChatColor.GRAY + "The " + ChatColor.DARK_PURPLE + "End Extractor" +
                ChatColor.GRAY + " can only be used in " + ChatColor.DARK_PURPLE + "The End");
        }
    }

    private void setupInterface() {
        new BlockMenuPreset(id, "&5Ender Extractor") {

            @Override
            public void init() {
                for (int i = 0; i < 27; i++) {
                    this.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }

                this.addItem(12, new CustomItem(Material.END_ROD, "&5Magic Extracted",
                    "&70&8/&5" + MAGIC_CAPACITY + " Magic Essence"), ChestMenuUtils.getEmptyClickHandler());

                this.addItem(EMPTY_SLOT, null, (player, i, itemStack, clickAction) -> {
                    ItemStack is = player.getItemOnCursor();
                    return (is.getType() != Material.AIR && SlimefunItem.getByItem(is) instanceof KleinStar)
                        || (itemStack.getType() != Material.AIR);
                });
            }

            @Override
            public boolean canOpen(Block b, Player p) {
                return p.hasPermission("slimefun.inventory.bypass")
                    || SlimefunPlugin.getProtectionManager()
                    .hasPermission(p, b.getLocation(), ProtectableAction.ACCESS_INVENTORIES);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }

            @Override
            public void newInstance(BlockMenu menu, Block b) {
                updateMagicValue(menu, getMagic(b));
            }
        };
    }

    private BlockTicker onTick() {
        return new BlockTicker() {

            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(@Nonnull Block b, @Nonnull SlimefunItem item, @Nonnull Config data) {
                if (!Utils.isEnd(b.getWorld()))
                    return;

                final BlockMenu menu = BlockStorage.getInventory(b);
                final ItemStack is = menu.getItemInSlot(EMPTY_SLOT);
                if (is != null)
                    powerStar(menu, b, is);

                if (getMagic(b) >= MAGIC_CAPACITY) return;

                // this is currently running my task, don't want it to run that again.
                if (tasks.contains(Utils.toPos(b.getLocation())))
                    return;

                // From testing this was sub 1ms at a radius of 40. It averages around 0.05ms-0.1ms
                // Can optimise more but eh, I'll do it later.
                Collection<Entity> entities = b.getWorld().getNearbyEntities(b.getLocation(), 20, 5, 20,
                    ent -> ent.getType() == EntityType.ENDERMAN);
                if (entities.isEmpty()) return;
                startupTask(b, (Enderman) entities.iterator().next());
            }
        };
    }

    private void powerStar(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull ItemStack is) {
        final ItemMeta im = is.getItemMeta();
        final SlimefunItem sfItem = SlimefunItem.getByItem(is);
        if (sfItem instanceof KleinStar) {
            final int capacity = ((KleinStar) sfItem).getTier().getCapacity();
            int currentVal = PersistentDataAPI.getInt(im, MAGIC_KEY, 0);

            if (currentVal >= capacity) return;

            final int magic = getMagic(b);

            if (magic >= MAGIC_OUTPUT) {
                PersistentDataAPI.setInt(im, MAGIC_KEY, currentVal + MAGIC_OUTPUT);
                currentVal = Math.min(capacity, currentVal + MAGIC_OUTPUT);
                List<String> lore = im.getLore();
                if (lore.size() < 3) return;

                final int itemCapacity = ((KleinStar) sfItem).getTier().getCapacity();
                lore.set(2, ChatColor.GRAY + Utils.getProgressBar(currentVal, capacity, '-', ':')
                    + " " + (currentVal >= capacity ? ChatColor.LIGHT_PURPLE + String.valueOf(currentVal)
                    : ChatColor.GRAY + String.valueOf(currentVal))
                    + "/" + ChatColor.DARK_PURPLE + itemCapacity + " ME "
                    + ChatColor.GRAY + "(" + Utils.getPercentage(currentVal, capacity) + ')');
                im.setLore(lore);
                is.setItemMeta(im);
                menu.replaceExistingItem(EMPTY_SLOT, is);
                updateMagicValue(menu, updateMagic(b, -MAGIC_OUTPUT));
            }
        }
    }

    private void drawLine(Location point1, Location point2) {
        final World world = point1.getWorld();
        final double distance = point1.distance(point2);
        final Vector p1 = point1.toVector();
        final Vector vector = point2.toVector().clone().subtract(p1).normalize().multiply(1);
        for (int length = 0; length < distance; length++) {
            p1.add(vector);
            spawnEndParticle(p1.toLocation(world), 1, Color.PURPLE);
        }
    }

    private void startupTask(@Nonnull Block b, @Nonnull Enderman e) {
        // Make them scream LUL
        tasks.add(Utils.toPos(b.getLocation()));
        e.setTarget(e);
        e.setAI(false);
        e.setMetadata("extracting", new FixedMetadataValue(BusyEnd.getInstance(), true));

        drawLine(b.getLocation(), e.getLocation().add(0, 1.5, 0));

        final World world = b.getWorld();
        final double distance = e.getLocation().distance(b.getLocation());
        final Vector p1 = e.getLocation().toVector();
        final Vector vector = b.getLocation().toVector().clone().subtract(p1).normalize().multiply(1);
        AtomicInteger length = new AtomicInteger();
        Bukkit.getScheduler().runTaskTimer(BusyEnd.getInstance(), (task) -> {
            if (length.get() >= distance) {
                task.cancel();
                extractMagicFromEnderman(b, e);
                return;
            }
            p1.add(vector);
            e.teleport(p1.toLocation(world));
            drawLine(b.getLocation(), e.getLocation().add(0, 1.5, 0));

            length.addAndGet(1);
        }, 2, 2);
    }

    private void extractMagicFromEnderman(@Nonnull Block b, @Nonnull Enderman enderman) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        enderman.remove();
        tasks.remove(Utils.toPos(b.getLocation()));

        // Let's make a bit of a "pop" effect
        final Location loc = b.getLocation();
        for (int i = 0; i < 6; i++) {
            spawnEndParticle(loc.clone().add(
                random.nextBoolean() ? random.nextDouble() * 2 : -random.nextDouble() * 2,
                random.nextDouble(),
                random.nextBoolean() ? random.nextDouble() * 2 : -random.nextDouble() * 2
            ), 12, Color.FUCHSIA);
        }

        updateMagicValue(BlockStorage.getInventory(b), updateMagic(b, MAGIC_PRODUCTION));
    }

    private void spawnEndParticle(@Nonnull Location loc, int amount, @Nonnull Color color) {
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), amount,
            new Particle.DustOptions(color, 2));
    }

    private int getMagic(@Nonnull Block b) {
        final String charge = BlockStorage.getLocationInfo(b.getLocation(), "magic");

        if (charge != null) {
            return Integer.parseInt(charge);
        } else {
            BlockStorage.addBlockInfo(b.getLocation(), "magic", "0", false);
            return 0;
        }
    }

    private int updateMagic(@Nonnull Block b, int amount) {
        final int newAmount = getMagic(b) + amount;
        BlockStorage.addBlockInfo(b.getLocation(), "magic", String.valueOf(newAmount), true);
        return newAmount;
    }

    private void updateMagicValue(BlockMenu menu, int magic) {
        menu.replaceExistingItem(MAGIC_ITEM, new CustomItem(Material.END_ROD, "&5Magic Extracted",
            (magic >= MAGIC_CAPACITY ? "&5" + magic : "&7" + magic) + "&8/&5" + MAGIC_CAPACITY + " Magic Essence")
        );
    }
}
