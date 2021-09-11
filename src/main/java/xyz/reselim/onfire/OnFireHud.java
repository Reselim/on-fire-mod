package xyz.reselim.onfire;

import java.awt.Color;
import java.util.function.Consumer;

import gg.essential.elementa.components.UIRoundedRectangle;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.AdditiveConstraint;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ChildBasedSizeConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import net.minecraft.client.Minecraft;

public class OnFireHud {
	public static final OnFireHud INSTANCE = new OnFireHud();

	public boolean enabled = false;

	private final Window window = new Window();
	private UIRoundedRectangle background;
	private UIText text;

	public OnFireHud() {
		OnFireConfig config = OnFireMod.INSTANCE.CONFIG;

		// UI

		background = new UIRoundedRectangle(4f);
		background.setX(new CenterConstraint());
		window.addChild(background);

		text = new UIText(config.text, false);
		text.setX(new AdditiveConstraint(new CenterConstraint(), new PixelConstraint(1)));
		text.setY(new CenterConstraint());
		background.addChild(text);

		// LISTENERS
		// State would probably be a good idea here

		Consumer<Object> onBackgroundColorUpdate = new Consumer<Object>() {
			public void accept(Object newBackgroundColor) {
				background.setColor((Color) newBackgroundColor);
			}
		};
		config.registerListener("backgroundColor", onBackgroundColorUpdate);
		onBackgroundColorUpdate.accept(config.backgroundColor);

		Consumer<Object> onForegroundColorUpdate = new Consumer<Object>() {
			public void accept(Object newForegroundColor) {
				text.setColor((Color) newForegroundColor);
			}
		};
		config.registerListener("foregroundColor", onForegroundColorUpdate);
		onForegroundColorUpdate.accept(config.foregroundColor);

		Consumer<Object> onTextUpdate = new Consumer<Object>() {
			public void accept(Object newText) {
				text.setText((String) newText);
			}
		};
		config.registerListener("text", onTextUpdate);

		Consumer<Object> onScaleUpdate = new Consumer<Object>() {
			public void accept(Object newScale) {
				background.setWidth(new AdditiveConstraint(new ChildBasedSizeConstraint(), new PixelConstraint(12 * (int) newScale)));
				background.setHeight(new AdditiveConstraint(new ChildBasedSizeConstraint(), new PixelConstraint(6 * (int) newScale)));
				text.setTextScale(new PixelConstraint((int) newScale));
			};
		};
		config.registerListener("scale", onScaleUpdate);
		onScaleUpdate.accept(config.scale);

		Consumer<Object> onOffsetUpdate = new Consumer<Object>() {
			public void accept(Object newOffset) {
				background.setY(new PixelConstraint((int) newOffset));
			};
		};
		config.registerListener("offset", onOffsetUpdate);
		onOffsetUpdate.accept(config.offset);
	}

	private Color lerpColor(Color colorA, Color colorB, float progress) {
		float redA = colorA.getRed();
		float greenA = colorA.getGreen();
		float blueA = colorA.getBlue();
		float alphaA = colorA.getAlpha();

		float redB = colorB.getRed();
		float greenB = colorB.getGreen();
		float blueB = colorB.getBlue();
		float alphaB = colorB.getAlpha();

		return new Color(
			(int) (redA + (redB - redA) * progress),
			(int) (greenA + (greenB - greenA) * progress),
			(int) (blueA + (blueB - blueA) * progress),
			(int) (alphaA + (alphaB - alphaA) * progress)
		);
	}

	public void draw() {
		OnFireConfig config = OnFireMod.INSTANCE.CONFIG;

		if (config.enabled && enabled) {
			Color baseColor = config.backgroundColor;
			Color color = baseColor;

			if (config.pulsating) {
				long time = Minecraft.getSystemTime();
				float animationLength = 300f;
				float animationProgress = (time / animationLength) % animationLength;
				float animationFactor = (float) Math.sin((double) animationProgress * Math.PI);

				Color targetColor = Color.WHITE;
				if (animationFactor < 0f) {
					targetColor = Color.BLACK;
					animationFactor = -animationFactor;
				}

				color = lerpColor(baseColor, targetColor, animationFactor * 0.15f);
			}

			background.setColor(
				new Color(
					color.getRed(),
					color.getGreen(),
					color.getBlue(),
					baseColor.getAlpha()
				)
			);

			window.draw();
		}
	}

	public void setEnabled(boolean shouldBeEnabled) {
		enabled = shouldBeEnabled;
	}
}
