package dev.nstruck.mobutil.event;

import dev.nstruck.mobutil.entity.CustomDragon;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
