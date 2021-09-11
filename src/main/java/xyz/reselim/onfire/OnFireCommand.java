package xyz.reselim.onfire;

import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

public class OnFireCommand extends Command {
	public OnFireCommand(String name) {
		super(name);
	}

	@DefaultHandler
	public void handle() {
		EssentialAPI.getGuiUtil().openScreen(OnFireMod.INSTANCE.CONFIG.gui());
	}
}
