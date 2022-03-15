package one.mcauth.magicatfabric;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import one.mcauth.magicatfabric.components.ManaComponent;
import one.mcauth.magicatfabric.components.PlayerManaComponent;

public class MagicatComponents implements EntityComponentInitializer {
    public static final ComponentKey<ManaComponent> COMPONENT_MANA =
            ComponentRegistry.getOrCreate(new Identifier("magicat", "manacomponent"), ManaComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(COMPONENT_MANA, (PlayerEntity t) -> new PlayerManaComponent(t), RespawnCopyStrategy.ALWAYS_COPY);
    }
}
