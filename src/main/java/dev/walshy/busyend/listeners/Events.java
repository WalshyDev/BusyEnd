package dev.walshy.busyend.listeners;

import dev.walshy.busyend.Items;
import dev.walshy.busyend.Utils;
import dev.walshy.busyend.items.KleinStar;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {

    @EventHandler
    public void onEndermanDamage(final EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.ENDERMAN
            && !e.getEntity().getMetadata("extracting").isEmpty()
            && e.getEntity().getMetadata("extracting").get(0).asBoolean()
        )
            e.setCancelled(true);
    }

    @EventHandler
    public void onRightClick(final PlayerInteractEvent e) {
        if (e.getItem() != null && SlimefunItem.getByItem(e.getItem()) instanceof KleinStar) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDragonDeath(final EntityDeathEvent e) {
        // SF didn't seem to do the drop so... eh, I'll do it.
        if (e.getEntityType() == EntityType.ENDER_DRAGON && Utils.isEnd(e.getEntity().getWorld())) {
            e.getDrops().add(Items.DRAGON_ESSENCE.clone());
        }
    }

    @EventHandler
    public void onEndExtractorHit(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK
            && e.getClickedBlock() != null
            && e.getClickedBlock().getType() == Material.END_PORTAL_FRAME
            && BlockStorage.check(e.getClickedBlock().getLocation(), Items.END_EXTRACTOR.getItemID())
        ) {
            // This is to make SF fire on break. I need to get the block data cleaned up and the item dropped.
            // I shouldn't do this myself as it can change, better to let SF handle it.
            Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(e.getClickedBlock(), e.getPlayer()));
        }
    }
}
