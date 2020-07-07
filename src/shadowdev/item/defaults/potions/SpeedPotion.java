package shadowdev.item.defaults.potions;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.PotionX;
import shadowdev.player.Buff;

public class SpeedPotion extends PotionX {

	public SpeedPotion() {
		super(new Buff("Speed Boost", 1, 6000) {
			
			@Override
			public void onLoss(Player p) {
				if (!p.hasPotionEffect(PotionEffectType.SPEED)) return;
				p.removePotionEffect(PotionEffectType.SPEED);
				p.sendMessage(ChatColor.RED + "Speed Boost has worn off");
			}
			
			@Override
			public void onApply(Player p) {
				if (p.hasPotionEffect(PotionEffectType.SPEED)) {
					p.sendMessage(ChatColor.YELLOW + "*The Potion had no effect*");
					return;
				}
				p.sendMessage(ChatColor.GREEN + "Applied Speed Boost");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
			}
		}, "spdPotA");
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Speed Potion");
		setLore("A bitter drink that", "enhances speed.", ChatColor.GREEN + "Effect: " + ChatColor.GOLD + "Boosts speed by 25% for 2 minutes.");
	}

	
	
}
