package net.refractored.hordes

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.config.Configs
import com.willfp.eco.core.config.interfaces.Config
import com.willfp.eco.core.extensions.Extension
import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.hordes.commands.SpawnHordeCommand
import java.io.File

class HordesExtension(
    plugin: EcoPlugin,
) : Extension(plugin) {
    init {
        instance = this
    }

    lateinit var hordeConfig: Config

    override fun onEnable() {
        if (!File(dataFolder, "hordes.yml").exists()) {
            BloodmoonPlugin.instance.saveResource("hordes.yml", false)
        }

        hordeConfig = Configs.fromFile(dataFolder.resolve("hordes.yml"))
    }

    override fun onAfterLoad() {
        BloodmoonPlugin.instance.handler.register(SpawnHordeCommand())
        BloodmoonPlugin.instance.handler.registerBrigadier()
    }

    override fun onDisable() {
    }

    companion object {
        /**
         * The extension's instance
         */
        lateinit var instance: HordesExtension
            private set
    }
}
