package uk.nstr.nms.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomEntityClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private LivingEntity entity;
    private ClickType type;

    public CustomEntityClickEvent(Player player, LivingEntity entity, ClickType type) {
        this.player = player;
        this.entity = entity;
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public ClickType getType() {
        return type;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum ClickType {

        LEFT, RIGHT;

    }

}
