package shadowdev.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import dev.shadow.api.IHasEnchanments;
import dev.shadow.api.IHasNBT;
import dev.shadow.api.ItemCustom;
import net.md_5.bungee.api.ChatColor;

public class ArmorX extends ItemCustom implements IHasNBT, IHasEnchanments {

	double armor, armorT = 1.6;
	protected int defenseB = 1;
	Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
	
	public ArmorX(String name, String id, Material mat, double armor, int d, double spd, String... lore) {
		super(mat, id);
		setDisplayName(ChatColor.GRAY + name);
		this.defenseB = (int) armor;
		if (lore.length > 1) {
			List<String> newLore = new ArrayList<String>();
			for (String s : lore) {
				newLore.add(s);
			}
			String ds = defenseB + "";
			if (ds.contains(".") && ds.split("\\.").length > 1) {
				String[] str = ds.split("\\.");
				ds = str[0] + "." + str[1].charAt(0);
			}
			if (newLore != null) {
				newLore.add(" ");
				newLore.add(ChatColor.GREEN + "Defense Boost: " + ChatColor.AQUA + ds);
				lore = newLore.toArray(new String[newLore.size()]);
			}
		}
		setLore(lore);
		//this.armorT = spd;
		this.durability = d;
		if (lore.length == 1) {
			if (!lore[0].contains("\n")) {
				String s = lore[0];
				String[] words = s.split(" ");
				List<String> newLore = new ArrayList<String>();
				int nLIndex = 0;
				while (true) {
					try {
						newLore.add(words[nLIndex] + " " + words[nLIndex + 1] + " " + words[nLIndex + 2] + " " + words[nLIndex + 3] + " " + words[nLIndex + 4]);
						nLIndex += 5;
					}catch(ArrayIndexOutOfBoundsException e) {
						try {
							newLore.add(words[nLIndex] + " " + words[nLIndex + 1] + " " + words[nLIndex + 2] + " " + words[nLIndex + 3]);
						}
						catch(ArrayIndexOutOfBoundsException e2) {
							try {
								newLore.add(words[nLIndex] + " " + words[nLIndex + 1] + " " + words[nLIndex + 2]);
							}
							catch(ArrayIndexOutOfBoundsException e3) {
								try {
									newLore.add(words[nLIndex] + " " + words[nLIndex + 1]);
								}
								catch(ArrayIndexOutOfBoundsException e4) {
									try {
										newLore.add(words[nLIndex]);
									}
									catch(ArrayIndexOutOfBoundsException e5) {
										break;
									}
								}
							}
						}
						break;
					}
				}
				if (defenseB > 0) {
					String ds = defenseB + "";
					if (ds.contains(".") && ds.split("\\.").length > 1) {
						String[] str = ds.split("\\.");
						ds = str[0] + "." + str[1].charAt(0);
					}
					if (newLore != null) {
						newLore.add(" ");
						newLore.add(ChatColor.GREEN + "Defense Boost: " + ChatColor.AQUA + ds);
						lore = newLore.toArray(new String[newLore.size()]);
					}
				}
				setLore(newLore.toArray(new String[newLore.size()]));
			}
		}
	}
	
	public ArmorX(String name, String id, Material mat, double armor, boolean noDurability, String... lore) {
		this(name, id, mat, armor, -1, 1.6, lore);
		this.noDurability = noDurability;
	}
	
	public int getDefenseBoost() {
		return defenseB;
	}
	
	public ArmorX addEnchant(Enchantment e, int level) {
		enchants.put(e, level);
		return this;
	}
	
	@Override
	public Map<Enchantment, Integer> getEnchants() {
		return enchants;
	}
	
	@Override
	public AttributeValue getItemAttribute(AttributeType t) {
		switch (t) {
		case ARMOR:
			return new AttributeValue((float)armor);
		default:
			break;
		}
		return null;
	}
	
}
