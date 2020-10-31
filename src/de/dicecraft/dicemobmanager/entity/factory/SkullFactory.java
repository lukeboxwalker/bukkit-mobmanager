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

    /**
     * Creates a skull ItemStack.
     *
     * @param texture    the texture to set.
     * @param sessionKey the session key.
     * @param uuid       the uuid for the skull.
     * @return a skull ItemStack
     */
    public ItemStack createSkull(final String texture, final String sessionKey, final UUID uuid) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        final ItemMeta headMeta = skull.getItemMeta();

        final GameProfile profile = new GameProfile(uuid, sessionKey);
        final Property textures = new Property(
                "textures", texture
        );
        profile.getProperties().put(textures.getName(), textures);

        try {
            final Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(headMeta);
        return skull;
    }

    public ItemStack createSkull(final String texture) {
        final String generatedString = RandomStringUtils.randomAlphanumeric(RANDOM_ALPHANUMERIC);
        return createSkull(texture, generatedString, UUID.randomUUID());
    }
}
