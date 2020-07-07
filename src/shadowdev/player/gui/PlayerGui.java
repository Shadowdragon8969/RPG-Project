package shadowdev.player.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import shadowdev.server.ServerManager;

public class PlayerGui {

	private InventoryListener i;
	private Inventory inv;
	private Player view;
	
	public PlayerGui(Player viewer, String title, int size) {
		inv = Bukkit.getServer().createInventory(null, size, title);
		view = viewer;
	}
	
	public void setItem(int slot, Material type, int amount, String name, String... lore) {
		ItemStack i = new ItemStack(type);
		i.setAmount(amount);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.setLore(Arrays.asList(lore));
		i.setItemMeta(m);
		inv.setItem(slot, i);
	}
	
	public void setItem(int slot, ItemStack i) {
		inv.setItem(slot, i);
	}
	
	public void setItem(int slot, ItemCustom i) {
		inv.setItem(slot, CraftingTableManager.getItemFromCustom(i).clone());
	}
	
	public void setItem(int slot, int amt, ItemCustom i) {
		ItemStack it = CraftingTableManager.getItemFromCustom(i).clone();
		it.setAmount(amt);
		inv.setItem(slot, it);
	}
	
	public void setItem(int slot, int amt, ItemCustom i, String... addLore) {
		ItemStack it = CraftingTableManager.getItemFromCustom(i).clone();
		it.setAmount(amt);
		ItemMeta m = it.getItemMeta();
		List<String> l = m.getLore();
		l.addAll(Arrays.asList(addLore));
		m.setLore(l);
		it.setItemMeta(m);
		inv.setItem(slot, it);
	}
	
	public void send() {
		view.openInventory(inv);
		ServerManager.getPlayer(view).setCurrentGui(this);
	}
	
	protected Player getViewer() {
		return view;
	}
	
	public InventoryListener getListener() {
		return i;
	}
	
	protected void setInventoryListener(InventoryListener i) {
		this.i = i;
	}
	
}
