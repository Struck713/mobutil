package dev.nstruck.mobutil.entity;

import dev.nstruck.mobutil.event.CustomDragonBlockEvent;
import dev.nstruck.mobutil.event.CustomDragonCollideEvent;
import dev.nstruck.mobutil.event.CustomEntityClickEvent;
import dev.nstruck.mobutil.navigation.MoveableEntity;
import dev.nstruck.mobutil.navigation.NavigationPoint;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class CustomDragon extends EntityEnderDragon implements MoveableEntity {

    public static EnderDragon spawn(Location location)
    {
        World mcWorld = ((CraftWorld) location.getWorld()).getHandle();

        CustomDragon customEntity = new CustomDragon(mcWorld);
        customEntity.setLocation(location.getX(), location.getY(),location.getZ(), location.getYaw(), location.getPitch());

        ((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);

        mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        customEntity.setCustomName("");
        customEntity.setCustomNameVisible(false);

        return (EnderDragon) customEntity.getBukkitEntity();
    }

    public static CustomDragon spawnCustom(Location location)
    {
        World mcWorld = ((CraftWorld) location.getWorld()).getHandle();

        CustomDragon customEntity = new CustomDragon(mcWorld);
        customEntity.setLocation(location.getX(), location.getY(),location.getZ(), location.getYaw(), location.getPitch());

        ((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);

        mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        customEntity.setCustomName("");
        customEntity.setCustomNameVisible(false);

        return customEntity;
    }

    public CustomDragon(World world) {
        super(world);
        this.bx = true;
    }


    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(1.5D);
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

    //stop damage
    @Override
    public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
        return false;
    }

    private double x;
    private double y;
    private double z;
    private double speed = 1;

    public void moveTo(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void moveTo(NavigationPoint point) {
        this.moveTo(point.getX(), point.getY(), point.getZ());
    }

    @Override
    public Location getLocation() {
        return this.getBukkitEntity().getLocation();
    }

    @Override
    public void m() {
        this.a = this.x;
        this.b = this.y;
        this.c = this.z;

        this.motX *= speed;
        this.motY *= speed;
        this.motZ *= speed;

        this.bw = true;

        List<Block> blocks = getBlocksInBounds();
        if (!blocks.isEmpty()) {
            Bukkit.getPluginManager().callEvent(new CustomDragonBlockEvent(this, blocks));
        }

        super.m();
    }

    private List<org.bukkit.block.Block> getBlocksInBounds()
    {
        org.bukkit.World world = this.getWorld().getWorld();
        List<Block> blocks = new ArrayList<>();

        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        int i = MathHelper.floor(axisAlignedBB.a);
        int j = MathHelper.floor(axisAlignedBB.b);
        int k = MathHelper.floor(axisAlignedBB.c);
        int l = MathHelper.floor(axisAlignedBB.d);
        int i1 = MathHelper.floor(axisAlignedBB.e);
        int j1 = MathHelper.floor(axisAlignedBB.f);

        for (int k1 = i; k1 <= l; ++k1)
        {
            for (int l1 = j; l1 <= i1; ++l1)
            {
                for (int i2 = k; i2 <= j1; ++i2)
                {
                    Block block = world.getBlockAt(k1, l1, i2);
                    org.bukkit.Material type = block.getType();

                    if (type != org.bukkit.Material.AIR)
                    {
                        blocks.add(block);
                    }
                }
            }
        }

        return blocks;
    }


    //stop fire
    @Override
    public void setOnFire(int i) { }

    //stop collisions
    @Override
    public void collide(Entity entity) {
        org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();
        if (!(bukkitEntity instanceof Player)) {
            return;
        }
        Player player = (Player)bukkitEntity;
        Bukkit.getPluginManager().callEvent(new CustomDragonCollideEvent(this, player));
    }

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
