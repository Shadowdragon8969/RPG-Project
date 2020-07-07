package shadowdev.world.entity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import dev.shadow.api.CraftingTableManager;
import net.minecraft.server.v1_15_R1.EntityCreature;
import net.minecraft.server.v1_15_R1.EntityHuman;
import net.minecraft.server.v1_15_R1.GenericAttributes;
import net.minecraft.server.v1_15_R1.Particles;
import net.minecraft.server.v1_15_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_15_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_15_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_15_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_15_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_15_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_15_R1.PathfinderGoalRandomStrollLand;

public class CreatureLivingBase extends EntityCreature {
	
	Creature base;
	public Entity child;
	public int stunTicks = 0, age = 0;
	
	protected CreatureLivingBase(Creature base, org.bukkit.World world) {
		super(base.et, ((CraftWorld)world).getHandle());
		this.base = base;
		this.getNavigation().d(true);
		if (base.hostile) {
			this.goalSelector.a(1, new PathfinderGoalFloat(this));
		    //this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
		    this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0d, true));
		    this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 0.8D));
		    this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		    this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
		    this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, new Class[0]));
		    this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
		}
		getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE).setValue(base.atk);
		getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(base.spd * 0.30000001192092896D);
	}
	
	
	
	@Override
	public void tick() {
		ticksLived = age;
		for (EntityScript es : base.getScripts()) {
			if (es instanceof EntityLivingScript) {
				((EntityLivingScript) es).whenAlive(base, getBukkitLivingEntity(), getBukkitLivingEntity().getLocation(), this.world.getWorld());
			}
		}
		if (stunTicks > 0) {
			stunTicks --;
			world.addParticle(Particles.CRIT, locX(), locY(), locZ(), 0, 0, 0);
		}
		else
		super.tick();
		if (ticksLived >= 2400 && base.doesRespawn()) {
			getBukkitEntity().remove();
			if (child != null) child.remove();
		}
	}
	
	public void globalTick() {
		age++;
		if (age >= 2400 && base.doesRespawn()) {
			getBukkitEntity().remove();
			if (child != null) child.remove();
		}
	}
	
	public void onSpawn() {
		if (base.parent != null) {
			child = world.getWorld().spawn(new Location(world.getWorld(), this.locX(), this.locY(), this.locZ()), base.parent);
			CraftingTableManager.parentMap.put(child, this);
		}
	}
	
	public static CreatureLivingBase fromLiving(org.bukkit.entity.LivingEntity e) {
		return (CreatureLivingBase) ((CraftLivingEntity)e).getHandle();
	}
	
}
