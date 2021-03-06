package shadowdev.player.skills;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.shadow.api.AttributeType;
import dev.shadow.api.CraftingTableManager;
import shadowdev.item.ToolX;
import shadowdev.player.Skill;
import shadowdev.server.ServerManager;
import shadowdev.utils.SwordSkillUtils;

public class RageSpike extends Skill {

	public RageSpike() {
		super(true, "Rage Spike", Material.FLINT, "ragespike", "R", "R", "R", "L", "R");
		setDescription("A basic, weak skill that leaps at the enemy and", "follows with an upward strike.", "Pattern: R R R L R");
	}
	
	@Override
	public void useSkill(Player pl) {
		for (double t = 1; t < 3.5; t += 0.1) {
			Location l = pl.getEyeLocation();
			double x = pl.getEyeLocation().getDirection().getX() * t;
			double y = pl.getEyeLocation().getDirection().getY() * t;
			double z = pl.getEyeLocation().getDirection().getZ() * t;
			l.add(x, y, z);
			for (LivingEntity e : pl.getWorld().getLivingEntities()) {
				if (e != pl && e.getBoundingBox().contains(l.getX(), l.getY(), l.getZ())) {
					ServerManager.damageEntity(e, ((ToolX)CraftingTableManager.isHoldingCustomItem(pl)).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 1.12, pl);
				}
			}
		}
		new BukkitRunnable() {
			double t = 1;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) {
					this.cancel();
					return;
				}
				t += 0.3;
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), Color.AQUA, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, ((ToolX)CraftingTableManager.isHoldingCustomItem(pl)).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 1.12, pl);
							hashit = true;
						}
					}
				}
				if (t > 3) {
					SwordSkillUtils.slashDownUp(pl, Color.AQUA, 2, 1.12);
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
