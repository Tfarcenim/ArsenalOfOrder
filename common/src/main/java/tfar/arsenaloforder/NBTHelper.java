package tfar.arsenaloforder;

import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class NBTHelper {
    public static Boolean getBoolean(ItemStack stack, String key) {//booleans are bytes internally
        return stack.hasTag() && stack.getTag().contains(key, Tag.TAG_BYTE) ? stack.getTag().getBoolean(key) : null;
    }

    public static void setBoolean(ItemStack stack, String key, Boolean value) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putBoolean(key,value);
        }
    }

    public static Integer getInt(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_INT) ? stack.getTag().getInt(key) : null;
    }

    public static int getIntOrDefault(ItemStack stack,String key,int fallback) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_INT) ? stack.getTag().getInt(key) : fallback;
    }

    public static void setInt(ItemStack stack,String key,Integer value) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putInt(key,value);
        }
    }

    public static Long getLong(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_LONG) ? stack.getTag().getLong(key) : null;
    }

    public static void setLong(ItemStack stack,String key,Long value) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putLong(key,value);
        }
    }

    public static Float getFloat(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_FLOAT) ? stack.getTag().getFloat(key) : null;
    }

    public static void setFloat(ItemStack stack, String key, Float value) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putFloat(key,value);
        }
    }

    public static Double getDouble(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_DOUBLE) ? stack.getTag().getDouble(key) : null;
    }

    public static void setDouble(ItemStack stack, String key, Double value) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putDouble(key,value);
        }
    }

    static String getString(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_STRING) ? stack.getTag().getString(key) : null;
    }

    static void setString(ItemStack stack,String key,String value) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putString(key,value);
        }
    }
}
