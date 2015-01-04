package jason.network.packet;

import jason.TestMod;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public abstract class AbstractMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage> {
	
	/**
	 * Handle a message received on the client side
	 * @return a message to send back to the Server, or null if no reply is necessary
	 */
	@SideOnly(Side.CLIENT)
	public abstract IMessage handleClientMessage(EntityPlayer player, T message, MessageContext ctx);
	
	/**
	 * Handle a message received on the server side
	 * @return a message to send back to the Client, or null
	 */
	public abstract IMessage handleServerMessage(EntityPlayer player, T message, MessageContext ctx);
	
	@Override
	public IMessage onMessage(T message, MessageContext ctx) {
		if(ctx.side.isClient()) {
			return handleClientMessage(TestMod.proxy.getPlayerEntity(ctx), message, ctx);
		} else {
			return handleServerMessage(TestMod.proxy.getPlayerEntity(ctx), message, ctx);
		}
	}
}
