package shadowdev.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.shadow.api.ItemCustom;
import dev.shadow.api.ObjectSet;
import net.md_5.bungee.api.ChatColor;
import shadowdev.player.GamePlayer;
import shadowdev.player.PlayerNA;

public class EconomyManager {

	static HashMap<ItemCustom, Integer> sellPrices = new HashMap<ItemCustom, Integer>();
	static List<Auction> aucHouse = new ArrayList<Auction>();
	
	public static int getSellPrice(ItemCustom c) {
		if (sellPrices.get(c) != null) return sellPrices.get(c);
		else {
			String s = c.getDisplayName();
			if (s.contains("[CMN]"))
				return 25;
			else if (s.contains("[UNC]"))
				return 70;
			else if (s.contains("[RAR]"))
				return 500;
			else if (s.contains("[EPC]"))
				return 10000;
			else if (s.contains("[LGD]"))
				return 50000;
		}
		return 10;
	}
	
	public static void addAuction(Auction a) {
		aucHouse.add(a);
		a.owner.sendMessage(ChatColor.GREEN + "Started auction for " + a.item.getDisplayName());
	}
	
	public static List<Auction> getAuctions() {
		return aucHouse;
	}
	
	public static void startEcon() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				List<Auction> exp = new ArrayList<Auction>();
				for (int i = 0; i < aucHouse.size(); i++) {
					Auction ary = aucHouse.get(i);
					ary.time--;
					if (ary.time <= 0) {
						ary.endAuc();
						exp.add(ary);
					}
				}
				for (int i = 0; i < exp.size(); i++) {
					aucHouse.remove(exp.get(i));
				}
			}
		}.runTaskTimer(ServerManager.instance, 0, 20);
	}
	
	
	
}
