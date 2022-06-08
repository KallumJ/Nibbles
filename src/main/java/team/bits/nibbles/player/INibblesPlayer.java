package team.bits.nibbles.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface INibblesPlayer {

    /**
     * A method to check if this player has played on the server
     * before, or is a new player.
     *
     * @return true if this player has played on the server before
     */
    boolean hasPlayedBefore();

    /**
     * Give an {@link ItemStack} to this player. Drops the item on
     * the ground if there is no room in our inventory.
     *
     * @param stack the item stack to give to this player
     */
    void giveItem(@NotNull ItemStack stack);

    /**
     * Does the same as {@link INibblesPlayer#giveItem(ItemStack)}
     * but for a collection of item stacks.
     */
    void giveItems(@NotNull Collection<ItemStack> stacks);

    /**
     * Check if this player has at least a certain amount of
     * a given item in their inventory.
     *
     * @param item      the item type to look for
     * @param minAmount the minimum amount the player needs to have
     * @return true if the player has at least minAmount of item
     */
    boolean hasItem(@NotNull Item item, int minAmount);

    /**
     * Remove a certain amount of a given item from this player's
     * inventory. Will be removed from multiple different slots
     * if needed.
     *
     * @param item   the item type to remove
     * @param amount the amount to remove
     * @return true if the items have been removed successfully
     */
    boolean removeItem(@NotNull Item item, int amount);

    void playSound(SoundEvent sound, SoundCategory category, float volume, float pitch);
}
