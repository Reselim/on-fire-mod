package xyz.reselim.onfire;

import java.io.File;
import java.awt.Color;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

public class OnFireConfig extends Vigilant {
	@Property(
		type = PropertyType.SWITCH,
		name = "HUD Enabled",
		category = "General",
		subcategory = "General",
		description = "Should the HUD be enabled?"
	)
	public boolean enabled = true;

	@Property(
		type = PropertyType.SWITCH,
		name = "Effect Enabled",
		category = "General",
		subcategory = "General",
		description = "Should the vanilla first person burning effect be enabled?"
	)
	public boolean effectEnabled = false;

	@Property(
		type = PropertyType.TEXT,
		name = "Text",
		category = "General",
		subcategory = "General"
	)
	public String text = "On fire!";

	@Property(
		type = PropertyType.COLOR,
		name = "Background Color",
		category = "General",
		subcategory = "Color"
	)
	public Color backgroundColor = new Color(0xEF5350);

	@Property(
		type = PropertyType.COLOR,
		name = "Text Color",
		category = "General",
		subcategory = "Color"
	)
	public Color foregroundColor = Color.BLACK;

	@Property(
		type = PropertyType.SWITCH,
		name = "Pulsating",
		category = "General",
		subcategory = "Color",
		description = "Should the pulsating (siren) effect be enabled?"
	)
	public boolean pulsating = true;

	@Property(
		type = PropertyType.SLIDER,
		name = "Scale",
		category = "General",
		subcategory = "Position",
		min = 1,
		max = 5
	)
	public int scale = 2;

	@Property(
		type = PropertyType.SLIDER,
		name = "Offset",
		category = "General",
		subcategory = "Position",
		description = "Offset from the top of the screen",
		min = 0,
		max = 150
	)
	public int offset = 20;

	public OnFireConfig() {
		super(new File("./config/onfire.toml"));
		initialize();
	}
}
