package shadowdev.item.defaults.potions;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.PotionX;
import shadowdev.player.Buff;
import shadowdev.player.GamePlayer;
import shadowdev.player.GameStat;
import shadowdev.server.ServerManager;

public class HealthPotion extends PotionX {

	public HealthPotion() {
		super(new Buff("Health Boost", 1, 1) {
			
			@Override
			public void onLoss(Player p) {
				
			}
			
			@Override
			public void onApply(Player p) {
				GamePlayer gp = ServerManager.getPlayer(p);
				
				double hp = gp.getValue("hp").getLevel();
				int max = 250 + gp.getAttribute("hitpoint").getLevel() * 10;
				hp += max * 0.15;
				if (hp > max) hp = max;
				gp.setValue("hp", new GameStat("hp", 0, Math.max(Math.min((int) Math.ceil(hp), max), 0), "Health"));
				
				p.setHealth(Math.min(hp / max * 20, 20));
			}
		}, "hpPotA");
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Health Potion");
		setLore("A standard health potion.", ChatColor.GREEN + "Effect: " + ChatColor.GOLD + "Restores Health by 15%");
	}

	
	
}
