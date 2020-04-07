package dev.walshy.busyend;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.text.DecimalFormat;

public final class Utils {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    private Utils() {}

    public static long toPos(@Nonnull final Location location) {
        return ((long) (location.getBlockX() & 0x3FFFFFF) << 38)
            | ((long) (location.getBlockZ() & 0x3FFFFFF) << 12)
            | (long) (location.getBlockY() & 0xFFF);
    }

    public static boolean isEnd(@Nonnull final World world) {
        return world.getUID().equals(Bukkit.getWorlds().get(!Bukkit.getAllowNether() ? 1 : 2).getUID());
    }

    @ParametersAreNonnullByDefault
    public static void drawParticleLine(Particle particle, int amount, Location point1, Location point2) {
        drawParticleLine(particle, amount, point1, point2, null);
    }

    @ParametersAreNonnullByDefault
    public static void drawParticleLine(Color color, int amount, Location point1, Location point2) {
        drawParticleLine(Particle.REDSTONE, amount, point1, point2, new Particle.DustOptions(color, amount));
    }

    @ParametersAreNonnullByDefault
    public static <T> void drawParticleLine(Particle particle, int amount, Location point1, Location point2,
                                            @Nullable T data) {
        final World world = point1.getWorld();
        final double distance = point1.distance(point2);
        final Vector p1 = point1.toVector();
        final Vector vector = point2.toVector().clone().subtract(p1).normalize().multiply(1);
        for (int length = 0; length < distance; length++) {
            p1.add(vector);
            world.spawnParticle(particle, p1.getX(), p1.getY(), p1.getZ(), amount, data);
        }
    }

    @Nonnull
    public static String getProgressBar(int x, int max, char filled, char empty) {
        float percentage = (100f / max * x);
        return ChatColor.DARK_GRAY + "["
            + ChatColor.GREEN + repeat(filled, (int) Math.round((double) percentage / 10)) +
            ChatColor.GRAY + repeat(empty, 10 - (int) Math.round((double) percentage / 10))
            + ChatColor.DARK_GRAY + ']';
    }

    public static String getPercentage(int x, int max) {
        float percentage = (100f / max * x);
        return df.format(percentage) + '%';
    }

    private static String repeat(char c, int amnt) {
        final StringBuilder sb = new StringBuilder();
        ;
        for (int i = 0; i < amnt; i++)
            sb.append(c);

        return sb.toString();
    }
}
