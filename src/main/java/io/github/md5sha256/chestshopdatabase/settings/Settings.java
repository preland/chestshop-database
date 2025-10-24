package io.github.md5sha256.chestshopdatabase.settings;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import javax.annotation.Nonnull;

@ConfigSerializable
public record Settings(
        @Setting("database-settings") @Nonnull DatabaseSettings databaseSettings
) {
}
