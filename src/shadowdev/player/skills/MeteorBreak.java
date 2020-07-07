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

public class MeteorBreak extends Skill {

	public MeteorBreak() {
		super(true, "Meteor Break", Material.DIAMOND, "metbreak", "R", "L", "L", "L", "R");
		setDescription("A hard to follow seven", "hit combo move", "Pattern: R L L L R");
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
					ServerManager.damageEntity(e, ((ToolX)CraftingTableManager.isHoldingCustomItem(pl)).getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() * 1.92, pl);
				}
			}
		}
		
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				if (!pl.isOnline()) {
					this.cancel();
					return;
				}
				t++;
				pl.getWorld().spawnParticle(Particle.CLOUD, pl.getLocation(), 1);
				if (t > 30) this.cancel();
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				if (!pl.isOnline()) {
					this.cancel();
					return;
				}
				t++;
				if (t == 1) {
					SwordSkillUtils.slashRightDiagDown(pl, Color.ORANGE, 2.5, 1.64);
				}
				if (t == 10) {
					SwordSkillUtils.slashRightDiagUp(pl, Color.ORANGE, 2.5, 1.64);
				}
				if (t == 20) {
					SwordSkillUtils.stab(pl, Color.RED, 2.5, 0.8);
				}
				if (t == 30) {
					SwordSkillUtils.slashDownUp(pl, Color.ORANGE, 2.5, 1.92);
				}
				if (t == 40) {
					SwordSkillUtils.stab(pl, Color.RED, 2.5, 1.1);
				}
				if (t == 50) {
					SwordSkillUtils.slashLeftRight(pl, Color.ORANGE, 2.5, 1.92);
				}
				if (t == 60) {
					SwordSkillUtils.slashRightLeft(pl, Color.ORANGE, 2.5, 1.92);
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
}
