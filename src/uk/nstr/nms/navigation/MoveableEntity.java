package uk.nstr.nms.navigation;

import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;

public interface MoveableEntity {

    void moveTo(NavigationPoint point);

    Location getLocation();

}
