package dev.walshy.busyend;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        if (e.getItem() != null) {
            if (e.getItem().getType() == Material.ENDER_PEARL
                && (SlimefunUtils.isItemSimilar(e.getItem(), Items.KLEIN_STAR_VIER, true)
                || SlimefunUtils.isItemSimilar(e.getItem(), Items.KLEIN_STAR_SPHERE, true))
            )
                e.setCancelled(true);
            else if (e.getItem().getType() == Material.ENDER_EYE
                && SlimefunUtils.isItemSimilar(e.getItem(), Items.KLEIN_STAR_OMEGA, true)
            )
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDragonDeath(final EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.ENDER_DRAGON && Utils.isEnd(e.getEntity().getWorld())) {
            e.getDrops().add(Items.DRAGON_ESSENCE);
        }
    }
}
