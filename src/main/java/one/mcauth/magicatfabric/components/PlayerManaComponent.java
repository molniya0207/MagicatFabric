package one.mcauth.magicatfabric.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.nbt.NbtCompound;
import one.mcauth.magicatfabric.Magicat;
import one.mcauth.magicatfabric.MagicatComponents;

public class PlayerManaComponent implements ManaComponent, AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {
    private final Object provider;
    int mana = 0;
    int manars = 5;
    int currentTickCounter = 0;
    boolean available = false;

    public PlayerManaComponent(Object provider) {
        this.provider = provider;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
        checkManaLimit();
    }

    @Override
    public void addMana(int mana) {
        this.mana += mana;
        checkManaLimit();
    }

    @Override
    public void removeMana(int mana) {
        this.mana -= mana;
        checkManaLimit();
    }

    @Override
    public boolean isEnoughMana(int mana) {
        return this.mana >= mana;
    }

    @Override
    public boolean getAvailable() {
        return this.available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
        checkManaLimit();
    }

    @Override
    public int getManaRechargeSpeed() {
        return this.manars;
    }

    @Override
    public void setManaRechargeSpeed(int manarc) {
        this.manars = manarc;
        checkManaRSLimit();
    }

    @Override
    public void addManaRechargeSpeed(int manarc) {
        this.manars += manarc;
        checkManaRSLimit();
    }

    @Override
    public void removeManaRechargeSpeed(int manarc) {
        this.manars -= manarc;
        checkManaRSLimit();
    }

    void checkManaLimit() {
        if (this.mana > 100) {
            this.mana = 100;
        }
        MagicatComponents.COMPONENT_MANA.sync(this.provider);
    }

    void checkManaRSLimit() {
        if (this.manars < 1) {
            this.manars = 1;
        }
        MagicatComponents.COMPONENT_MANA.sync(this.provider);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.mana = tag.getInt("mana");
        this.available = tag.getBoolean("manaavailable");
        this.manars = tag.getInt("manars");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("mana", this.mana);
        tag.putBoolean("manaavailable", this.available);
        tag.putInt("manars", this.manars);
    }

    @Override
    public void serverTick() {
        Magicat.LOGGER.info("PlayerManaComponent#serverTick");
        boolean isAvailable = false;//MagicatComponents.COMPONENT_MANA_AVAILABLE.get(this.provider).getAvailable();
        if (isAvailable) {
            currentTickCounter++;
            if (currentTickCounter >= this.manars) {
                currentTickCounter = 0;
                this.mana += 1;
                checkManaLimit();
            }
        }
    }

    @Override
    public void clientTick() {
        Magicat.LOGGER.info("PlayerManaComponent#clientTick");
    }
}
