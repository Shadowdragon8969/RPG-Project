package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class KoboldMace extends ToolX {

	public KoboldMace() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Kobold Mace", "koboldmace", Material.IRON_SHOVEL, 8.0f, true, "An iron mace wielded by Kobold Knights. ");
		noDurability = true;
		attackSpeed = -3.2;
	}
	
	@Override
	public int getEnhancements() {
		return 8;
	}
	
}
