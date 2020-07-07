package shadowdev.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import dev.shadow.api.AttributeType;
import dev.shadow.api.AttributeValue;
import dev.shadow.api.IEnhanceable;
import dev.shadow.api.IHasEnchanments;
import dev.shadow.api.IHasNBT;
import dev.shadow.api.ItemCustom;
import net.md_5.bungee.api.ChatColor;

public abstract class ToolX extends ItemCustom implements IHasNBT, IHasEnchanments, IEnhanceable {

	protected double damage, attackSpeed = 1.6;
	public int enc = 0;
	boolean showDamage = true;
	public boolean isenc = false;
	Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
	String[] bl;
	public ToolX base;
	public List<ToolX> children = new ArrayList<ToolX>();
	
	public ToolX(String name, String id, Material mat, double damage, int d, double spd, String... lore) {
		super(mat, id);
		bl = lore;
		setDisplayName(ChatColor.GRAY + name);
		if (lore.length > 1) {
			List<String> newLore = new ArrayList<String>();
			for (String s : lore) {
				newLore.add(s);
			}
			String ds = damage + "";
			if (ds.contains(".") && ds.split("\\.").length > 1) {
				String[] str = ds.split("\\.");
				ds = str[0] + "." + str[1].charAt(0);
			}
			if (newLore != null) {
				newLore.add(" ");
				newLore.add(ChatColor.GREEN + "Damage: " + ChatColor.RED + (ds));
				newLore.add(ChatColor.GREEN + "Speed: " + ChatColor.YELLOW + attackSpeed);
				lore = newLore.toArray(new String[newLore.size()]);
			}
		}
		setLore(lore);
		this.attackSpeed = spd;
		this.damage = damage;
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
				if (showDamage) {
					String ds = damage + "";
					if (ds.contains(".") && ds.split("\\.").length > 1) {
						String[] str = ds.split("\\.");
						ds = str[0] + "." + str[1].charAt(0);
					}
					if (newLore != null) {
						newLore.add(" ");
						newLore.add(ChatColor.GREEN + "Damage: " + ChatColor.RED + ds);
						newLore.add(ChatColor.GREEN + "Speed: " + ChatColor.YELLOW + attackSpeed);
						lore = newLore.toArray(new String[newLore.size()]);
					}
				}
				setLore(newLore.toArray(new String[newLore.size()]));
			}
		}
	}
	
	public void rebuild() {
		String add = "";
		if (isEnhanced()) {
			add = ChatColor.YELLOW + " [" + ChatColor.GREEN + "+" + getCurrentEnhancement() + ChatColor.YELLOW + "]";
		}
		setDisplayName(getDisplayName() + add);
		if (bl.length > 1) {
			List<String> newLore = new ArrayList<String>();
			for (String s : bl) {
				newLore.add(s);
			}
			String ds = (damage + getCurrentEnhancement()) + "";
			if (ds.contains(".") && ds.split("\\.").length > 1) {
				String[] str = ds.split("\\.");
				ds = str[0] + "." + str[1].charAt(0);
			}
			if (newLore != null) {
				newLore.add(" ");
				newLore.add(ChatColor.GREEN + "Damage: " + ChatColor.RED + ds + ChatColor.YELLOW + " (+" + getCurrentEnhancement() + ")");
				newLore.add(ChatColor.GREEN + "Speed: " + ChatColor.YELLOW + attackSpeed);
				bl = newLore.toArray(new String[newLore.size()]);
			}
		}
		
		setLore(bl);
		if (bl.length == 1) {
			if (!bl[0].contains("\n")) {
				String s = bl[0];
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
				if (showDamage) {
					String ds = (damage + getCurrentEnhancement()) + "";
					if (ds.contains(".") && ds.split("\\.").length > 1) {
						String[] str = ds.split("\\.");
						ds = str[0] + "." + str[1].charAt(0);
					}
					if (newLore != null) {
						newLore.add(" ");
						newLore.add(ChatColor.GREEN + "Damage: " + ChatColor.RED + ds + ChatColor.YELLOW + " (+" + getCurrentEnhancement() + ")");
						newLore.add(ChatColor.GREEN + "Speed: " + ChatColor.YELLOW + attackSpeed);
						bl = newLore.toArray(new String[newLore.size()]);
					}
				}
				setLore(newLore.toArray(new String[newLore.size()]));
			}
		}
	}
	
	public ToolX(String name, String id, Material mat, double damage, boolean noDurability, String... lore) {
		this(name, id, mat, damage, -1, 1.6, lore);
		this.noDurability = noDurability;
	}
	
	public ToolX addEnchant(Enchantment e, int level) {
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
		case ATTACK_DAMAGE:
			return new AttributeValue((float)damage);
		case ATTACK_SPEED:
			return new AttributeValue((float) attackSpeed);
		default:
			break;
		}
		return null;
	}
	
	@Override
	public int getCurrentEnhancement() {
		return enc;
	}
	
	@Override
	public int getEnhancements() {
		return 1;
	}
	
	@Override
	public boolean isEnhanced() {
		return isenc;
	}
	
}
