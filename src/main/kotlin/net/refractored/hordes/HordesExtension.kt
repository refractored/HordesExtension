package net.refractored.hordes

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.extensions.Extension
import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.hordes.commands.SpawnHordeCommand
import net.refractored.hordes.hordes.HordeRegistry
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class HordesExtension(
    plugin: EcoPlugin,
) : Extension(plugin) {
    init {
        instance = this
    }

    lateinit var hordeConfig: YamlConfiguration
        private set

    override fun onEnable() {
        if (!File(dataFolder, "hordes.yml").exists()) {
            BloodmoonPlugin.instance.saveResource("hordes.yml", false)
        }

        hordeConfig = YamlConfiguration.loadConfiguration(dataFolder.resolve("hordes.yml"))

        HordeRegistry.refreshHordeConfigs()

//        BloodmoonPlugin.instance.scheduler.runTimer(5, 5) {
//            for (activeWorld in BloodmoonRegistry.getActiveWorlds()) {
//                if (HordeRegistry.getHordeConfig(activeWorld.world) != null) {
//                }
//            }
//        }
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
