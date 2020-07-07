package dev.shadow.api;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MultiblockRecipe {

	HashMap<Character, String> f = new HashMap<Character, String>();
	
	boolean shaped;
	String shape = null;
	ItemStack result;
	int productCount = 1;
	
	public MultiblockRecipe(ItemStack result) {
		this.result = result;
	}
	
	public MultiblockRecipe(ItemCustom result) {
		this.result = CraftingTableManager.references.get(result.id2);
	}
	
	public void setProductCount(int prod) {
		this.productCount = prod;
	}
	
	public void setIngredient(char x, String ingredID) {
		f.put(x, ingredID);
	}
	
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	void checkShape(Inventory inv) {
		if (inv == null || shape == null) return;
		for (int i = 0; i < 9; i++) {
			try {
			if (shape.charAt(i) == ' ' && inv.getItem(i) != null) {
				return;
			}
			if (shape.charAt(i) != ' ' && inv.getItem(i) == null) {
				return;
			}
			if (shape.charAt(i) == ' ' && inv.getItem(i) == null) {
				continue;
			}
			Material m = Material.getMaterial(f.get(shape.charAt(i)));
			if (m != null && inv.getItem(i) != null && inv.getItem(i).getAmount() == 1 && m == inv.getItem(i).getType()) {
				continue;
			}
			if (m == null) {
				ItemStack it = CraftingTableManager.references.get(CraftingTableManager.getById(f.get(shape.charAt(i))).id2);
				if (inv.getItem(i).getAmount() == 1 && inv.getItem(i).equals(it)) {
					continue;
				}
			}
			return;
			}catch(NullPointerException e) {
				System.out.println("Error checking recipe at index: " + i + "with shape name: " + f.get(shape.charAt(i)));
				return;
			}
		}
		inv.clear();
		inv.setItem(4, result);
		inv.getItem(4).setAmount(productCount);
	}
	
}
