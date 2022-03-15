package one.mcauth.magicatfabric.common.items;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import one.mcauth.magicatfabric.MagicatComponents;
import one.mcauth.magicatfabric.components.ManaComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArtemiumWand extends Item {
    int selSpell = 0;
    public ArtemiumWand(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));
        ManaComponent component = MagicatComponents.COMPONENT_MANA.get(user);
        if (user.isSneaking()) {
            selSpell++;
            if (selSpell >= 4) {
                selSpell = 0;
            }
            switch (selSpell) {
                case 0:
                    user.sendMessage(new TranslatableText("spell.regeneration").formatted(Formatting.AQUA), true);
                    break;

                case 1:
                    user.sendMessage(new TranslatableText("item.magicat.manacrystal").formatted(Formatting.AQUA), true);
                    break;

                case 2:
                    user.sendMessage(new TranslatableText("item.magicat.manafruit").formatted(Formatting.AQUA), true);
                    break;

                case 3:
                    user.sendMessage(new TranslatableText("item.magicat.catalyzer").formatted(Formatting.AQUA), true);
                    break;
            }
        } else {
            boolean usedSpell = false;
            switch (selSpell) {
                case 0:
                    if (!component.isEnoughMana(80)) {
                        user.sendMessage(new TranslatableText("spell.notenoughmana").formatted(Formatting.RED), true);
                        break;
                    }
                    component.removeMana(80);
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 1));
                    usedSpell = true;
                    break;

                case 1:
                    user.sendMessage(new TranslatableText("item.magicat.manacrystal").formatted(Formatting.AQUA), true);
                    break;

                case 2:
                    user.sendMessage(new TranslatableText("item.magicat.manafruit").formatted(Formatting.AQUA), true);
                    break;

                case 3:
                    user.sendMessage(new TranslatableText("item.magicat.catalyzer").formatted(Formatting.AQUA), true);
                    break;
            }
            if (usedSpell) user.getStackInHand(hand).damage(1, user, (p) -> {
                p.sendToolBreakStatus(hand);
            });
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        switch (selSpell) {
            case 0:
                tooltip.add(new TranslatableText("spell.regeneration").formatted(Formatting.AQUA));
                break;

            case 1:
                tooltip.add(new TranslatableText("item.magicat.manacrystal").formatted(Formatting.AQUA));
                break;

            case 2:
                tooltip.add(new TranslatableText("item.magicat.manafruit").formatted(Formatting.AQUA));
                break;

            case 3:
                tooltip.add(new TranslatableText("item.magicat.catalyzer").formatted(Formatting.AQUA));
                break;
        }
    }

    @Override
    public boolean isDamageable() {
        return true;
    }
}
