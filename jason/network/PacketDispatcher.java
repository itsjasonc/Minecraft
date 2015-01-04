package jason.network;

import jason.lib.Constants;
import jason.network.packet.AbstractMessageHandler;
import jason.network.packet.bidirectional.AbstractBiMessageHandler;
import jason.network.packet.bidirectional.MyMessage;
import jason.network.packet.bidirectional.PlaySoundPacket;
import jason.network.packet.client.AbstractClientMessageHandler;
import jason.network.packet.server.AbstractServerMessageHandler;
import jason.network.packet.server.OpenGuiPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketDispatcher {
	// a simple counter will allow us to get rid of 'magic' numbers used during packet registration
	private static byte packetId = 0;
	
	/**
	 * The SimpleNetworkWrapper instance is used both to register and send packets.
	 */
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.CHANNEL);
	
	
	/**
	 * Call during pre-init or loading, register all packets here
	 */
	public static final void registerPackets() {
		//Using an incrementing field instead of hard-coded numerals, I don't need to think
		//about what number comes next or if I missed one should I ever rearrange the order
		//of registration (for instance, if you wanted to alphabetize them)
		registerBiMessage(MyMessage.Handler.class, MyMessage.class);
		
		//Bi-directional packets (with side-specific handlers)
		registerBiMessage(PlaySoundPacket.Handler.class, PlaySoundPacket.class);
		
		
		//Packets handled on SERVER
		registerMessage(OpenGuiPacket.Handler.class, OpenGuiPacket.class);
		
	}
	
	/**
	 * Registers a message and message handler on the designated side;
	 * used for standard IMessage + IMessageHandler implementations
	 */
	private static final <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass, Side side) {
		PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}
	
	/**
	 * Registers a message and message handler on both sides; used mainly
	 * for standard IMessage + IMessageHandler implementations and ideal
	 * for messages that are handled identically on either side
	 */
	private static final <REQ extends IMessage, REPLY extends IMessage> void registerBiMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass) {
		PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId, Side.CLIENT);
		PacketDispatcher.dispatcher.registerMessage(handlerClass, messageClass, packetId++, Side.SERVER);
	}
	
	/**
	 * Registers a message and message handler, automatically determining Side(s) based on the handler class
	 * @param handlerClass	Must extend one of {@link AbstractClientMessageHandler}, {@link AbstractServerMessageHandler}, or {@link AbstractBiMessageHandler}
	 */
	private static final <REQ extends IMessage> void registerMessage(Class<? extends AbstractMessageHandler<REQ>> handlerClass, Class<REQ> messageClass) {
		if (AbstractClientMessageHandler.class.isAssignableFrom(handlerClass)) {
			registerMessage(handlerClass, messageClass, Side.CLIENT);
		} else if (AbstractServerMessageHandler.class.isAssignableFrom(handlerClass)) {
			registerMessage(handlerClass, messageClass, Side.SERVER);
		} else if (AbstractBiMessageHandler.class.isAssignableFrom(handlerClass)) {
			registerBiMessage(handlerClass, messageClass);
		} else {
			throw new IllegalArgumentException("Cannot determine on which Side(s) to register " + handlerClass.getName() + " - unrecognized handler class!");
		}
	}
	
	/**
	 * Send this message to the specified player.
	 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 */
	public static final void sendTo(IMessage message, EntityPlayerMP player) {
		PacketDispatcher.dispatcher.sendTo(message, player);
	}

	/**
	 * Send this message to everyone within a certain range of a point.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, NetworkRegistry.TargetPoint)}
	 */
	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		PacketDispatcher.dispatcher.sendToAllAround(message, point);
	}

	/**
	 * Sends a message to everyone within a certain range of the coordinates in the same dimension.
	 */
	public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
		PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}

	/**
	 * Sends a message to everyone within a certain range of the player provided.
	 */
	public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
		PacketDispatcher.sendToAllAround(message, player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, range);
	}

	/**
	 * Send this message to everyone within the supplied dimension.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 */
	public static final void sendToDimension(IMessage message, int dimensionId) {
		PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
	}

	/**
	 * Send this message to the server.
	 * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 */
	public static final void sendToServer(IMessage message) {
		PacketDispatcher.dispatcher.sendToServer(message);
	}
	
}
