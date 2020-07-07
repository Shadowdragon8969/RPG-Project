package shadowdev.world.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ChasePlayerScript implements EntityLivingScript {

	boolean attack = false;
	double agro = 18;
	
	public ChasePlayerScript(boolean attack, double agro) {
		this.attack = attack;
		this.agro = agro;
	}

	@Override
	public void whenAlive(Creature c, LivingEntity e, Location l, World w) {
		if (e.isDead()) return;
		for (Entity ent : e.getNearbyEntities(agro, agro, agro)) {
			if (ent instanceof Player) {
				Location el = ent.getLocation();
				Vector dir = l.toVector().subtract(el.toVector()).normalize();
				double x = dir.getX();
				double z = dir.getZ();
				Location n = l.clone();
				n.setYaw(180f - (float) Math.toDegrees(Math.atan2(x, z)));
				//System.out.println(angYaw);
				double y = 0;
				if (!Double.isFinite(n.getYaw())) n.setYaw(0);
				e.teleport(n);
				if (e.getLocation().distance(ent.getLocation()) < c.attackRange && attack) {
					((Player) ent).damage(c.getAttack(), e);
				}
				if (ent.getLocation().getY() > e.getLocation().getY() && e.isOnGround() && ent.isOnGround()) {
					y = 0.78;
				}else y = -0.2;
					e.setVelocity((e.getLocation().getDirection().multiply(c.spd / 12).setY(y)));
			}
		}
	}
	
}
