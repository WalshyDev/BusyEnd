package dev.walshy.busyend.listeners;

import dev.walshy.busyend.Utils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Collection;

public class EndEvents implements Listener {

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        if ((e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL
            || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.ENDER_PEARL
        ) && Utils.isEnd(e.getEntity().getWorld())
            && e.getEntity() instanceof Mob
        ) {
            final Mob entity = (Mob) e.getEntity();

            AttributeInstance maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (maxHealth != null) {
                maxHealth.setBaseValue(maxHealth.getValue() * 2);
                entity.setHealth(maxHealth.getValue());
            }

            final Collection<Entity> entities = entity.getWorld().getNearbyEntities(entity.getLocation(), 20, 10, 20,
                ent -> ent instanceof Player);

            if (!entities.isEmpty())
                entity.setTarget((LivingEntity) entities.iterator().next());
        }
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent e) {
        // TODO: Make a bit of a "bloodrush" kinda event here.
        // Make a bunch of buffed very angry mobs rise up and try to stop the player taking the essence
        // This should be hard on these players hehe >:)
    }
}
