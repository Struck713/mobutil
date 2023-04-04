package uk.nstr.nms.navigation;

import org.bukkit.Location;
import org.bukkit.World;

public class NavigationPoint {

    private double x;
    private double y;
    private double z;

    public NavigationPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public boolean isClose(Location location) {
        Location point = toLocation(location.getWorld());
        return point.distance(location) <= 5;
    }

}
