package one.mcauth.magicatfabric.common.blocks.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ArtemInfuserBlockScreen extends CottonInventoryScreen<ArtemInfuserGuiDescription> {
    public ArtemInfuserBlockScreen(ArtemInfuserGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
