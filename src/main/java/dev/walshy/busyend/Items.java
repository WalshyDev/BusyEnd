package dev.walshy.busyend;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Items {

    // Category
    public static final Category BUSY_END_CATEGORY = new Category(
        new NamespacedKey(BusyEnd.getInstance(), "busyend"),
        new CustomItem(Material.END_CRYSTAL, "&5&lBusyEnd")
    );

    public static final SlimefunItemStack END_EXTRACTOR = new SlimefunItemStack(
        "END_EXTRACTOR",
        Material.END_PORTAL_FRAME,
        "&5End Extractor", "&5Extract power and magic", "&5from the end realm"
    );

    public static final SlimefunItemStack CURSED_CRAFTING_TABLE = new SlimefunItemStack(
        "CURSED_CRAFTING_TABLE",
        Material.ENDER_CHEST,
        "&cCursed &7Crafting Table",
        "&7Used to craft magical items", "&cHowever... there may be a ", "&cside-effect to this power"
    );

    public static final SlimefunItemStack DRAGON_ESSENCE = new SlimefunItemStack(
        "DRAGON_ESSENCE",
        Material.DRAGON_EGG,
        "&5Dragon Essence",
        "&7A powerful magic essence", "&7dropped by the dragon.",
        "&7This could be used to make great things"
    );

    public static final SlimefunItemStack SWIFTWOLFS_RENDERING_GALE = new SlimefunItemStack(
        "SWIFTWOLFS_RENDERING_GALE",
        Material.COMPASS,
        "&7Swiftwolf's Rending Gale",
        "&7Consume magic to fly around"
    );

    //region Klein Stars
    public static final SlimefunItemStack KLEIN_STAR = new SlimefunItemStack(
        "KLEIN_STAR_EIN",
        Material.DEAD_BUSH,
        "&fKlein Star",
        "&7Holds up to 1,000 magic essence",
        "",
        "&7[::::::::::] 0/0 ME (0.00%)"
    );

    public static final SlimefunItemStack KLEIN_STAR_ZWEI = new SlimefunItemStack(
        "KLEIN_STAR_ZWEI",
        Material.DEAD_BUSH,
        "&fKlein Star Zwei",
        "&7Holds up to 4,000 magic essence",
        "",
        "&7[::::::::::] 0/0 ME (0.00%)"
    );

    public static final SlimefunItemStack KLEIN_STAR_DREI = new SlimefunItemStack(
        "KLEIN_STAR_DREI",
        Material.STICK,
        "&fKlein Star Drei",
        "&7Holds up to 12,000 magic essence",
        "",
        "&7[::::::::::] 0/0 ME (0.00%)"
    );

    public static final SlimefunItemStack KLEIN_STAR_VIER = new SlimefunItemStack(
        "KLEIN_STAR_VIER",
        Material.ENDER_PEARL,
        "&fKlein Star Vier",
        "&7Holds up to 48,000 magic essence",
        "",
        "&7[::::::::::] 0/0 ME (0.00%)"
    );

    public static final SlimefunItemStack KLEIN_STAR_SPHERE = new SlimefunItemStack(
        "KLEIN_STAR_SPHERE",
        Material.ENDER_PEARL,
        "&fKlein Star Sphere",
        "&7Holds up to 100,000 magic essence",
        "",
        "&7[::::::::::] 0/0 ME (0.00%)"
    );

    public static final SlimefunItemStack KLEIN_STAR_OMEGA = new SlimefunItemStack(
        "KLEIN_STAR_OMEGA",
        Material.ENDER_EYE,
        "&fKlein Star &lOmega",
        "&7Holds up to 250,000 magic essence",
        "",
        "&7[::::::::::] 0/0 ME (0.00%)"
    );
    //endregion

    private Items() {}
}
