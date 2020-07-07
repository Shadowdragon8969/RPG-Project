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

public class LightningFall extends Skill {

	public LightningFall() {
		super(true, "Lightning Fall", Material.NETHER_STAR, "lightfall", "R", "L", "L", "R", "R");
		setDescription("Thrust Forward and land", "creating a surge of lightning.", "Pattern: R L L R R");
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
				CraftingTableManager.spawnParticleRGB(pl.getWorld(), Color.PURPLE, pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ(), 3, 3);
				if (pl.isOnGround()) {
					pl.getWorld().strikeLightningEffect(pl.getLocation());
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
