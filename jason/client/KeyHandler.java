package jason.client;

import jason.handler.GuiHandler;
import jason.lib.Constants;
import jason.network.PacketDispatcher;
import jason.network.packet.bidirectional.MyMessage;
import jason.network.packet.bidirectional.PlaySoundPacket;
import jason.network.packet.server.OpenGuiPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KeyHandler {

	
	private final Minecraft mc;

	//Key index
	public static final byte KEY_GUI = 0;
	
	/** Key descriptions **/
	private static final String[] desc = { "gui" };
	
	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_TAB};
	
	public static final KeyBinding[] keys = new KeyBinding[desc.length];
	
	public KeyHandler() {
		this.mc = Minecraft.getMinecraft();
		for(int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding("Activate GUI", keyValues[i], "Test");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(Keyboard.getEventKeyState()) {
			onKeyPressed(mc, Keyboard.getEventKey());
		}
	}

	public static void onKeyPressed(Minecraft mc, int kb) {
		if(mc.inGameHasFocus && mc.thePlayer != null) {
			if (kb == keys[KEY_GUI].getKeyCode()) {
				if(mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
				}
				PacketDispatcher.sendToServer(new OpenGuiPacket(GuiHandler.GUI_TEST));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static KeyBinding getKeyBindFromCode(Minecraft mc, int keyCode) {
		for(KeyBinding k : keys) {
			if(k.getKeyCode() == keyCode) {
				return k;
			}
		}
		if(keyCode == mc.gameSettings.keyBindForward.getKeyCode()) {
			return mc.gameSettings.keyBindForward;
		} else if (keyCode == mc.gameSettings.keyBindJump.getKeyCode()) {
			return mc.gameSettings.keyBindJump;
		}

		return null;
	}
}
