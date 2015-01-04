package jason.network.packet.bidirectional;

import io.netty.buffer.ByteBuf;
import jason.TestMod;
import jason.entity.TestPlayerInfo;
import jason.network.PacketDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlaySoundPacket implements IMessage {
	private String sound;
	private float volume, pitch;
	
	private double x, y, z;
	
	public PlaySoundPacket() {
	}

	public PlaySoundPacket(String sound, float volume, float pitch, double x, double y, double z) {
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Use only when sending to the SERVER to use the entity's coordinates as the center
	 * if sent to the client, the position coordinates will be ignored
	 */
	public PlaySoundPacket(String sound, float volume, float pitch, Entity entity) {
		this(sound, volume, pitch, entity.posX, entity.posY, entity.posZ);
	}
	
	/**
	 * Use only when sending to the CLIENT - the sound will play at the player's location
	 */
	public PlaySoundPacket(String sound, float volume, float pitch) {
		this(sound, volume, pitch, 0, 0, 0);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		sound = (buffer.readByte() > 0 ? ByteBufUtils.readUTF8String(buffer) : null);
		volume = buffer.readFloat();
		pitch = buffer.readFloat();
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeByte(sound == null ? (byte) 0 : (byte) 1);
		if(sound != null) {
			ByteBufUtils.writeUTF8String(buffer, sound);
		}
		buffer.writeFloat(volume);
		buffer.writeFloat(pitch);
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
	}
	
	/**
	 * Here, I use AbstractBiMessageHandler instead of IMessageHandler because
	 * the message is handled slightly differently depending on side, but it can just as well
	 * be implemented using IMessageHandler:onMessage:
	 * 
	 * @Override
	 * public IMessage onMessage(ActivateSkillPacket message, MessageContext ctx) {
	 * 		EntityPlayer player = FalloutMod.proxy.getPlayerEntity(ctx);
	 * 		if(ctx.side.isClient()) {
	 * 			player.playSound(message.sound, message.volume, message.pitch);
	 * 		} else {
	 * 			player.worldObj.playSoundEffect(message.x, message.y, message.z, message.sound, message.volume, message.pitch);
	 * 		}
	 * }
	 */
	public static class Handler extends AbstractBiMessageHandler<PlaySoundPacket> {
		@Override
		public IMessage handleClientMessage(EntityPlayer player, PlaySoundPacket message, MessageContext ctx) {
			System.out.println("Recieved a PlaySoundPacket from the server as " + player.getDisplayName());
			if(message != null && message.sound != null) {
				player.playSound(message.sound, message.volume, message.pitch);
			}
			else {
				System.out.println("Message was null");
			}
			return null;
		}
		
		@Override
		public IMessage handleServerMessage(EntityPlayer player, PlaySoundPacket message, MessageContext ctx) {
			System.out.println("Recieved a PlaySoundPacket from the client as " + player.getDisplayName());
			//PacketDispatcher.sendToAllAround(message, player, 64.0D);
			if(message != null && message.sound != null) {
				player.worldObj.playSoundEffect(message.x, message.y, message.z, message.sound, message.volume, message.pitch);
			} else {
				System.out.println("Message was null");
			}
			return null;
		}
	}
}
