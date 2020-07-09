package uk.nstr.nms.event;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.nstr.nms.entity.CustomDragon;

import java.util.List;

public class CustomDragonBlockEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private CustomDragon entity;
    private List<Block> collidedWith;

    public CustomDragonBlockEvent(CustomDragon entity, List<Block> collidedWith) {
        this.entity = entity;
        this.collidedWith = collidedWith;
    }

    public CustomDragon getEntity() {
        return entity;
    }

    public List<Block> getCollidedWith() {
        return collidedWith;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
