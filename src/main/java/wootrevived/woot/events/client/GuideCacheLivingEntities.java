package wootrevived.woot.events.client;

import guideme.scene.level.GuidebookLevel;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wootrevived.api.WootFactoryMob;
import wootrevived.woot.Woot;
import wootrevived.woot.registries.WootFactoryMobsRegistry;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID, value = { Dist.CLIENT })
public class GuideCacheLivingEntities {
    private static final Map<EntityType<?>, LivingEntity> livingEntities = new HashMap<>();

    public static Map<EntityType<?>, LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    @SubscribeEvent
    @SuppressWarnings("UnstableApiUsage")
    public static void onScreenEventOpening(ScreenEvent.Opening event){
        if(event.getNewScreen() instanceof TitleScreen){
            LayeredRegistryAccess<RegistryLayer> layeredAccess = RegistryLayer.createRegistryAccess();

            PackRepository packRepository = new PackRepository(new ServerPacksSource());
            packRepository.reload();
            packRepository.setSelected(packRepository.getAvailableIds());

            var resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, packRepository.openAllSelected());

            var worldgenLayer = RegistryDataLoader.load(
                    resourceManager,
                    layeredAccess.getAccessForLoading(RegistryLayer.WORLDGEN),
                    RegistryDataLoader.WORLDGEN_REGISTRIES
            );

            GuidebookLevel level = new GuidebookLevel(layeredAccess.replaceFrom(RegistryLayer.WORLDGEN, worldgenLayer).compositeAccess());

            livingEntities.clear();

            for(WootFactoryMob<?> mob : WootFactoryMobsRegistry.getFactoryMobValues()){
                if(mob.isBlacklisted()) continue;
                EntityType<?> entityType = mob.getEntityType();
                Entity entity = entityType.create(level);
                if(!(entity instanceof LivingEntity livingEntity)) continue;
                livingEntities.put(entityType, livingEntity);
            }
        }
    }
}
