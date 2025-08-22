package wootrevived.woot.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import wootrevived.api.WootFactoryMob;
import wootrevived.woot.blocks.fake_spawner.FakeSpawnerBlockEntity;
import wootrevived.woot.registries.WootFactoryMobsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GiveCommand {
    private static final SuggestionProvider<CommandSourceStack> suggestionProvider = new SuggestionProvider<>() {
        private static final List<ResourceLocation> mobLocations = new ArrayList<>();

        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> commandContext, SuggestionsBuilder suggestionsBuilder) {
            if(mobLocations.isEmpty()) {
                for(WootFactoryMob<?> mob : WootFactoryMobsRegistry.getFactoryMobValues()){
                    if(mob.isBlacklisted()) continue;
                    EntityType<?> entityType = mob.getEntityType();
                    Entity entity = entityType.create(commandContext.getSource().getLevel());
                    if(!(entity instanceof LivingEntity)) continue;
                    ResourceLocation location = ForgeRegistries.ENTITY_TYPES.getKey(entityType);
                    mobLocations.add(location);
                }
            }

            return SharedSuggestionProvider.suggestResource(mobLocations.stream(), suggestionsBuilder);
        }
    };

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("give")
                .requires(cs -> cs.hasPermission(2))
                .then(
                    Commands.argument("target", EntityArgument.player())
                            .then(
                                    Commands.argument("entity", ResourceLocationArgument.id()).suggests(suggestionProvider)
                                            .executes(ctx -> giveItem(
                                                    ctx.getSource(),
                                                    EntityArgument.getPlayer(ctx, "target"),
                                                    ResourceLocationArgument.getId(ctx, "entity")
                                            ))
                            )
                );
    }

    private static int giveItem(CommandSourceStack source, ServerPlayer target, ResourceLocation resourceLocation) {
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(resourceLocation);
        WootFactoryMob<?> mob = WootFactoryMobsRegistry.getFactoryMob(entityType);

        Object object = entityType.create(source.getLevel());
        if(object instanceof LivingEntity entity){
            CompoundTag tag = mob.saveTag(entity.serializeNBT());
            ItemStack fakeSpawner = FakeSpawnerBlockEntity.getItemStack(tag);
            ItemHandlerHelper.giveItemToPlayer(target, fakeSpawner);
        }

        return 1;
    }
}
