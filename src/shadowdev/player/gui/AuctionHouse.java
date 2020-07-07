package shadowdev.player.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import shadowdev.server.Auction;
import shadowdev.server.EconomyManager;

public class AuctionHouse extends PlayerGui implements InventoryListener {

	List<Auction> icons = new ArrayList<Auction>();
	List<Integer> amts = new ArrayList<Integer>();
	HashMap<Integer, Auction> slots = new HashMap<Integer, Auction>();
	int start = 0, prevSlot = -1;
	boolean conf = false;
	
	public AuctionHouse(Player p, int start) {
		super (p, ChatColor.GREEN + "Auction House", 54);
		//setItem(6, Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Swords", "Shows the swords that", "you own and can equip");
		//setItem(15, Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Armor", "Shows the armor that", "you own and can equip");
		//setItem(24, Material.BLAZE_POWDER, 1, ChatColor.YELLOW + "Stats", "Shows your stats a levels");
		//setItem(33, Material.BOOK, 1, ChatColor.YELLOW + "Skills", "Shows the skills that", "you have acquired and", "their attack patterns");
		//setItem(51, Material.CHEST, 1, ChatColor.YELLOW + "Inventory", "Shows the items that", "you have collected");
		setItem(45, Material.CHEST, 1, ChatColor.YELLOW + "Create Auction", "Put an item up", "for auction");
		this.start = start;
		for (int i = 0; i < EconomyManager.getAuctions().size(); i++) {
			Auction item = EconomyManager.getAuctions().get(i);
			int amt = 1;
			boolean present = false;
			if (present) continue;
			amts.add(amt);
			icons.add(item);
		}
		if (icons.size() < 1) {
			setItem(10, Material.OAK_SIGN, 1, ChatColor.RED + "Couldn't Find any auctions :/", "");
		}
		setInventoryListener(this);
		for (int i = 5; i < 54; i += 9) {
			setItem(i, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
			setItem(i + 2, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
		}
		setItem(50, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		setItem(52, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		if (start > 0) {
			setItem(2, Material.ARROW, 1, ChatColor.GREEN + "Page Up", "");
		}
		if (icons.size() - 12 - start > 0)
		setItem(47, Material.ARROW, 1, ChatColor.GREEN + "Page Down", "");
		///
		int x = 0;
		if (start + x < icons.size() && icons.get(start + x) != null) {
			System.out.println(icons.get(start + x).getItem().getDisplayName() + "|||" + amts.get(start + x));
			setItem(10, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(10, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(11, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(11, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(12, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(12, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(19, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(19, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(20, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(20, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(21, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(21, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(28, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(28, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(29, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(29, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(30, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(30, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(37, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(37, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(38, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(38, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(39, amts.get(start + x), icons.get(start + x).getItem(), ChatColor.YELLOW + "Top Bidder: " + icons.get(start + x).getTopBidder().getName(), ChatColor.YELLOW + "Bid Amount: " + ChatColor.GREEN.toString() + icons.get(start + x).getHighestBidI() + ChatColor.GOLD + "コル");
			slots.put(39, icons.get(start + x));
			x++;
		}
		///
	}
	
	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		new MainMenu(p).onClick(p, i, slot, click, inv);
		if (i != null && slot == 47) {
			start += 12;
			AuctionHouse im = new AuctionHouse(p, start);
			im.send();
		}
		if (i != null && slot == 2) {
			start -= 12;
			AuctionHouse im = new AuctionHouse(p, start);
			im.send();
		}
		if (slots.get(slot) != null) {
			if (slot == prevSlot) {
				Auction a = slots.get(slot);
				a.tryPlaceBid(p, (int) ((double)a.getHighestBid() * 1.2));
			}else {
				p.sendMessage(ChatColor.GREEN + "Click again to confirm bid");
			}
		}
		if (slot == 45) {
			AuctionMaker am = new AuctionMaker(p, 0);
			am.send();
		}
		prevSlot = slot;
	}
	
}
