package io.github.md5sha256.chestshopdatabase.gui.dialog;

import io.github.md5sha256.chestshopdatabase.gui.FindState;
import io.github.md5sha256.chestshopdatabase.util.DialogUtil;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FindDialog {

    @Nonnull
    private static DialogBase createMainPageBase(@Nullable ItemStack searchQuery) {
        var builder = DialogBase.builder(Component.text("Find ChestShops"))
                .canCloseWithEscape(true);
        if (searchQuery == null) {
            return builder.build();
        }
        return builder.body(List.of(DialogBody.item(searchQuery).build())).build();
    }

    private static Dialog waitScreen() {
        return Dialog.create(factory -> factory
                .empty()
                .base(waitScreenBase())
                .type(DialogType.notice())
        );
    }

    private static DialogBase waitScreenBase() {
        return DialogBase.builder(Component.text("Chest Shop Query"))
                .canCloseWithEscape(true)
                .body(List.of(DialogBody.plainMessage(Component.text("Querying..."))))
                .build();
    }

    @Nonnull
    public static Dialog createMainPageDialog(
            @Nonnull FindState findState
    ) {
        DialogAction submitAction = DialogAction.customClick((view, audience) -> {
            audience.closeDialog();
            audience.showDialog(waitScreen());
        }, ClickCallback.Options.builder().uses(1).build());
        ActionButton submitButton = ActionButton.builder(Component.text("Submit Query"))
                .action(submitAction)
                .build();
        ActionButton exitButton = ActionButton.builder(Component.text("Exit"))
                .action(DialogUtil.CLOSE_DIALOG_ACTION)
                .build();
        List<ActionButton> actions = List.of(
                ActionButton.builder(Component.text("Filters"))
                        .action(DialogUtil.openDialogAction(() -> FilterDialog.createFiltersDialog(
                                findState, () -> createMainPageDialog(findState))))
                        .build(),
                ActionButton.builder(Component.text("Sorting"))
                        .action(DialogUtil.openDialogAction(() -> SortDialog.createSortDialog(
                                findState, () -> createMainPageDialog(findState))))
                        .build(),
                exitButton
        );

        return Dialog.create(factory ->
                factory.empty()
                        .base(createMainPageBase(findState.item()))
                        .type(DialogType.multiAction(actions)
                                .exitAction(submitButton)
                                .columns(1)
                                .build()));
    }

}
