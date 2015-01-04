package jason.network.packet.server;

import io.netty.buffer.ByteBuf;
import jason.TestMod;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class OpenGuiPacket implements IMessage {
	private int id;
	
	public OpenGuiPacket() {}
	
	public OpenGuiPacket(int id) {
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		id = buffer.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(id);
	}
	
	public static class Handler extends AbstractServerMessageHandler<OpenGuiPacket> {
		@Override
		public IMessage handleServerMessage(EntityPlayer player, OpenGuiPacket message, MessageContext ctx) {
			player.openGui(TestMod.instance, message.id, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
			return null;
		}
	}
}
