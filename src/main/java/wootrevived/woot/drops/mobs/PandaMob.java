package wootrevived.woot.drops.mobs;

import com.google.common.base.CaseFormat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Panda;
import wootrevived.api.WootFactoryMob;
import wootrevived.api.registrations.WootFactoryMobRegistration;

public class PandaMob extends WootFactoryMob<Panda> {
    public PandaMob(EntityType<Panda> entityType, Properties properties) {
        super(entityType, properties);
    }

    @Override
    public MutableComponent getDisplayName(CompoundTag mobTag) {
        MutableComponent tip = Component.empty();
        if(mobTag.getString("MainGene").equals(mobTag.getString("HiddenGene"))){
            tip.append(Component.literal(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, mobTag.getString("MainGene")).replaceAll("([a-z])([A-Z])", "$1 $2") + " "));
        } else {
            tip.append(Component.literal("Normal "));
        }
        return tip.append(super.getDisplayName(mobTag));
    }

    @Override
    public MutableComponent getTooltipKillName(CompoundTag mobTag) {
        return super.getDisplayName(mobTag);
    }

    @Override
    public CompoundTag saveTag(CompoundTag mobTag){
        CompoundTag tag = super.saveTag(mobTag);
        tag.putString("MainGene", mobTag.getString("MainGene"));
        tag.putString("HiddenGene", mobTag.getString("HiddenGene"));
        return tag;
    }

    public static void register(WootFactoryMobRegistration registration) {
        registration.registerFactoryMob(new PandaMob(EntityType.PANDA, new Properties()));
    }
}
