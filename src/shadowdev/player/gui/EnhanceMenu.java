package shadowdev.player.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.shadow.api.AttributeType;
import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;
import shadowdev.item.ToolX;
import shadowdev.player.GamePlayer;
import shadowdev.server.ServerManager;

public class EnhanceMenu extends PlayerGui implements InventoryListener {

	List<ItemCustom> icons = new ArrayList<ItemCustom>();
	List<Integer> amts = new ArrayList<Integer>();
	int start = 0;
	
	public EnhanceMenu(Player p, int start) {
		super (p, ChatColor.GREEN + "Enhancement Menu", 54);
		setItem(6, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
		setItem(33, Material.ENCHANTED_BOOK, 1, ChatColor.RED + "Enhance Item", "Select Materials to use first");
		setItem(51, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
		//setItem(15, Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Armor", "Shows the armor that", "you own and can equip");
		//setItem(24, Material.BLAZE_POWDER, 1, ChatColor.YELLOW + "Stats", "Shows your stats a levels");
		//setItem(33, Material.BOOK, 1, ChatColor.YELLOW + "Skills", "Shows the skills that", "you have acquired and", "their attack patterns");
		//setItem(51, Material.CHEST, 1, ChatColor.YELLOW + "Inventory", "Shows the items that", "you have collected");
		this.start = start;
		for (int i = 0; i < ServerManager.getPlayer(p).getInventory().size(); i++) {
			ItemCustom item = ServerManager.getPlayer(p).getInventory().get(i);
			if (!(item instanceof MaterialX) && !(item instanceof ToolX)) continue;
			int amt = 0;
			boolean present = false;
			for (ItemCustom ic : icons) {
				if (ic.getId().equalsIgnoreCase(item.getId())) {
					present = true;
					break;
				}
			}
			if (present) continue;
			for (int x = 0; x < ServerManager.getPlayer(p).getInventory().size(); x++) {
				if (item.getDisplayName().equalsIgnoreCase(ServerManager.getPlayer(p).getInventory().get(x).getDisplayName())) {
					amt++;
				}
			}
			amts.add(amt);
			icons.add(item);
		}
		if (icons.size() < 1) {
			setItem(10, Material.OAK_SIGN, 1, ChatColor.RED + "Couldn't Find any materials to enhance :/", "");
		}
		setInventoryListener(this);
		for (int i = 5; i < 54; i += 9) {
			setItem(i, Material.RED_STAINED_GLASS_PANE, 1, " ", "");
			setItem(i + 2, Material.RED_STAINED_GLASS_PANE, 1, " ", "");
		}
		setItem(50, Material.RED_STAINED_GLASS_PANE, 1, " ", "");
		setItem(52, Material.RED_STAINED_GLASS_PANE, 1, " ", "");
		if (start > 0) {
			setItem(2, Material.ARROW, 1, ChatColor.GREEN + "Page Up", "");
		}
		if (icons.size() - 12 - start > 0)
		setItem(47, Material.ARROW, 1, ChatColor.GREEN + "Page Down", "");
		///
		int x = 0;
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(10, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(11, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(12, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(19, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(20, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(21, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(28, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(29, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(30, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(37, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(38, amts.get(start + x), icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(39, amts.get(start + x), icons.get(start + x));
			x++;
		}
		///
	}
	
	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		//new MainMenu(p).onClick(p, i, slot, click, inv);
		if (i != null && slot == 47) {
			start += 12;
			EnhanceMenu im = new EnhanceMenu(p, start);
			im.send();
		}
		if (i != null && slot == 2) {
			start -= 12;
			EnhanceMenu im = new EnhanceMenu(p, start);
			im.send();
		}
		ItemCustom c = CraftingTableManager.isCustomItem(i);
		if (c != null && (c instanceof MaterialX || c instanceof ToolX)) {
			if (c instanceof MaterialX) {		
				if (inv.getItem(15) != null && c == CraftingTableManager.isCustomItem(inv.getItem(15))) {
					inv.getItem(15).setAmount(inv.getItem(15).getAmount() + 1);
					i.setAmount(i.getAmount() - 1);
					inv.setItem(slot, i);
				}else {
					if (inv.getItem(15) == null) {
						ItemStack it = CraftingTableManager.getItemFromCustom(c);
						it.setAmount(1);
						inv.setItem(15, it);
						i.setAmount(i.getAmount() - 1);
						inv.setItem(slot, i);
					}
				}
			}
			if (c instanceof ToolX) {
					if (inv.getItem(24) == null) {
						ItemStack it = CraftingTableManager.getItemFromCustom(c);
						it.setAmount(1);
						inv.setItem(24, it);
						i.setAmount(i.getAmount() - 1);
						inv.setItem(slot, i);
				}
			}
		}
		if (inv.getItem(24) != null && inv.getItem(15) != null) {
			//System.out.println("im here");
			ToolX hilt = (ToolX) CraftingTableManager.isCustomItem(inv.getItem(24));
			ToolX base = hilt;
			if (hilt.isEnhanced()) base = hilt.base;
			MaterialX blade = (MaterialX) CraftingTableManager.isCustomItem(inv.getItem(15));
			if (ServerManager.enhance.get(base) != null && ServerManager.enhance.get(base) == blade) {
				boolean canEnc = true;
				if (hilt.getCurrentEnhancement() >= hilt.getEnhancements() - 1) {
					setItem(33, Material.BARRIER, 1, ChatColor.RED + ChatColor.BOLD.toString() + "Can not enhance item", ChatColor.RED + "This item can not be", ChatColor.RED + "enhanced any further.");
					return;
				}
				//System.out.println("im here now");
				ToolX op = base.children.get(hilt.getCurrentEnhancement());
				inv.setItem(42, CraftingTableManager.getItemFromCustom(op));
				double successRate = inv.getItem(24).getAmount() + inv.getItem(15).getAmount() - blade.getSmithingDifficulty() + 35 + ServerManager.getPlayer(p).getStat("smithing").getLevel();
				successRate = Math.min(successRate, 100);
				successRate = Math.max(successRate, 1);
				if (canEnc)
				setItem(33, Material.ENCHANTED_BOOK, 1, ChatColor.GOLD + ChatColor.BOLD.toString() + "Enhance Item", ChatColor.RED + "The materials required will be", ChatColor.RED + "consumed, think before you click!", ChatColor.RED + "If Enhancement fails you", ChatColor.RED + "will not lose your tool.", ChatColor.YELLOW + "Success Rate: " + ChatColor.GREEN + successRate + "%");
			}
		}
		if (slot == 33 && inv.getItem(42) != null) {
			ToolX hilt = (ToolX) CraftingTableManager.isCustomItem(inv.getItem(24));
			ToolX base = hilt;
			if (hilt.isEnhanced()) base = hilt.base;
			MaterialX blade = (MaterialX) CraftingTableManager.isCustomItem(inv.getItem(15));
			double successRate = inv.getItem(24).getAmount() + inv.getItem(15).getAmount() - blade.getSmithingDifficulty() + 35 + ServerManager.getPlayer(p).getStat("smithing").getLevel();
			successRate = Math.min(successRate, 100);
			successRate = Math.max(successRate, 1);
			ToolX op = (ToolX) CraftingTableManager.isCustomItem(inv.getItem(42));
			GamePlayer gp = ServerManager.getPlayer(p);
			gp.addExp("smithing", (int) op.getItemAttribute(AttributeType.ATTACK_DAMAGE).getValue() + inv.getItem(24).getAmount() + inv.getItem(15).getAmount() + (blade.getSmithingDifficulty() / 5));
			successRate /= 100;
			for (int it = 0; it < inv.getItem(15).getAmount(); it++) {
				gp.removeItem(blade);
			}
			if (new Random().nextDouble() <= successRate) {
				p.sendMessage(ChatColor.GREEN + "ENHANCEMENT SUCCESS!!! " + ChatColor.WHITE + "You created a " + op.getDisplayName());
				gp.addItem(op);
				p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
				p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
				gp.removeItem(hilt);
				if (gp.getEquippedWeapon() == hilt) {
					gp.equipWeapon(op);
				}
			}else {
				p.sendMessage(ChatColor.RED + "ENHANCEMENT FAILURE!!! " + ChatColor.WHITE + "Better luck next time!");
				p.playSound(p.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1, 1);
			}
			EnhanceMenu m = new EnhanceMenu(p, start);
			m.send();
		}
	}
	
}
