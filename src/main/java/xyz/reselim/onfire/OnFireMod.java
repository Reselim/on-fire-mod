package xyz.reselim.onfire;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

@Mod(
    modid = OnFireMod.MODID,
    name = OnFireMod.MOD_NAME,
    version = OnFireMod.VERSION,
    acceptedMinecraftVersions = "[1.8.9]",
    clientSideOnly = true
)
public class OnFireMod {
    public static final String MODID = "onfire";
    public static final String MOD_NAME = "On Fire";
    public static final String VERSION = "0.0.1";

	@Mod.Instance
	public static OnFireMod INSTANCE;
	public final OnFireConfig CONFIG = new OnFireConfig();
	public final Minecraft MINECRAFT = Minecraft.getMinecraft();

    @Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		new OnFireCommand("onfire").register();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void draw(RenderTickEvent event) {
		OnFireHud.INSTANCE.draw();
	}

	@SubscribeEvent
	public void tick(RenderTickEvent event) {
		EntityPlayerSP player = MINECRAFT.thePlayer;
		boolean burning = player != null ? player.isBurning() : false;

		if (burning != OnFireHud.INSTANCE.enabled) {
			OnFireHud.INSTANCE.setEnabled(burning);
		}
	}
}