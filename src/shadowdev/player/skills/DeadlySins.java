package shadowdev.player.skills;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import shadowdev.player.Skill;
import shadowdev.server.ServerManager;
import shadowdev.utils.SwordSkillUtils;

public class DeadlySins extends Skill {

	public DeadlySins() {
		super(true, "Deadly Sins", Material.FERMENTED_SPIDER_EYE, "deadlySins", "L", "R", "L", "R", "L");
		setDescription("A seven hit skill that consists of various slashes,", "several full circle spins and a backwards somersault.", "Pattern: L R L R L");
	}
	
	@Override
	public void useSkill(Player user) {
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				t++;
				if (!user.isOnline()) {
					this.cancel();
					return;
				}
				if (t == 1) {
					SwordSkillUtils.slashLeftRight(user, Color.RED, 2.5, 1.2);
				}
				if (t == 10) {
					SwordSkillUtils.slashRightDiagDown(user, Color.RED, 2.5, 1.2);
				}
				if (t == 20) {
					SwordSkillUtils.slashUpDown(user, Color.RED, 2.5, 1.2);
				}
				if (t == 30) {
					SwordSkillUtils.spin(user, Color.RED, 2.5, 1.2);
				}
				if (t == 40) {
					SwordSkillUtils.slashLeftDiagDown(user, Color.RED, 2.5, 1.2);
				}
				if (t == 50) {
					SwordSkillUtils.slashLeftRight(user, Color.RED, 2.5, 1.2);
				}
				if (t == 60) {
					SwordSkillUtils.spin(user, Color.RED, 2.5, 1.2);
				}
				if (t > 70) {
					user.setVelocity(user.getLocation().getDirection().multiply(-3));
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
