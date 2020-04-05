package dev.walshy.busyend.items;

import dev.walshy.busyend.Items;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DragonEssence extends SimpleSlimefunItem<ItemUseHandler> {

    public DragonEssence() {
        super(Items.BUSY_END_CATEGORY, Items.DRAGON_ESSENCE, RecipeType.MOB_DROP, new ItemStack[] {
            null, null, null,
            null, new CustomItem(Material.DRAGON_HEAD, "&5Ender Dragon"), null,
            null, null, null
        });
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return PlayerRightClickEvent::cancel;
    }
}
