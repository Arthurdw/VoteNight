package net.xiler.mc.votenight.commands;

import net.xiler.mc.votenight.Main;
import net.xiler.mc.votenight.utils.chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class vote implements CommandExecutor {

    private final Main plugin;

    public vote(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("skip").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String[] msg;
        if (!sender.hasPermission(plugin.getConfig().getString("perms.vote"))) msg = chat.format(plugin, "messages.noperms");
        else if (plugin.getConfig().getStringList("worlds").contains(((Player) sender).getWorld().getName())) {
            long time = ((Player) sender).getWorld().getTime();
            if (time < 12300 || time > 23850) msg = chat.format(plugin, "messages.day");
            else {
                if (plugin.get_voters().contains(((Player) sender).getUniqueId().toString())) msg = chat.format(plugin, "messages.alreadyvoted");
                else {
                    msg = chat.format(plugin, "messages.success");
                    plugin.add_voter(((Player) sender).getUniqueId().toString());
                }
            }
        }
        else msg = chat.format(plugin, "messages.disabled");
        for (String message: msg) sender.sendMessage(message);
        return false;
    }
}
