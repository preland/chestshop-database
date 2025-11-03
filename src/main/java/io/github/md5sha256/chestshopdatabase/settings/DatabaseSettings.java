package io.github.md5sha256.chestshopdatabase.settings;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ConfigSerializable
public record DatabaseSettings(
        @Setting("url")
        @Required
        @Nonnull String url,
        @Setting("type")
        @Nullable String type,
        @Setting("username")
        @Nonnull String username,
        @Setting("password")
        @Nonnull String password
) {

}
