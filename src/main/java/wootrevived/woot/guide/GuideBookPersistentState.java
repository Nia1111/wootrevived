package wootrevived.woot.guide;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import wootrevived.woot.Woot;
import wootrevived.woot.util.entity.WootTags;

import java.util.HashSet;
import java.util.Set;

public class GuideBookPersistentState extends SavedData {
    private static final Factory<GuideBookPersistentState> FACTORY = new Factory<>(GuideBookPersistentState::new, GuideBookPersistentState::fromTag);
    private static final String NAME = Woot.MOD_ID + "_guidebook";
    private final Set<String> receivedPlayers;

    private GuideBookPersistentState(Set<String> receivedPlayers) {
        this.receivedPlayers = receivedPlayers;
    }

    private GuideBookPersistentState() {
        this(new HashSet<>());
    }

    public boolean hasPlayerReceivedGuideBook(Player player) {
        return receivedPlayers.contains(player.getStringUUID());
    }

    public void addPlayerReceivedGuideBook(Player player) {
        receivedPlayers.add(player.getStringUUID());
        setDirty();
    }

    public static GuideBookPersistentState fromTag(CompoundTag tag, HolderLookup.Provider provider){
        Set<String> receivedPlayers = new HashSet<>();
        ListTag list = tag.getList(WootTags.GUIDE_PLAYER_TAG, Tag.TAG_STRING);
        for(int i = 0; i < list.size(); i++){
            receivedPlayers.add(list.getString(i));
        }
        return new GuideBookPersistentState(receivedPlayers);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        ListTag list = new ListTag();
        for(String receivedPlayer : receivedPlayers) {
            list.add(StringTag.valueOf(receivedPlayer));
        }
        tag.put(WootTags.GUIDE_PLAYER_TAG, list);
        return tag;
    }

    public static GuideBookPersistentState get(MinecraftServer server){
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);
        return level.getDataStorage().computeIfAbsent(FACTORY, NAME);
    }
}
