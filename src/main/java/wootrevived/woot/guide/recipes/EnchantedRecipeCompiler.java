package wootrevived.woot.guide.recipes;

import guideme.compiler.PageCompiler;
import guideme.compiler.tags.BlockTagCompiler;
import guideme.compiler.tags.MdxAttrs;
import guideme.document.block.LytBlockContainer;
import guideme.libs.mdast.mdx.model.MdxJsxElementFields;
import net.minecraft.util.Mth;
import wootrevived.woot.util.Config;

import java.util.*;

public class EnchantedRecipeCompiler extends BlockTagCompiler {
    @Override
    public Set<String> getTagNames() {
        return Set.of("EnchantedRecipe");
    }

    @Override
    protected void compile(PageCompiler compiler, LytBlockContainer parent, MdxJsxElementFields el) {
        int enchantLevel = Mth.clamp(MdxAttrs.getInt(compiler, parent, el, "lvl", 1), 1, Config.EnchantedLiquifier.MAX_ENCHANT_LVL);
        parent.append(new LytEnchantedLiquifierRecipe(enchantLevel));
    }
}
