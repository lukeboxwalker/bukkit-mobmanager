package de.dicecraft.dicemobmanager.entity.factory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullFactory {

    private static final int RANDOM_ALPHANUMERIC = 10;

    public ItemStack createSkull(final String texture, final String sessionKey, final UUID uuid) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta headMeta = skull.getItemMeta();

        GameProfile profile = new GameProfile(uuid, sessionKey);
        Property textures = new Property(
                "textures", texture
        );
        profile.getProperties().put(textures.getName(), textures);

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(headMeta);
        return skull;
    }

    public ItemStack createSkull(final String texture) {
        String generatedString = RandomStringUtils.randomAlphanumeric(RANDOM_ALPHANUMERIC);
        return createSkull(texture, generatedString, UUID.randomUUID());
    }
}
