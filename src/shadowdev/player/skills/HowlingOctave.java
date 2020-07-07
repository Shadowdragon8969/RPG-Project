package shadowdev.player.skills;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import shadowdev.player.Skill;
import shadowdev.server.ServerManager;
import shadowdev.utils.SwordSkillUtils;

public class HowlingOctave extends Skill {

	public HowlingOctave() {
		super(true, "Howling Octave", Material.FERMENTED_SPIDER_EYE, "howlingOctave", "L", "L", "R", "L", "R");
		setDescription("Five high-speed continuous thrusts then cut downward", ", upward, before another full force upward cut", "Pattern: L L L R");
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
					SwordSkillUtils.stab(user, Color.ORANGE, 3, 1.32);
				}
				if (t == 10) {
					SwordSkillUtils.stab(user, Color.ORANGE, 3, 1.32);
				}
				if (t == 20) {
					SwordSkillUtils.stab(user, Color.ORANGE, 3, 1.32);
				}
				if (t == 30) {
					SwordSkillUtils.stab(user, Color.ORANGE, 3, 1.32);
				}
				if (t == 40) {
					SwordSkillUtils.stab(user, Color.ORANGE, 3, 1.32);
				}
				if (t == 50) {
					SwordSkillUtils.slashUpDown(user, Color.ORANGE, 2.5, 1.2);
				}
				if (t == 60) {
					SwordSkillUtils.slashDownUp(user, Color.ORANGE, 2.5, 1.2);
				}
				if (t > 70) {
					SwordSkillUtils.slashUpDown(user, Color.ORANGE, 2.5, 1.2);
					this.cancel();
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 1);
	}
	
}
