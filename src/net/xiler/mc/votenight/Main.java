package net.xiler.mc.votenight;

import net.xiler.mc.votenight.commands.ForceSkip;
import net.xiler.mc.votenight.commands.vote;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    private ArrayList voters;

    public Main() { this.voters = new ArrayList(); }

    @Override
    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        saveDefaultConfig();
        if (!(0 < getConfig().getInt("required") && getConfig().getInt("required") <= 100)) {
            getLogger().severe("The 'required' field value has to be between 1 & 100");
            getLogger().info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now disabled!\n" +
                    "The 'required' field value has to be between 1 & 100 (config.yml)");
            getPluginLoader().disablePlugin(this);
        } else {
            new vote(this);
            new ForceSkip(this);
            getLogger().info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " is now enabled!");
        }
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

    public void reset_voters() {
        this.voters = new ArrayList();
    }
}
