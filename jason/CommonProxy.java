package jason;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import jason.lib.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy {

	public void preInit() {}
	
	public void registerRenderers() {}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}
}
