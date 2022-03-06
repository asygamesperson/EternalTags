package xyz.oribuin.eternaltags.command.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.framework.CommandContext;
import dev.rosewood.rosegarden.command.framework.RoseCommand;
import dev.rosewood.rosegarden.command.framework.RoseCommandWrapper;
import dev.rosewood.rosegarden.command.framework.annotation.RoseExecutable;
import dev.rosewood.rosegarden.utils.StringPlaceholders;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oribuin.eternaltags.conversion.ValidPlugin;
import xyz.oribuin.eternaltags.manager.LocaleManager;
import xyz.oribuin.eternaltags.manager.PluginConversionManager;
import xyz.oribuin.eternaltags.util.TagsUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConvertCommand extends RoseCommand {

    public ConvertCommand(RosePlugin rosePlugin, RoseCommandWrapper parent) {
        super(rosePlugin, parent);
    }

    @RoseExecutable
    public void execute(CommandContext context, ValidPlugin plugin) {
        final LocaleManager locale = this.rosePlugin.getManager(LocaleManager.class);
        final PluginConversionManager manager = this.rosePlugin.getManager(PluginConversionManager.class);
        CommandSender sender = context.getSender();

        // Check if the player arg was provided.
        if (plugin == null) {
            locale.sendMessage(sender, "command-convert-invalid-plugin", StringPlaceholders.single("options", TagsUtil.formatList(Arrays.stream(ValidPlugin.values())
                    .map(ValidPlugin::getDisplay)
                    .collect(Collectors.toList()))));

            return;
        }

        if (sender instanceof Player) {
            locale.sendMessage(sender, "only-console");
            return;
        }

        int total = manager.convertPlugin(plugin).size();
        locale.sendMessage(sender, "command-convert,converted", StringPlaceholders.single("total", total));
    }


    @Override
    protected String getDefaultName() {
        return "clear";
    }

    @Override
    public String getDescriptionKey() {
        return "command-clear-description";
    }

    @Override
    public String getRequiredPermission() {
        return "eternaltags.clear";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

}
