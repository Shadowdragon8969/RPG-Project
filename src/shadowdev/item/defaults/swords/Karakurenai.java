package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class Karakurenai extends ToolX {

	public Karakurenai() {
		super(ChatColor.GOLD + "[LGD] " + ChatColor.WHITE + "Karakurenai", "karakurenai", Material.IRON_SWORD, 145.0f, true, "A razor sharp katana with a decent reach. It glimmers like a star.");
		noDurability = true;
		attackSpeed = -1.4;
	}
	
	@Override
	public int getEnhancements() {
		return 30;
	}
	
}
