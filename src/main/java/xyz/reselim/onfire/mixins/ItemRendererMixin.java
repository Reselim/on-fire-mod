package xyz.reselim.onfire.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.ItemRenderer;
import xyz.reselim.onfire.OnFireConfig;
import xyz.reselim.onfire.OnFireMod;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Inject(
		at = @At("HEAD"),
		method = "renderFireInFirstPerson*",
		cancellable = true
	)
	public void onRenderBurning(float partialTicks, CallbackInfo info) {
		OnFireConfig config = OnFireMod.INSTANCE.CONFIG;
		if (!config.effectEnabled) {
			info.cancel();
		}
	}
}
