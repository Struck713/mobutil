package dev.nstruck.mobutil.navigation;

import org.bukkit.Location;

public interface MoveableEntity {

    void moveTo(NavigationPoint point);

    Location getLocation();

}
