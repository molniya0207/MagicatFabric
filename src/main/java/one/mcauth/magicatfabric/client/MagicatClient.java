package one.mcauth.magicatfabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import one.mcauth.magicatfabric.Magicat;
import one.mcauth.magicatfabric.common.blocks.gui.ArtemInfuserBlockScreen;
import one.mcauth.magicatfabric.common.blocks.gui.ArtemInfuserGuiDescription;

public class MagicatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.<ArtemInfuserGuiDescription, ArtemInfuserBlockScreen>register(Magicat.SCREENHANDLER_ARTEMINFUSER, (gui, inventory, title) -> new ArtemInfuserBlockScreen(gui, inventory.player, title));
    }
}
