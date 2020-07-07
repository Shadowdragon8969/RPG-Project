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

public class Avalanche extends Skill {

	public Avalanche() {
		super(true, "Avalanche", Material.IRON_SWORD, "swordBash", "L", "L", "L", "L", "R");
		setDescription("Slam your sword diagonally downward", "crushing any opponent caught", "Pattern: L L L L R");
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
			int t = 45;
			boolean hashit = false;
			Location l = pl.getEyeLocation();
			@Override
			public void run() {
				if (!pl.isOnline()) {
					this.cancel();
					return;
				}
				t -= 10;
				double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * 2.5;
				double y = (pl.getEyeLocation().getDirection().getY() + Math.sin(Math.toRadians(t))) * 2.5;
				double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * 2.5;
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), Color.AQUA, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
				if (!hashit) {
					for (LivingEntity e : pl.getWorld().getLivingEntities()) {
						if (e != pl && e.getBoundingBox().contains(l.getX() + x, l.getY() + y, l.getZ() + z)) {
							ServerManager.damageEntity(e, ((ToolX)CraftingTableManager.isHoldingCustomItem(pl)).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 1.12, pl);
							hashit = true;
						}
					}
				}
				if (t < -30) {
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
