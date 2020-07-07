package dev.shadow.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public abstract class Multiblock {
	
	List<ObjectSet<PosRelative, Material>> blocks;
	boolean shaped = true;
	
	public Multiblock(EnumDimension d) {
		blocks = new ArrayList<ObjectSet<PosRelative, Material>>();
	}
	
	protected final void addBlock(PosRelative r, Material m) {
		blocks.add(new ObjectSet<PosRelative, Material>(r, m));
	}
	
	public abstract void onInteract(Player p, BlockState s, Location l);
	
}
