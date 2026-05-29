package com.example.simpleessentials;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

/**
 * SimpleEssentials
 * Lightweight plugin for Minecraft (Spigot/Paper) 1.20.1.
 *
 * Commands:
 *   /gm0 [player]  - Survival
 *   /gm1 [player]  - Creative
 *   /gm2 [player]  - Adventure
 *   /gm3 [player]  - Spectator
 *   /feed [player] - Restore hunger and saturation
 *   /heal [player] - Restore full health, extinguish fire
 */
public class SimpleEssentials extends JavaPlugin {

    private static final String PREFIX = ChatColor.AQUA + "[SimpleEssentials] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        getLogger().info("SimpleEssentials enabled (MC 1.20.1).");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleEssentials disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName().toLowerCase()) {
            case "gm0":
                return handleGameMode(sender, args, GameMode.SURVIVAL);
            case "gm1":
                return handleGameMode(sender, args, GameMode.CREATIVE);
            case "gm2":
                return handleGameMode(sender, args, GameMode.ADVENTURE);
            case "gm3":
                return handleGameMode(sender, args, GameMode.SPECTATOR);
            case "feed":
                return handleFeed(sender, args);
            case "heal":
                return handleHeal(sender, args);
            default:
                return false;
        }
    }

    // ----- Game mode -----
    private boolean handleGameMode(CommandSender sender, String[] args, GameMode mode) {
        if (!sender.hasPermission("simpleessentials.gamemode")) {
            sendNoPermission(sender);
            return true;
        }

        Player target = resolveTarget(sender, args);
        if (target == null) {
            return true; // message already sent
        }

        target.setGameMode(mode);
        String modeName = capitalize(mode.name());

        target.sendMessage(PREFIX + ChatColor.GREEN + "Your game mode is now " + modeName + ".");
        if (!target.equals(sender)) {
            sender.sendMessage(PREFIX + ChatColor.GREEN + "Set " + target.getName()
                    + "'s game mode to " + modeName + ".");
        }
        return true;
    }

    // ----- Feed -----
    private boolean handleFeed(CommandSender sender, String[] args) {
        if (!sender.hasPermission("simpleessentials.feed")) {
            sendNoPermission(sender);
            return true;
        }

        Player target = resolveTarget(sender, args);
        if (target == null) {
            return true;
        }

        target.setFoodLevel(20);
        target.setSaturation(20f);
        target.setExhaustion(0f);

        target.sendMessage(PREFIX + ChatColor.GREEN + "Your hunger has been restored.");
        if (!target.equals(sender)) {
            sender.sendMessage(PREFIX + ChatColor.GREEN + "Fed " + target.getName() + ".");
        }
        return true;
    }

    // ----- Heal -----
    private boolean handleHeal(CommandSender sender, String[] args) {
        if (!sender.hasPermission("simpleessentials.heal")) {
            sendNoPermission(sender);
            return true;
        }

        Player target = resolveTarget(sender, args);
        if (target == null) {
            return true;
        }

        AttributeInstance maxHealthAttr = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double maxHealth = (maxHealthAttr != null) ? maxHealthAttr.getValue() : 20.0D;
        target.setHealth(maxHealth);
        target.setFireTicks(0);
        target.setFreezeTicks(0);

        target.sendMessage(PREFIX + ChatColor.GREEN + "You have been healed.");
        if (!target.equals(sender)) {
            sender.sendMessage(PREFIX + ChatColor.GREEN + "Healed " + target.getName() + ".");
        }
        return true;
    }

    // ----- Helpers -----

    /**
     * Resolves the player the command should affect.
     * - With no args: the sender (must be a player).
     * - With one arg: the named online player.
     * Returns null and sends an explanatory message if resolution fails.
     */
    private Player resolveTarget(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                return (Player) sender;
            }
            sender.sendMessage(PREFIX + ChatColor.RED + "Console must specify a player. Usage: <command> <player>");
            return null;
        }

        Player target = getServer().getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Player '" + args[0] + "' is not online.");
            return null;
        }
        return target;
    }

    private void sendNoPermission(CommandSender sender) {
        sender.sendMessage(PREFIX + ChatColor.RED + "You don't have permission to use this command.");
    }

    private String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.charAt(0) + value.substring(1).toLowerCase();
    }
}
