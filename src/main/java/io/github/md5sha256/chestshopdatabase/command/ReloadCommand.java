package io.github.md5sha256.chestshopdatabase.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.md5sha256.chestshopdatabase.ChestshopDatabasePlugin;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public record ReloadCommand(@Nonnull ChestshopDatabasePlugin plugin) implements CommandBean.Single {

    @Override
    public @NotNull LiteralArgumentBuilder<CommandSourceStack> command() {
        return Commands.literal("reload")
                .requires(sourceStack -> sourceStack.getSender().hasPermission("csdb.reload"))
                .executes(ctx -> {
                    ctx.getSource().getSender().sendMessage(
                            Component.text("Reloading ChestShop Database plugin...", NamedTextColor.YELLOW)
                    );
                    
                    try {
                        plugin.reloadPlugin();
                        ctx.getSource().getSender().sendMessage(
                                Component.text("Plugin reloaded successfully!", NamedTextColor.GREEN)
                        );
                    } catch (Exception ex) {
                        ctx.getSource().getSender().sendMessage(
                                Component.text("Failed to reload plugin: " + ex.getMessage(), NamedTextColor.RED)
                        );
                        plugin.getLogger().severe("Failed to reload plugin:");
                        ex.printStackTrace();
                    }
                    
                    return Command.SINGLE_SUCCESS;
                });
    }
}
