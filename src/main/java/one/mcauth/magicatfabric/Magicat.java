package one.mcauth.magicatfabric;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import one.mcauth.magicatfabric.common.blockentities.ArtemInfuserEntity;
import one.mcauth.magicatfabric.common.blocks.ArtemInfuser;
import one.mcauth.magicatfabric.common.blocks.gui.ArtemInfuserGuiDescription;
import one.mcauth.magicatfabric.common.items.ArtemiumWand;
import one.mcauth.magicatfabric.common.items.ManaFruit;
import one.mcauth.magicatfabric.common.items.TestManaCrystal;
import one.mcauth.magicatfabric.components.ManaComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Magicat implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("magicat");

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier("magicat", "magicattab"),
			() -> new ItemStack(Items.AMETHYST_BLOCK));

	public static Item ITEM_ARTEMIUM = new Item(new FabricItemSettings().group(ITEM_GROUP));
	public static Item ITEM_MAGICALCATS = new Item(new FabricItemSettings().group(ITEM_GROUP));
	public static Item ITEM_MANACRYSTAL = new TestManaCrystal(new FabricItemSettings().group(ITEM_GROUP));
	public static Item ITEM_MANAFRUIT = new ManaFruit(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().alwaysEdible().snack().hunger(3).saturationModifier(1f).build()));
	public static Item ITEM_CATALYZER = new Item(new FabricItemSettings().group(ITEM_GROUP));
	public static Item ITEM_WAND_ARTEMIUM = new ArtemiumWand(new FabricItemSettings().group(ITEM_GROUP).rarity(Rarity.RARE).maxDamage(60));

	public static Block BLOCK_ARTEMINFUSER = new ArtemInfuser(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());

	public static BlockEntityType<ArtemInfuserEntity> ENTITY_ARTEMINFUSER;

	public static ScreenHandlerType<ArtemInfuserGuiDescription> SCREENHANDLER_ARTEMINFUSER = null;

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("magicat", "artemium"), ITEM_ARTEMIUM);
		Registry.register(Registry.ITEM, new Identifier("magicat", "magicalcats"), ITEM_MAGICALCATS);
		Registry.register(Registry.ITEM, new Identifier("magicat", "manacrystal"), ITEM_MANACRYSTAL);
		Registry.register(Registry.ITEM, new Identifier("magicat", "manafruit"), ITEM_MANAFRUIT);
		Registry.register(Registry.ITEM, new Identifier("magicat", "catalyzer"), ITEM_CATALYZER);
		Registry.register(Registry.ITEM, new Identifier("magicat", "artemium_wand"), ITEM_WAND_ARTEMIUM);

		Registry.register(Registry.BLOCK, new Identifier("magicat", "arteminfuser"), BLOCK_ARTEMINFUSER);
		Registry.register(Registry.ITEM, new Identifier("magicat", "arteminfuser"), new BlockItem(BLOCK_ARTEMINFUSER, new FabricItemSettings().group(ITEM_GROUP)));

		ENTITY_ARTEMINFUSER = Registry.register(Registry.BLOCK_ENTITY_TYPE, "magicat:arteminfuser_entity", FabricBlockEntityTypeBuilder.create(ArtemInfuserEntity::new, BLOCK_ARTEMINFUSER).build(null));

		SCREENHANDLER_ARTEMINFUSER = ScreenHandlerRegistry.registerSimple(ArtemInfuser.ID, (syncId, inventory) -> new ArtemInfuserGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));

		HudRenderCallback.EVENT.register((MatrixStack matrixStack, float tickDelta) -> {
			MinecraftClient client = MinecraftClient.getInstance();
			InGameHud inGameHud = client.inGameHud;
			TextRenderer textRenderer = inGameHud.getTextRenderer();

			assert client.player != null;

			ClientPlayerEntity player = client.player;

			ManaComponent component = MagicatComponents.COMPONENT_MANA.get((PlayerEntity)player);
			boolean available = component.getAvailable();
			if (available) {
				textRenderer.draw(matrixStack, "Mana: " + component.getMana(), 8f, 8f, 0x186bd9);
			}
		});
	}
}
