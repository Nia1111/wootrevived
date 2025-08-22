package wootrevived.woot.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;
import wootrevived.woot.client.sprite.factory_upgrade.FactoryUpgradeDynamicSpriteSource;

public class Atlas extends SpriteSourceProvider {
    public Atlas(PackOutput output, ExistingFileHelper fileHelper){
        super(output, fileHelper, "minecraft");
    }

    @Override
    protected void addSources() {
        atlas(BLOCKS_ATLAS)
                .addSource(new FactoryUpgradeDynamicSpriteSource(FactoryUpgradeDynamicSpriteSource.Atlas.BLOCKS));
    }
}
