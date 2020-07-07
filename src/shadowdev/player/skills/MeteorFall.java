package shadowdev.player.skills;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.shadow.api.AttributeType;
import dev.shadow.api.CraftingTableManager;
import shadowdev.item.ToolX;
import shadowdev.player.Skill;
import shadowdev.server.ServerManager;
import shadowdev.utils.SwordSkillUtils;

public class MeteorFall extends Skill {

	public MeteorFall() {
		super(true, "Meteor Fall", Material.NETHERRACK, "metfall", "R", "L", "L", "R", "L");
		setDescription("Thrust Forward and land", "creating an explosion.", "Pattern: R L L R L");
	}
	
	@Override
	public void useSkill(Player pl) {
		pl.setVelocity(pl.getEyeLocation().getDirection().multiply(1.6));
		for (double t = 1; t < 3.5; t += 0.1) {
			Location l = pl.getEyeLocation();
			double x = pl.getEyeLocation().getDirection().getX() * t;
			double y = pl.getEyeLocation().getDirection().getY() * t;
			double z = pl.getEyeLocation().getDirection().getZ() * t;
			l.add(x, y, z);
			for (LivingEntity e : pl.getWorld().getLivingEntities()) {
				if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
					ServerManager.damageEntity(e, ((ToolX)CraftingTableManager.isHoldingCustomItem(pl)).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 1.12, pl);
					e.setVelocity(pl.getEyeLocation().getDirection().multiply(3));
				}
			}
		}
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (!pl.isOnline()) {
					this.cancel();
					return;
				}
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), Color.ORANGE, pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ(), 3, 3);
				if (pl.isOnGround()) {
					SwordSkillUtils.slashUpDown(pl, Color.ORANGE, 2.5, 3);
					pl.getWorld().spawnParticle(Particle.FLAME, pl.getLocation(), 50, 0, 0, 0, 1);
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getLocation().distance(pl.getLocation()) < 4) {
							ServerManager.damageEntity(e, ((ToolX)CraftingTableManager.isHoldingCustomItem(pl)).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 2.64, pl);
							e.setVelocity(pl.getEyeLocation().getDirection().multiply(3));
						}
					}
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
