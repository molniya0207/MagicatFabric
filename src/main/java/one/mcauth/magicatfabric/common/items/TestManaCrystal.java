package one.mcauth.magicatfabric.common.items;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Util;
import one.mcauth.magicatfabric.MagicatComponents;
import one.mcauth.magicatfabric.components.ManaComponent;
import org.checkerframework.checker.units.qual.C;

public class TestManaCrystal extends Item {
    public TestManaCrystal(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ManaComponent manaComponent = MagicatComponents.COMPONENT_MANA.get(context.getPlayer());
        context.getPlayer().sendSystemMessage(new LiteralText(Integer.toString(manaComponent.getMana())), Util.NIL_UUID);
        manaComponent.addMana(1);
        context.getPlayer().sendSystemMessage(new LiteralText(Integer.toString(manaComponent.getMana())), Util.NIL_UUID);
        return ActionResult.SUCCESS;
    }
}
