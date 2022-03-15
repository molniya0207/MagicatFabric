package one.mcauth.magicatfabric.components;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ManaComponent extends Component {
    int getMana();
    void setMana(int mana);
    void addMana(int mana);
    void removeMana(int mana);
    boolean isEnoughMana(int mana);

    boolean getAvailable();
    void setAvailable(boolean available);

    int getManaRechargeSpeed();
    void setManaRechargeSpeed(int manarc);
    void addManaRechargeSpeed(int manarc);
    void removeManaRechargeSpeed(int manarc);
}
