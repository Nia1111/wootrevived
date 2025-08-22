package wootrevived.woot.drops.mobs;

import com.google.common.base.CaseFormat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.registrations.WootFactoryMobRegistration;

public class AxolotlMob extends WootFactoryMob<Axolotl> {
    public AxolotlMob(EntityType<Axolotl> entityType, Properties properties) {
        super(entityType, properties);
    }

    @Override
    public MutableComponent getDisplayName(CompoundTag mobTag) {
        Axolotl.Variant variant = Axolotl.Variant.byId(mobTag.getInt("Variant"));
        MutableComponent tip = Component.literal(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, variant.getSerializedName()).replaceAll("([a-z])([A-Z])", "$1 $2") + " ");
        return tip.append(super.getDisplayName(mobTag));
    }

    @Override
    public MutableComponent getTooltipKillName(CompoundTag mobTag) {
        return super.getDisplayName(mobTag);
    }

    @Override
    public CompoundTag saveTag(CompoundTag mobTag){
        CompoundTag tag = super.saveTag(mobTag);
        tag.putInt("Variant", mobTag.getInt("Variant"));
        return tag;
    }

    public static void register(WootFactoryMobRegistration registration) {
        registration.registerFactoryMob(new AxolotlMob(EntityType.AXOLOTL, new Properties()));
    }
}
