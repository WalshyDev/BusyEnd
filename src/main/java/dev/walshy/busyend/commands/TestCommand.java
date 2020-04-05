package dev.walshy.busyend.commands;

import dev.walshy.busyend.BusyEnd;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

public class TestCommand implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        LivingEntity dragon = (LivingEntity) player.getWorld().spawnEntity(player.getLocation().clone().add(0, 40, 0)
            , EntityType.ENDER_DRAGON);
        Bukkit.getScheduler().runTaskLater(BusyEnd.getInstance(), () -> {
            dragon.setHealth(0);
        }, 5);

        return true;
    }
}
