package io.github.md5sha256.chestshopdatabase.database;

import io.github.md5sha256.chestshopdatabase.settings.DatabaseSettings;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.JdbcType;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.util.Locale;
import java.util.UUID;

/**
 * Factory to build a SqlSessionFactory and choose the appropriate mapper at runtime
 * depending on `database-settings.type` ("mariadb" or "mysql").
 */
public final class DatabaseFactory {

    private DatabaseFactory() {
    }

    public static SqlSessionFactory buildSessionFactory(@Nonnull DatabaseSettings settings) {
        String type = settings.type();
        if (type == null) {
            type = "mariadb";
        }
        type = type.toLowerCase(Locale.ROOT);

        String driverClass;
        Class<? extends DatabaseMapper> mapperClass;

        switch (type) {
            case "mysql" -> {
                driverClass = "com.mysql.cj.jdbc.Driver";
                mapperClass = MysqlChestshopMapper.class;
            }
            case "mariadb" -> {
                driverClass = "org.mariadb.jdbc.Driver";
                mapperClass = MariaChestshopMapper.class;
            }
            default -> throw new IllegalArgumentException("Unsupported database type: " + type);
        }

        try {
            Class.forName(driverClass);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load driver: " + driverClass, ex);
        }

        DataSource dataSource = new PooledDataSource(driverClass, "jdbc:" + settings.url(), settings.username(), settings.password());
        Environment environment = new Environment("production", new JdbcTransactionFactory(), dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.getTypeHandlerRegistry().register(UUID.class, JdbcType.OTHER, UUIDAsBin16Handler.class);
        // register both mappers to be safe; the plugin will request the one it wants
        configuration.addMapper(MariaChestshopMapper.class);
        configuration.addMapper(MysqlChestshopMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    public static Class<? extends DatabaseMapper> mapperClassFor(@Nonnull DatabaseSettings settings) {
        String type = settings.type();
        if (type == null) {
            type = "mariadb";
        }
        type = type.toLowerCase(Locale.ROOT);
        return switch (type) {
            case "mysql" -> MysqlChestshopMapper.class;
            case "mariadb" -> MariaChestshopMapper.class;
            default -> throw new IllegalArgumentException("Unsupported database type: " + type);
        };
    }
}
