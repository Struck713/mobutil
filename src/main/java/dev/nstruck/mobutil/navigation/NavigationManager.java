package dev.nstruck.mobutil.navigation;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

public class NavigationManager<T extends MoveableEntity> {

    private T entity;
    private List<NavigationPoint> points;
    private BukkitTask task;

    private int currentPoint;

    public NavigationManager(T entity, NavigationPoint... points) {
        this.entity = entity;
        this.points = Arrays.asList(points);
    }

    public void start(JavaPlugin plugin) {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                NavigationPoint point = points.get(currentPoint);
                entity.moveTo(point);

                if (point.isClose(entity.getLocation())) {
                    currentPoint++;
                    if (currentPoint >= points.size()) {
                        currentPoint = 0;
                    }
                }
            }
        }.runTaskTimer(plugin, 20l, 20l);
    }

    public void stop() {
        this.task.cancel();
    }


    public T getEntity() {
        return entity;
    }
}
