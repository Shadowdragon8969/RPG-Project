package shadowdev.player.skills;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import shadowdev.player.Skill;
import shadowdev.server.ServerManager;
import shadowdev.utils.SwordSkillUtils;

public class SharpNail extends Skill {

	public SharpNail() {
		super(true, "Sharp Nail", Material.IRON_INGOT, "sharpnail", "L", "L", "L", "R", "L");
		setDescription("A strong three hit combo", "Pattern: L L L R L");
	}
	
	@Override
	public void useSkill(Player user) {
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				if (!user.isOnline()) {
					this.cancel();
					return;
				}
				t++;
				if (t == 1) {
					SwordSkillUtils.slashLeftDiagUp(user, Color.RED, 2.5, 1.8);
				}
				if (t == 10) {
					SwordSkillUtils.slashLeftRight(user, Color.RED, 2.5, 1.8);
				}
				if (t == 20) {
					SwordSkillUtils.slashLeftDiagDown(user, Color.RED, 2.5, 1.8);
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
