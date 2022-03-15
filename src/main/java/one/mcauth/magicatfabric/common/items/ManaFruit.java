package one.mcauth.magicatfabric.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import one.mcauth.magicatfabric.MagicatComponents;
import one.mcauth.magicatfabric.components.ManaComponent;

public class ManaFruit extends Item {
    public ManaFruit(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack newStack = user.eatFood(world, stack);
        if (!(user instanceof PlayerEntity) || world.isClient)
            return newStack;
        PlayerEntity player = (PlayerEntity)user;
        ManaComponent component = MagicatComponents.COMPONENT_MANA.get(player);
        if (!component.getAvailable()) {
            player.sendSystemMessage(new TranslatableText("message.mananowavailable"), Util.NIL_UUID);
            component.setAvailable(true);
        } else {
            player.sendMessage(new LiteralText("+5 Mana").formatted(Formatting.GREEN), true);
            player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
            component.addMana(5);
        }
        return newStack;
    }
}
