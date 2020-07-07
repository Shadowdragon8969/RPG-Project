package shadowdev.item.defaults.potions;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.PotionX;
import shadowdev.player.Buff;
import shadowdev.player.GamePlayer;
import shadowdev.player.GameStat;
import shadowdev.server.ServerManager;

public class StaminaPotion extends PotionX {

	public StaminaPotion() {
		super(new Buff("Stamina Boost", 1, 1) {
			
			@Override
			public void onLoss(Player p) {
				
			}
			
			@Override
			public void onApply(Player p) {
				GamePlayer gp = ServerManager.getPlayer(p);
				
				double hp = gp.getValue("stam").getLevel();
				int max = 90 + gp.getAttribute("stamina").getLevel() * 10;
				hp += max * 0.15;
				if (hp > max) hp = max;
				gp.setValue("stam", new GameStat("stam", 0, Math.max(Math.min((int) Math.ceil(hp), max), 0), "Stamina"));
				
				p.setHealth(Math.min(hp / max * 20, 20));
			}
		}, "hpPotA");
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Stamina Potion");
		setLore("A sweet potion that", "restores the user's stamina.", ChatColor.GREEN + "Effect: " + ChatColor.GOLD + "Restores Stamina by 15%");
	}

	
	
}
