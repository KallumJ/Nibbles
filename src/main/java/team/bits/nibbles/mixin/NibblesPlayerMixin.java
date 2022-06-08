package team.bits.nibbles.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.bits.nibbles.event.base.EventManager;
import team.bits.nibbles.player.CopyPlayerDataEvent;
import team.bits.nibbles.player.INibblesPlayer;

import java.util.Collection;

@Mixin(PlayerEntity.class)
public abstract class NibblesPlayerMixin implements INibblesPlayer {

    /* ### Custom variables ### */

    /**
     * True if the player has played on the server before, false if
     * this is the first time the player joined.
     */
    private boolean hasPlayedBefore;

    /* ### Shadowed members ### */

    @Shadow
    @Final
    private PlayerInventory inventory;

    @Shadow
    public abstract @Nullable ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

    /* ### Mixin functions ### */

    /**
     * Read custom data from the player's NBT
     */
    @Inject(

            method = "readCustomDataFromNbt",
            at = @At("RETURN")
    )
    public void readNbtData(NbtCompound nbt, CallbackInfo ci) {
        // if this method gets called, it means there is data to
        // read, which means the player has played before
        this.hasPlayedBefore = true;
    }

    /**
     * Write custom data to the player's NBT
     */
    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("RETURN")
    )
    public void writeNbtData(NbtCompound nbt, CallbackInfo ci) {
    }

    /**
     * @see CopyPlayerDataEvent
     */
    protected void nibblesCopyFromOldPlayer(@NotNull INibblesPlayer oldPlayer) {
        this.hasPlayedBefore = oldPlayer.hasPlayedBefore();
    }

    /* ### Function implementations ### */

    @Override
    public boolean hasPlayedBefore() {
        return this.hasPlayedBefore;
    }

    @Override
    public void giveItem(@NotNull ItemStack stack) {
        Validate.notNull(stack);

        // try and insert the stack into our inventory
        if (!this.inventory.insertStack(stack)) {

            // if inserting fails, drop the stack on the ground
            ItemEntity itemEntity = this.dropItem(stack, false);
            if (itemEntity != null) {
                itemEntity.resetPickupDelay();
            }
        }
    }

    @Override
    public void giveItems(@NotNull Collection<ItemStack> stacks) {
        Validate.notNull(stacks);

        // call the giveItem method for every stack in the collection
        stacks.forEach(this::giveItem);
    }

    @Override
    public boolean hasItem(@NotNull Item item, int minAmount) {
        Validate.notNull(item);
        Validate.isTrue(minAmount > 0);

        // loop over all the slots in our inventory
        for (int slot = 0; slot < this.inventory.size(); slot++) {
            // check if the stack in the slot is the same item as we're looking for
            ItemStack stack = this.inventory.getStack(slot);
            if (stack.isOf(item)) {
                // if the stack in the slot has enough items, return true
                // otherwise, decrement the minAmount with the stack size
                int count = stack.getCount();
                if (count >= minAmount) {
                    return true;
                } else {
                    minAmount -= count;
                }
            }
        }

        return false;
    }

    @Override
    public boolean removeItem(@NotNull Item item, int amount) {
        Validate.notNull(item);
        Validate.isTrue(amount > 0);

        // if we don't have enough of the item, return false right away
        if (!this.hasItem(item, amount)) {
            return false;
        }

        // loop over all the slots in our inventory
        for (int slot = 0; slot < this.inventory.size(); slot++) {
            // check if the stack in the slot is the same item as we're looking for
            ItemStack stack = this.inventory.getStack(slot);
            if (stack.isOf(item)) {
                // remove the requested amount from the slot. if the slot
                // doesn't have enough we remove whatever possible and
                // keep looking for more
                int remove = Math.min(amount, stack.getCount());
                this.inventory.removeStack(slot, remove);
                amount -= remove;

                // once we've removed enough, break out of the loop
                if (amount == 0) {
                    break;
                }
            }
        }

        return true;
    }

    @Override
    public void playSound(SoundEvent sound, SoundCategory soundCategory, float volume, float pitch) {
        final ServerPlayerEntity player = ServerPlayerEntity.class.cast(this);
        final ServerWorld world = player.getWorld();
        final Vec3d position = player.getPos();

        world.playSound(
                null, position.x, position.y, position.z,
                sound, soundCategory, volume, pitch
        );
    }

    /*
     * This is a little static event handler for passing the CopyPlayerData
     * event through to the right NibblesPlayerMixin instance
     */
    static {
        EventManager.INSTANCE.registerEvents((CopyPlayerDataEvent.Listener) event ->
                _nibblesCopyPlayerData(event.getOldPlayer(), event.getNewPlayer())
        );
    }

    private static void _nibblesCopyPlayerData(@NotNull INibblesPlayer oldPlayer, @NotNull INibblesPlayer newPlayer) {
        ((NibblesPlayerMixin) newPlayer).nibblesCopyFromOldPlayer((NibblesPlayerMixin) oldPlayer);
    }
}
