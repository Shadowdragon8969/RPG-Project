package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class ChivalricRapier extends ToolX {

	public ChivalricRapier() {
		super(ChatColor.LIGHT_PURPLE + "[EPC] " + ChatColor.WHITE + "Chivalric Rapier", "chivalricRapier", Material.IRON_SWORD, 30.0f, true, "A well forged Argentium sword. It has little to no weight.");
		noDurability = true;
		attackSpeed = -0.8;
	}
	
	
}
