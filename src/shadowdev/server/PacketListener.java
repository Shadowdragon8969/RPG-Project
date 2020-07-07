package shadowdev.server;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_15_R1.Packet;
import net.minecraft.server.v1_15_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_15_R1.PacketPlayInBlockPlace;
import net.minecraft.server.v1_15_R1.PacketPlayInUseEntity;
import shadowdev.item.ToolX;
import shadowdev.player.GamePlayer;

public class PacketListener extends ChannelDuplexHandler {

	Player user;
	boolean right = false;
	
	public PacketListener(Player user) {
		this.user = user;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Packet) {
			Packet<?> p = (Packet<?>) msg;
			String s = "";
			//System.out.println(p.getClass().getName());
			if (p instanceof PacketPlayInArmAnimation) {
				s += "l";
				if (right) {
					//System.out.println("right click");
					right = false;
					super.channelRead(ctx, msg);
					return;
				}
				ItemCustom item = CraftingTableManager.isHoldingCustomItem(user);
				if (item != null) {
					if (item instanceof ToolX) {
						Player pl = user;
						ServerManager.getPlayer(pl).addToSkillPattern('L');
						new BukkitRunnable() {
							int t = 45;
							Location l = pl.getEyeLocation();
							@Override
							public void run() {
								t -= 10;
								double x = Math.cos(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * 2;
								double y = pl.getEyeLocation().getDirection().getY() * 2;
								double z = Math.sin(Math.toRadians(pl.getEyeLocation().getYaw() + 90 - t)) * 2;
								CraftingTableManager.spawnParticleRGB(pl.getWorld(), Color.YELLOW, l.getX() + x, l.getY() + y, l.getZ() + z, 1, 1);
								if (t < -30) {
									this.cancel();
								}
							}
						}.runTaskTimer(ServerManager.instance, 0, 1);
					}
				}
			}
			if (p instanceof PacketPlayInBlockPlace) {
				right = true;
				s += "r";
				GamePlayer gp = ServerManager.getPlayer(user);
				ItemCustom item = CraftingTableManager.isHoldingCustomItem(user);
				if (item != null) {
					Entity e = ServerManager.onRClick(user, item);
					if (e != null) {
						PacketPlayInUseEntity pe = new PacketPlayInUseEntity(((CraftEntity)e).getHandle());
						msg = pe;
					}
					gp.addToSkillPattern('R');
				}
				//System.out.println("right click");
			}
			//System.out.println(s);
		}
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		super.write(ctx, msg, promise);
	}
	
}
