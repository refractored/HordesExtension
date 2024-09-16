package net.refractored.hordes.commands

import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.bloodmoonreloaded.exceptions.CommandErrorException
import net.refractored.bloodmoonreloaded.util.MessageUtil.getStringPrefixed
import net.refractored.bloodmoonreloaded.util.MessageUtil.miniToComponent
import net.refractored.hordes.hordes.HordeRegistry
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class SpawnHordeCommand {
    @CommandPermission("bloodmoon.admin.horde.spawn")
    @Description("Spawns a horde on a player.")
    @Command("bloodmoon spawn horde")
    fun execute(
        actor: BukkitCommandActor,
        player: Player,
        @Optional announce: Boolean = true,
    ) {
        HordeRegistry.getHordeConfig(player.world)?.spawnHorde(player, announce) ?: throw CommandErrorException(
            BloodmoonPlugin.instance.langYml
                .getStringPrefixed("messages.NoHordeConfigFound")
                .miniToComponent(),
        )
        actor.reply(
            BloodmoonPlugin.instance.langYml
                .getStringPrefixed("messages.SpawnedHordeOnPlayer")
                .replace("%player%", player.name)
                .miniToComponent(),
        )
    }
}
