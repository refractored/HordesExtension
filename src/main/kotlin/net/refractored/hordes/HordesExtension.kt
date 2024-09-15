package net.refractored.hordes

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.extensions.Extension
import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.hordes.commands.SpawnHordeCommand

class HordesExtension(
    plugin: EcoPlugin,
) : Extension(plugin) {
    override fun onEnable() {
    }

    override fun onAfterLoad() {
        BloodmoonPlugin.instance.handler.register(SpawnHordeCommand())
        BloodmoonPlugin.instance.handler.registerBrigadier()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
