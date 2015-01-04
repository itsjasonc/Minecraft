package jason.network.packet.client;

import jason.entity.TestPlayerInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SyncPlayerPropsMessage implements IMessage {
	//Stores ExtendedPlayer data
	private NBTTagCompound data;
	
	public SyncPlayerPropsMessage() {}
	
	public SyncPlayerPropsMessage(EntityPlayer player) {
		data = new NBTTagCompound();
		TestPlayerInfo.get(player).saveNBTData(data);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		data = ByteBufUtils.readTag(buffer);
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		ByteBufUtils.writeTag(buffer, data);
	}
	
	public static class Handler extends AbstractClientMessageHandler<SyncPlayerPropsMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage handleClientMessage(EntityPlayer player, SyncPlayerPropsMessage message, MessageContext ctx) {
			TestPlayerInfo.get(player).loadNBTData(message.data);
			return null;
		}
	}
}
