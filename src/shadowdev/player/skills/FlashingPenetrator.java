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

public class FlashingPenetrator extends Skill {

	public FlashingPenetrator() {
		super(true, "Flashing Penetrator", Material.SPECTRAL_ARROW, "flashPene", "L", "R", "R", "R", "R");
		setDescription("A strong lunge and thrust", "with your sword.", "Pattern: L R R R R");
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
		SwordSkillUtils.stab(pl, Color.BLUE, 6, 1.8);
		SwordSkillUtils.stab(pl, Color.YELLOW, 6, 1.8);
	}
}
