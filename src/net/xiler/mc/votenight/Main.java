package net.xiler.mc.votenight;

import net.xiler.mc.votenight.commands.vote;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {

    private final ArrayList voters;

    public Main() {
        this.voters = new ArrayList();
    }

    @Override
    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        new vote(this);
        getLogger().info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now enabled!");
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now disabled!");
    }

    public ArrayList get_voters() {
        return this.voters;
    }

    public void add_voter(String voter) {
        this.voters.add(voter);
    }
}
