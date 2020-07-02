package uk.nstr.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import uk.nstr.nms.command.NpcCommand;
import uk.nstr.nms.event.CustomEntityClickEvent;
import uk.nstr.nms.util.NMSUtil;

public class NMSEntities extends JavaPlugin implements Listener {

    private NpcCommand command;

    @Override
    public void onEnable() {
        NMSUtil.init();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(this, this);

        this.command = new NpcCommand(this);
        getCommand("npc").setExecutor(command);
    }

    @Override
    public void onDisable() {
        this.command.resetTasksAndEntities();
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
