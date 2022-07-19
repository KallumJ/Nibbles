package team.bits.nibbles.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
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

    /**
     * Returns string that can be used in commands relating to this item stack, e.g /give
     * @param stack the ItemStack to transform into a string
     * @return the generated string
     */
    public static String stackToCommandString(ItemStack stack) {
        StringBuilder builder = new StringBuilder();
        builder.append(stack.getItem());

        if (stack.getNbt() != null) {
            String nbt = NbtHelper.toNbtProviderString(stack.getNbt()).trim();
            // Remove whitespace outside of quotes
            nbt = nbt.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)","");
            builder.append(nbt);
        }

        return builder.toString();
    }
}
