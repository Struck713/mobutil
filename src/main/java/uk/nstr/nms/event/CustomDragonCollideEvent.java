package uk.nstr.nms.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.nstr.nms.entity.CustomDragon;

import java.util.List;

public class CustomDragonCollideEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private CustomDragon entity;
    private Player player;

    public CustomDragonCollideEvent(CustomDragon entity, Player player) {
        this.entity = entity;
        this.player = player;
    }

    public CustomDragon getEntity() {
        return entity;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
