package shadowdev.item;

import org.bukkit.Material;

import dev.shadow.api.ItemCustom;

public class MaterialX extends ItemCustom {

	int forgeDiff = 0;
	
	public MaterialX(Material type, String id, int diff) {
		super(type, id);
		forgeDiff = diff;
	}
	
	public int getSmithingDifficulty() {
		return forgeDiff;
	}
	
}
