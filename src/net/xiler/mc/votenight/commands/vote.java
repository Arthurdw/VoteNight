package net.xiler.mc.votenight.commands;

import net.xiler.mc.votenight.Main;
import net.xiler.mc.votenight.utils.chat;
import org.bukkit.Bukkit;
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
        if (!(sender instanceof Player)) {
            sender.sendMessage(chat.parse(plugin, "&4This command can only be executed by a player!"));
            return true;
        }
        String[] msg, bc = new String[0];
        if (!sender.hasPermission(plugin.getConfig().getString("perms.vote"))) msg = chat.format(plugin, "messages.noperms");
        else if (plugin.getConfig().getStringList("worlds").contains(((Player) sender).getWorld().getName())) {
            long time = ((Player) sender).getWorld().getTime();
            if (time < 12300 || time > 23850) msg = chat.format(plugin, "messages.day");
            else {
                if (plugin.get_voters().contains(((Player) sender).getUniqueId().toString())) msg = chat.format(plugin, "messages.alreadyvoted");
                else {
                    msg = chat.format(plugin, "messages.success");
                    plugin.add_voter(((Player) sender).getUniqueId().toString());
                    if (plugin.get_voters().size() >= Math.round(Math.ceil(0.6*((Player) sender).getWorld().getPlayers().size()))) {
                        bc = plugin.getConfig().getStringList("messages.skipping").toArray(new String[0]);
                        ((Player) sender).getWorld().setTime(23850);
                        plugin.reset_voters();
                    } else bc = plugin.getConfig().getStringList("messages.vote").toArray(new String[0]);
                }
            }
        }
        else msg = chat.format(plugin, "messages.disabled");
        for (String message: msg) sender.sendMessage(message);
        if (bc != null) for (String message: bc) Bukkit.broadcastMessage(chat.parse(plugin, message)
                .replace("{member}", ((Player) sender).getDisplayName())
                .replace("{totalvoted}", String.valueOf(plugin.get_voters().size()))
                .replace("{required}", String.valueOf(Math.round(Math.ceil(0.6*((Player) sender).getWorld().getPlayers().size())))));
        return false;
    }
}
