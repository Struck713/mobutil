package uk.nstr.nms.command;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import uk.nstr.nms.entity.*;

import java.util.ArrayList;
import java.util.List;

public class NpcCommand implements CommandExecutor {

    private List<LivingEntity> entityLivings;

    public NpcCommand() {
        this.entityLivings = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player)sender;
        if (command.getName().equalsIgnoreCase("npc")) {
                if (args.length == 0) {
                    player.sendMessage("/npc <spawn/kill> (zombie/skeleton/villager/witch)");
                    return true;
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("kill")) {
                        entityLivings.forEach(entity -> {
                            EntityLiving livingEntity = ((CraftLivingEntity) entity).getHandle();
                            player.sendMessage(livingEntity.getName());
                            livingEntity.die(DamageSource.MAGIC);
                            entity.remove();
                        });
                        player.sendMessage(String.format("Killed %d entities.", entityLivings.size()));
                        entityLivings.clear();
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
                        if (args[1].equalsIgnoreCase("slime")) {
                            Slime slime = CustomSlime.spawn(player.getLocation());
                            this.entityLivings.add(slime);
                            player.sendMessage("Spawned custom Slime.");
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

}
