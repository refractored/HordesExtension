package net.refractored.hordes

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.extensions.Extension
import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.hordes.commands.SpawnHordeCommand
import net.refractored.hordes.hordes.HordeRegistry
import net.refractored.hordes.listeners.OnBloodmoonStart
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

        plugin.eventManager.registerListener(OnBloodmoonStart())

        if (plugin.langYml.getStringOrNull("messages.HordeSpawnedOnPlayer") == null) {
            plugin.langYml.set("messages.HordeSpawnedOnPlayer", "<red><bold>A horde has descended upon %player%!")
        }

        if (plugin.langYml.getBoolOrNull("messages.HordeSpawnedOnPlayerPrefixed") == null) {
            plugin.langYml.set("messages.HordeSpawnedOnPlayerPrefixed", false)
        }

        if (plugin.langYml.getStringOrNull("messages.SpawnedHordeOnPlayer") == null) {
            plugin.langYml.set("messages.HordeSpawnedOnPlayer", "<white>Spawned horde on %player%.")
        }
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
