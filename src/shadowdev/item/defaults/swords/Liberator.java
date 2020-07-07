package shadowdev.item.defaults.swords;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import dev.shadow.api.IInteractable;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;
import shadowdev.player.Buff;
import shadowdev.player.GamePlayer;
import shadowdev.player.GameStat;
import shadowdev.server.ServerManager;

public class Liberator extends ToolX implements IInteractable {

	public Liberator() {
		super(ChatColor.GOLD + "[LGD] " + ChatColor.WHITE + "Liberator", "liberator", Material.IRON_SWORD, 165.0f, true, "A sword and shield combo. The weight", "of this weapon is difficult to determine.", "Ability: Holy Sword", "Consume 50 stamina for", "50% defense for 5 seconds");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 35;
	}
	
	@Override
	public void onInteract(Player user) {
		if (!user.isSneaking()) return;
		GamePlayer gp = ServerManager.getPlayer(user);
		int stam = gp.getValue("stam").getLevel();
		if (stam >= 50) {
			user.sendMessage(ChatColor.RED + "No enough stamina.");
			return;
		}
		Buff b = new Buff("Defense Boost", 50, 50) {
			
			@Override
			public void onLoss(Player p) {
				ServerManager.getPlayer(p).setAttributeBuff("defense", new GameStat("defense", 0, 0, "Buff Defense"));
				p.sendMessage(ChatColor.RED + "Defense Boost has worn off");
			}
			
			@Override
			public void onApply(Player p) {
				ServerManager.getPlayer(p).setAttributeBuff("defense", new GameStat("defense", 0, ServerManager.getPlayer(p).getAttribute("defense").getLevel(), "Buff Defense"));
			}
		};
		ServerManager.getPlayer(user).addBuff(b);
		user.sendMessage(ChatColor.AQUA + "Ability Cast: " + ChatColor.GREEN + "Defense Boost " + ChatColor.RED + "-50 Stamina");
	}
	
}
