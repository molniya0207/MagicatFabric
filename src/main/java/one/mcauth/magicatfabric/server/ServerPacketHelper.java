package one.mcauth.magicatfabric.server;

import com.ibm.icu.impl.duration.impl.Utils;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.NetworkState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ServerPacketHelper implements DedicatedServerModInitializer {
    public static Identifier INFUSE_PACKET_ID = new Identifier("magicat", "infuse_packet");

    @Override
    public void onInitializeServer() {

    }
}
