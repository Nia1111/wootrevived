package wootrevived.api;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import wootrevived.api.interfaces.WootDropsProperties;
import wootrevived.api.interfaces.WootGenerationProperties;
import wootrevived.api.interfaces.WootSpawnProperties;

public abstract class WootUpgradeItem extends Item {
    /**
     * The current level of your {@link WootUpgradeItem upgrade item}.
     */
    protected final int level;

    public WootUpgradeItem(Properties properties, int level) {
        super(properties);
        this.level = level;
    }

    public void applyGenerationProperties(WootGenerationProperties properties) {
    }

    public void applySpawnProperties(WootSpawnProperties properties){
    }

    public void modifyDrops(WootDropsProperties properties) {
    }

    /**
     * @return The level of the upgrade item
     */
    public int getLevel(){
        return this.level;
    }

    /**
     * @return The location to your item.png texture based on the level
     */
    public ResourceLocation getTextureLocation(){
        return ForgeRegistries.ITEMS.getKey(this).withPrefix("textures/item/").withSuffix(".png");
    }

    /**
     * This is called when the factory block side is generated on the atlas.
     * The {@code NativeImage upgradeSide} will display on the block when
     * your upgrade will be applied.
     * @param upgradeSide The {@link NativeImage native image} of the factory upgrade block
     * @param upgradeItem The {@link NativeImage native image} of your upgrade item
     */
    @OnlyIn(Dist.CLIENT)
    public void applyUpgradeTexture(NativeImage upgradeSide, NativeImage upgradeItem){
        for(int y = 2; y < 14; y++){
            for(int x = 2; x < 14; x++){
                int color = upgradeItem.getPixelRGBA(x, y);
                int alpha = color >> 24;
                if(alpha != 0)
                    upgradeSide.setPixelRGBA(x, y, color);
            }
        }
    }
}
