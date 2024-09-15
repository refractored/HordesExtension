package net.refractored.hordes.commands

import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class SpawnHordeCommand {
    @CommandPermission("bloodmoon.admin.horde.spawn")
    @Description("Spawns a horde on a player.")
    @Command("bloodmoon spawn horde")
    fun execute(actor: BukkitCommandActor) {
        actor.reply(
            "test!",
        )
        TODO()
    }
}
