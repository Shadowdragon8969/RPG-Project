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

public class Reaver extends Skill {

	public Reaver() {
		super(true, "Reaver", Material.ORANGE_DYE, "reaver", "R", "R", "R", "R", "L");
		setDescription("A basic, weak skill that leaps at the enemy and", "follows with an upward strike.", "Pattern: R R R R L");
	}
	
	@Override
	public void useSkill(Player pl) {
		pl.setInvulnerable(true);
		pl.setVelocity(pl.getEyeLocation().getDirection().multiply(1.5));
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
				t += 0.7;
				double x = pl.getEyeLocation().getDirection().getX() * t;
				double y = pl.getEyeLocation().getDirection().getY() * t;
				double z = pl.getEyeLocation().getDirection().getZ() * t;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), Color.ORANGE, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, ((ToolX)CraftingTableManager.isHoldingCustomItem(pl)).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 1.12, pl);
							System.out.println("hit");
							hashit = true;
						}
					}
				}
				if (t > 9) {
					pl.setInvulnerable(false);
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
