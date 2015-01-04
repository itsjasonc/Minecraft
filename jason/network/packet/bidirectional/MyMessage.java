package jason.network.packet.bidirectional;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MyMessage implements IMessage {
	private String text;
	
	public MyMessage() {}
	
	public MyMessage(String text) {
		this.text = text;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		text = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, text);
	}

	public static class Handler extends AbstractBiMessageHandler<MyMessage> {
		@Override
		public IMessage handleClientMessage(EntityPlayer player, MyMessage message, MessageContext ctx) {
			System.out.println("Client " + player.getDisplayName() + " received string " + message.text);
			return null;
		}
		
		@Override
		public IMessage handleServerMessage(EntityPlayer player, MyMessage message, MessageContext ctx) {
			System.out.println("Client " + player.getDisplayName() + " received string " + message.text);
			return null;
		}
	}	
}
