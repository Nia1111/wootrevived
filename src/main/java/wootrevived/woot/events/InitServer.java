package wootrevived.woot.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wootrevived.woot.Woot;
import wootrevived.woot.recipes.stygian_anvil.StygianAnvilRecipe;
import wootrevived.woot.recipes.dye_liquifier.DyeLiquifierRecipe;
import wootrevived.woot.recipes.fluid_infuser.FluidInfuserRecipe;
import wootrevived.woot.recipes.item_infuser.ItemInfuserRecipe;
import wootrevived.woot.drops.simulator.DropSimulatorDimension;
import wootrevived.woot.drops.simulator.DropSimulator;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID)
public class InitServer {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        RecipeManager recipeManager = event.getServer().getRecipeManager();
        StygianAnvilRecipe.loadRecipes(recipeManager);
        DyeLiquifierRecipe.loadRecipes(recipeManager);
        FluidInfuserRecipe.loadRecipes(recipeManager);
        ItemInfuserRecipe.loadRecipes(recipeManager);

        ServerLevel level = event.getServer().getLevel(DropSimulatorDimension.DROP_SIMULATOR_LEVEL);
        if(level != null)
            DropSimulator.init(level);
    }
}
