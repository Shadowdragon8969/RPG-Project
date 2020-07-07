package shadowdev.server;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import dev.shadow.api.ItemCustom;
import dev.shadow.api.ObjectSet;
import net.md_5.bungee.api.ChatColor;
import shadowdev.player.GamePlayer;
import shadowdev.player.PlayerNA;

public class Auction {
	
	
	
	int startingBid;
	Player owner, bidder = null;
	ItemCustom item;
	List<ObjectSet<OfflinePlayer, Integer>> bids = new ArrayList<ObjectSet<OfflinePlayer, Integer>>();
	int time;
	
	public Auction(Player owner, ItemCustom item, int startingBid, int timeM) {
		this.owner = owner;
		this.item = item;
		ServerManager.getPlayer(owner).removeItem(item);
		this.startingBid = Integer.max(startingBid, 10);
		time = timeM * 60;
		bids.add(new ObjectSet<OfflinePlayer, Integer>(new PlayerNA(), -1));
	}
	
	public ItemCustom getItem() {
		return item;
	}
	
	public double getHighestBid() {
		if (bids.size() == 1) {
			return (double) startingBid / 1.2;
		}
		return bids.get(Math.max(bids.size() - 1, 0)).value;
	}
	
	public int getHighestBidI() {
		return (int) getHighestBid();
	}
	
	public OfflinePlayer getTopBidder() {
		return bids.get(Math.max(bids.size() - 1, 0)).key;
	}
	
	public void tryPlaceBid(Player bidder, int amount) {
		GamePlayer gp = ServerManager.getPlayer(bidder);
		if (owner == bidder) {
			bidder.sendMessage(ChatColor.RED + "You can't bid on your own auction");
			return;
		}
		if (getTopBidder() == bidder) {
			bidder.sendMessage(ChatColor.RED + "You already have the highest bid for this auction");
			return;
		}
		if (bids.get(Math.max(bids.size() - 1, 0)).value <= 0 && amount >= startingBid) {
			messageBidders(bidder, amount);
			bids.add(new ObjectSet<OfflinePlayer, Integer>(bidder, amount));
			gp.getAttribute("cor").addLevels(-amount);
			this.bidder = bidder;
			bidder.sendMessage(ChatColor.GREEN + "Bid of " + amount + ChatColor.GOLD + "コル" + ChatColor.GREEN + "placed on " + item.getDisplayName());
			if (owner.isOnline()) {
				owner.sendMessage(ChatColor.GREEN + bidder.getDisplayName() + " has placed a bid of " + amount + ChatColor.GOLD + "コル" + ChatColor.GREEN + " on your " + item.getDisplayName());
			}
			return;
		}
		else {
			if (amount < (double) bids.get(Math.max(bids.size() - 1, 0)).value * 1.2) {
				bidder.sendMessage(ChatColor.RED + "You must bid at least " + (int) ((double) bids.get(bids.size() - 1).value * 1.2) + ChatColor.GOLD + "コル");
			}else {
				messageBidders(bidder, amount);
				bids.add(new ObjectSet<OfflinePlayer, Integer>(bidder, amount));
				gp.getAttribute("cor").addLevels(-amount);
				this.bidder = bidder;
				bidder.sendMessage(ChatColor.GREEN + "Bid of " + amount + ChatColor.GOLD + "コル" + ChatColor.GREEN + "placed on " + item.getDisplayName());
				if (owner.isOnline()) {
					owner.sendMessage(ChatColor.GREEN + bidder.getDisplayName() + " has placed a bid of " + amount + ChatColor.GOLD + "コル" + ChatColor.GREEN + " on your " + item.getDisplayName());
				}
				return;
			}
		}
	}
	
	void messageBidders(Player newBid, int amount) {
		List<OfflinePlayer> checked = new ArrayList<OfflinePlayer>();
		for (int i = Math.max((int) (bids.size() - 1), 0); i > 0; i--) {
			ObjectSet<OfflinePlayer, Integer> bid = bids.get(i);
			if (checked.contains(bid.key)) continue;
			if (bid.key.isOnline() && bid.key != newBid) {
				((Player)bid.key).sendMessage(newBid.getName() + " has outbid you by " + (amount - bid.value) + " on " + item.getDisplayName());
			}
			checked.add(bid.key);
		}
	}
	
	public void endAuc() {
		if (bids.get(Math.max(bids.size() - 1, 0)).value > 0) {
			if (bidder != null && bidder.isOnline()) {
				GamePlayer gp = ServerManager.getPlayer(bidder);
				gp.addItem(item);
				bidder.sendMessage(ChatColor.GREEN + "You won " + owner.getName() + "'s auction for a " + item.getDisplayName() + "!");
			}else
				if (!bidder.isOnline()) {
					GamePlayer gp = new GamePlayer(bidder.getUniqueId());
					gp.addItem(item);
					gp.save();
				}
			if (owner != null && owner.isOnline()) {
				GamePlayer gp = ServerManager.getPlayer(owner);
				owner.sendMessage(ChatColor.GREEN + "Auction of " + item.getDisplayName() + ChatColor.GREEN + " sold to " + bidder.getName() + " for " + bids.get(bids.size() - 1).value + ChatColor.GOLD + "コル");
				gp.getAttribute("cor").addLevels(bids.get(bids.size() - 1).value);
			}else
				if (!owner.isOnline()) {
					GamePlayer gp = new GamePlayer(owner.getUniqueId());
					gp.getAttribute("cor").addLevels(bids.get(bids.size() - 1).value);
					gp.save();
				}
		}else {
			if (owner != null && owner.isOnline()) {
				GamePlayer gp = ServerManager.getPlayer(owner);
				owner.sendMessage(ChatColor.YELLOW + "Auction of " + item.getDisplayName() + ChatColor.YELLOW + " ended with no bids");
				gp.addItem(item);
			}else
				if (!owner.isOnline()) {
					GamePlayer gp = new GamePlayer(owner.getUniqueId());
					gp.addItem(item);
					gp.save();
				}
		}
		for (ObjectSet<OfflinePlayer, Integer> bid : bids) {
			if (!bid.key.getName().equalsIgnoreCase(bidder.getName()) && !bid.key.getName().equalsIgnoreCase("N/A")) {
				GamePlayer gp;
				if (!bid.key.isOnline()) gp = new GamePlayer(bid.key.getUniqueId());
				else gp = ServerManager.getPlayer((Player) bid.key);
				gp.getAttribute("cor").addLevels(bid.value);
				if (!bid.key.isOnline()) gp.save();
			}
		}
	}
	
}
