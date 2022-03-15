package one.mcauth.magicatfabric.common.blocks.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import one.mcauth.magicatfabric.Magicat;
import one.mcauth.magicatfabric.server.ServerPacketHelper;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ArtemInfuserGuiDescription extends SyncedGuiDescription {
    public ArtemInfuserGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(Magicat.SCREENHANDLER_ARTEMINFUSER, syncId, playerInventory, getBlockInventory(context, 3), getBlockPropertyDelegate(context));
        Inventory blockInventory = getBlockInventory(context, 3);

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(172, 128);
        root.setInsets(Insets.ROOT_PANEL);

        root.add(new WLabel(new TranslatableText("gui.arteminfuser.label.netheriteslot")), 0, 1);
        root.add(new WLabel(new TranslatableText("gui.arteminfuser.label.diamondslot")), 0, 2);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot, 6, 1);
        WItemSlot itemSlot2 = WItemSlot.of(blockInventory, 1);
        root.add(itemSlot2, 6, 2);

        WItemSlot itemSlot3 = WItemSlot.of(blockInventory, 2);
        itemSlot3.setInsertingAllowed(false);
        root.add(itemSlot3, 8, 1);

        WButton createButton = new WButton(new TranslatableText("gui.arteminfuser.button.infuse"));
        createButton.setOnClick(() -> {
            //PacketByteBuf buf = PacketByteBufs.create();
            //buf.writeBlockPos(pos);
            //ClientPlayNetworking.send(ServerPacketHelper.INFUSE_PACKET_ID, buf);
            ScreenNetworking.of(this, NetworkSide.CLIENT).send(ServerPacketHelper.INFUSE_PACKET_ID, buf -> buf.writeInt(1));
        });
        ScreenNetworking.of(this, NetworkSide.SERVER).receive(ServerPacketHelper.INFUSE_PACKET_ID, buf -> {
            int lox = buf.readInt();
            BlockPos blockPos = context.get((world, pos) -> {
                return pos;
            }).get();
            ItemStack stack = blockInventory.getStack(2);
            if (blockInventory.getStack(0).getItem() == Items.NETHERITE_INGOT &&
                    blockInventory.getStack(1).getItem() == Items.DIAMOND &&
                    (stack.getItem() == Magicat.ITEM_ARTEMIUM || stack.isEmpty()) &&
                    stack.getCount() <= 60) {
                blockInventory.removeStack(0, 1);
                blockInventory.removeStack(1, 1);
                if (stack.isEmpty()) {
                    stack = new ItemStack(Magicat.ITEM_ARTEMIUM, 4);
                } else {
                    stack.increment(4);
                }
                blockInventory.setStack(2, stack);
            }
        });
        root.add(createButton, 0, 3, 8, 20);

        root.add(this.createPlayerInventoryPanel(), 0, 5);

        root.validate(this);
    }
}
