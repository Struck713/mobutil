package uk.nstr.nms.command;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import uk.nstr.nms.entity.*;
import uk.nstr.nms.navigation.NavigationManager;
import uk.nstr.nms.navigation.NavigationPoint;

import java.util.ArrayList;
import java.util.List;

public class NpcCommand implements CommandExecutor {

    private JavaPlugin plugin;
    private List<LivingEntity> entityLivings;
    private List<NavigationManager> tasks;

    public NpcCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.entityLivings = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player)sender;
        if (command.getName().equalsIgnoreCase("npc")) {
                if (args.length == 0) {
                    player.sendMessage("/npc <spawn/kill> (zombie/skeleton/villager/witch/dragon)");
                    return true;
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("kill")) {
                        int entities = entityLivings.size();
                        this.resetTasksAndEntities();
                        player.sendMessage(String.format("Killed %d entities.", entities));
                        return true;
                    }
                    player.sendMessage("Invalid command usage!");
                    return true;
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("spawn")) {
                        if (args[1].equalsIgnoreCase("zombie")) {
                            Zombie zombie = CustomZombie.spawn(player.getLocation());
                            this.entityLivings.add(zombie);
                            player.sendMessage("Spawned custom Zombie.");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("skeleton")) {
                            Skeleton skeleton = CustomSkeleton.spawn(player.getLocation());
                            this.entityLivings.add(skeleton);
                            player.sendMessage("Spawned custom Skeleton.");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("witch")) {
                            Witch witch = CustomWitch.spawn(player.getLocation());
                            this.entityLivings.add(witch);
                            player.sendMessage("Spawned custom Witch.");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("villager")) {
                            Villager villager = CustomVillager.spawn(player.getLocation());
                            this.entityLivings.add(villager);
                            player.sendMessage("Spawned custom Villager.");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("dragon")) {
                            CustomDragon customDragon = CustomDragon.spawnCustom(player.getLocation());
                            customDragon.setSpeed(0.95D);

                            NavigationManager<CustomDragon> navigationManager = new NavigationManager<>(
                                    customDragon,
                                    new NavigationPoint(50, 100, 50),
                                    new NavigationPoint(100, 100, 50),
                                    new NavigationPoint(100, 100, 100),
                                    new NavigationPoint(50, 100, 100));
                            navigationManager.start(this.plugin);

                            this.tasks.add(navigationManager);
                            this.entityLivings.add((EnderDragon)customDragon.getBukkitEntity());
                            player.sendMessage("Spawned custom EnderDragon.");
                            return true;
                        }
                        player.sendMessage("Invalid entity type.");
                        return true;
                    }
                    player.sendMessage("Invalid command usage!");
                    return true;
                }
            }
            return true;
        }

        public void resetTasksAndEntities() {
            entityLivings.forEach(entity -> {
                EntityLiving livingEntity = ((CraftLivingEntity) entity).getHandle();
                livingEntity.die(DamageSource.MAGIC);
                entity.remove();
            });
            tasks.forEach(NavigationManager::stop);
            entityLivings.clear();
        }

}
