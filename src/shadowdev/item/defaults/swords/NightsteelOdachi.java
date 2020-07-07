package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class NightsteelOdachi extends ToolX {

	public NightsteelOdachi() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Nightsteel Odachi", "nsOdachi", Material.IRON_SWORD, 12.0f, true, "A lightweight steel odachi wielded by samurai. The steel of the blade is ice cold.");
		noDurability = true;
		attackSpeed = -1.8;
	}
	
	@Override
	public int getEnhancements() {
		return 9;
	}
	
}
