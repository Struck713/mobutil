package uk.nstr.nms;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import uk.nstr.nms.command.NpcCommand;
import uk.nstr.nms.entity.CustomSkeleton;
import uk.nstr.nms.entity.CustomVillager;
import uk.nstr.nms.entity.CustomWitch;
import uk.nstr.nms.entity.CustomZombie;
import uk.nstr.nms.event.CustomEntityClickEvent;
import uk.nstr.nms.util.NMSUtil;

import java.util.ArrayList;
import java.util.List;

public class NMSEntities extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        NMSUtil.init();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(this, this);

        getCommand("npc").setExecutor(new NpcCommand());
    }

    @EventHandler
    public void onInteract(CustomEntityClickEvent event) {
        Player player = event.getPlayer();
        CustomEntityClickEvent.ClickType type = event.getType();

        if (type == CustomEntityClickEvent.ClickType.LEFT) {
            player.sendMessage("you left clicked!");
            return;
        }

        if (type == CustomEntityClickEvent.ClickType.RIGHT) {
            player.sendMessage("you right clicked!");
            return;
        }
    }

}
