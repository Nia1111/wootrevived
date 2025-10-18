package wootrevived.woot.mixins.impl;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Tesselator.class)
public interface TesselatorMixin {
    @Accessor(value = "buffer")
    ByteBufferBuilder woot$getBuffer();
}
