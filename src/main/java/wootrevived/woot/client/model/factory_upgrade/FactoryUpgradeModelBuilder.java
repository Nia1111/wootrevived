package wootrevived.woot.client.model.factory_upgrade;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.BlocksRegistry;

public class FactoryUpgradeModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T> {
    public static <T extends ModelBuilder<T>> FactoryUpgradeModelBuilder<T> begin(T parent, ExistingFileHelper existingFileHelper)
    {
        return new FactoryUpgradeModelBuilder<>(parent, existingFileHelper);
    }

    public FactoryUpgradeModelBuilder(T parent, ExistingFileHelper existingFileHelper)
    {
        super(ResourceLocation.tryBuild(Woot.MOD_ID, BlocksRegistry.FACTORY_UPGRADE_TAG), parent, existingFileHelper, false);
    }
}
