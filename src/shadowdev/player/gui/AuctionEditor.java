package shadowdev.player.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.shadow.api.ItemCustom;
import net.md_5.bungee.api.ChatColor;
import shadowdev.server.Auction;
import shadowdev.server.EconomyManager;

public class AuctionEditor extends PlayerGui implements InventoryListener {
	
	ItemCustom c;
	double time = 5, startingbid = 10;
	
	public AuctionEditor(Player viewer, ItemCustom c, int time, int startingbid) {
		super(viewer, ChatColor.YELLOW + "Setup Auction", 45);
		setItem(13, c);
		setItem(29, Material.CLOCK, 1, ChatColor.YELLOW + "Time: " + time + " Minutes", ChatColor.GREEN + "Click to increase", ChatColor.GREEN + "Right-Click to decrease");
		setItem(31, Material.EMERALD_BLOCK, 1, ChatColor.GREEN + "Confirm");
		setItem(33, Material.EMERALD, 1, ChatColor.YELLOW + "Cost: " + startingbid + ChatColor.GOLD + " コル", ChatColor.GREEN + "Click to increase", ChatColor.GREEN + "Right-Click to decrease");
		this.c = c;
		this.time = time;
		this.startingbid = startingbid;
		setInventoryListener(this);
	}

	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		switch(slot) {
		case 29:
			if (click == ClickType.LEFT) {
				time++;
			}
			if (click == ClickType.RIGHT) {
				time--;
			}
			time = Math.max(1, time);
			AuctionEditor ae1 = new AuctionEditor(p, c, (int) time, (int) startingbid);
			ae1.send();
			break;
		case 33:
			if (click == ClickType.LEFT) {
				startingbid *= 1.2;
			}
			if (click == ClickType.RIGHT) {
				startingbid /= 1.2;
			}
			startingbid = Math.max(10, startingbid);
			AuctionEditor ae2 = new AuctionEditor(p, c, (int) time, (int) startingbid);
			ae2.send();
			break;
		case 31:
			Auction a = new Auction(p, c, (int) startingbid, (int) time);
			EconomyManager.addAuction(a);
			p.closeInventory();
			break;
		}
		
	}
	
}
