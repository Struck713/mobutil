package uk.nstr.nms.entity;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.PluginManager;
import uk.nstr.nms.event.CustomEntityClickEvent;

public class CustomVillager extends EntityVillager {

    public static Villager spawn(Location location)
    {
        World mcWorld = ((CraftWorld) location.getWorld()).getHandle();

        CustomVillager customEntity = new CustomVillager(mcWorld);
        customEntity.setLocation(location.getX(), location.getY(),location.getZ(), location.getYaw(), location.getPitch());

        ((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);

        mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        customEntity.setCustomName("");
        customEntity.setCustomNameVisible(false);

        return (Villager) customEntity.getBukkitEntity();
    }

    public CustomVillager(World world) {
        super(world);
        ((Navigation)this.getNavigation()).b(true);
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.setSize(0.6F, 1.95F);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(35.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0);
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource == null || damagesource.getEntity() == null) {
            return false;
        }
        Entity damageSource = damagesource.getEntity();
        CraftEntity entity = damageSource.getBukkitEntity();
        if (entity == null) {
            return false;
        }
        org.bukkit.entity.Entity entity1 = entity;
        if (!(entity1 instanceof Player)) {
            return false;
        }

        Player player = (Player)entity1;
        org.bukkit.entity.Entity entity2 = getBukkitEntity();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.callEvent(new CustomEntityClickEvent(player, (LivingEntity)entity2, CustomEntityClickEvent.ClickType.LEFT));

        if (damagesource == DamageSource.MAGIC) {
            this.die(DamageSource.MAGIC);
            return true;
        }
        return false;
    }

    @Override
    public boolean a(EntityHuman entityhuman) {

        Player player = (Player)entityhuman.getBukkitEntity();
        org.bukkit.entity.Entity entity2 = getBukkitEntity();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.callEvent(new CustomEntityClickEvent(player, (LivingEntity)entity2, CustomEntityClickEvent.ClickType.RIGHT));
        return false;
    }

    @Override
    public void die(DamageSource damagesource) {
        Entity entity = damagesource.getEntity();
        EntityLiving entityliving = this.bt();
        if (this.aW >= 0 && entityliving != null) {
            entityliving.b(this, this.aW);
        }

        if (entity != null) {
            entity.a(this);
        }

        this.aP = true;
        this.bs().g();
    }

    //stop fire
    @Override
    public void setOnFire(int i) { }

    //stop collisions
    @Override
    public void collide(Entity entity) { }

    @Override
    public boolean ad() {
        return false;
    }

    //sounds
    @Override
    protected String z() {
        return "";
    }

    @Override
    protected String bo() {
        return "";
    }

    @Override
    protected String bp() {
        return "";
    }

}
