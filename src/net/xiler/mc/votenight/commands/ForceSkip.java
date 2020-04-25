package net.xiler.mc.votenight.commands;

import net.xiler.mc.votenight.Main;
import net.xiler.mc.votenight.utils.chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceSkip implements CommandExecutor {

    private final Main plugin;

    public ForceSkip(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("forceskip").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(chat.parse(plugin, "&4This command can only be executed by a player!"));
            return true;
        }
        String[] msg = new String[0], bc = new String[0];
        if (!sender.hasPermission(plugin.getConfig().getString("perms.admin.forceskip"))) msg = chat.format(plugin, "messages.noperms");
        else if (plugin.getConfig().getStringList("worlds").contains(((Player) sender).getWorld().getName())) {
            long time = ((Player) sender).getWorld().getTime();
            if (time < 12300 || time > 23850) msg = chat.format(plugin, "messages.day");
            else {
                ((Player) sender).getWorld().setTime(23850);
                plugin.reset_voters();
                bc = plugin.getConfig().getStringList("messages.forceskip").toArray(new String[0]);
            }
        }
        else msg = chat.format(plugin, "messages.disabled");
        if (msg != null) for (String message: msg) sender.sendMessage(message);
        if (bc != null) for (String message: bc) Bukkit.broadcastMessage(chat.parse(plugin, message));
        return false;
    }
}
