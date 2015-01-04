package jason.handler;

import jason.entity.TestPlayerInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventsFML {
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		TestPlayerInfo.get(event.player).onPlayerLoggedIn();
	}
}
