package team.bits.nibbles.database.driver;

import org.jetbrains.annotations.NotNull;
import team.bits.nibbles.utils.PropertiesFileUtils;

import java.io.File;
import java.util.Objects;
import java.util.Properties;

public final class DatabaseDriverFactory {

    private DatabaseDriverFactory() {
    }

    public static @NotNull IDatabaseDriver create(@NotNull String propertiesFilePath) {
        Objects.requireNonNull(propertiesFilePath);

        return create(PropertiesFileUtils.loadFromClasspath(propertiesFilePath));
    }

    public static @NotNull IDatabaseDriver create(@NotNull File propertiesFile) {
        Objects.requireNonNull(propertiesFile);

        return create(PropertiesFileUtils.loadFromFile(propertiesFile));
    }

    public static @NotNull IDatabaseDriver create(@NotNull Properties properties) {
        Objects.requireNonNull(properties);

        return new DatabaseDriver(new DatabaseProperties(properties));
    }
}
