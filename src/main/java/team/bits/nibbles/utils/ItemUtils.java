package team.bits.nibbles.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemUtils {

    public static void setLore(@NotNull ItemStack stack, @NotNull List<Text> tooltips) {
        NbtList loreNbt = new NbtList();
        for (Text tooltip : tooltips) {
            loreNbt.add(NbtString.of(Text.Serializer.toJson(tooltip)));
        }

        NbtCompound displayNbt = new NbtCompound();
        displayNbt.put(ItemStack.LORE_KEY, loreNbt);

        stack.setSubNbt(ItemStack.DISPLAY_KEY, displayNbt);
    }
}
