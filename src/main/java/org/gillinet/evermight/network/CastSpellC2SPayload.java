
package org.gillinet.evermight.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CastSpellC2SPayload(double x, double y, double z) implements CustomPacketPayload {
    public static final Type<CastSpellC2SPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("evermight", "cast_spell"));

    public static final StreamCodec<ByteBuf, CastSpellC2SPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, CastSpellC2SPayload::x,
            ByteBufCodecs.DOUBLE, CastSpellC2SPayload::y,
            ByteBufCodecs.DOUBLE, CastSpellC2SPayload::z,
            CastSpellC2SPayload::new
    );

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
