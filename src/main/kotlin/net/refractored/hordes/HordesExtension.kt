package net.refractored.hordes

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.extensions.Extension
import net.refractored.bloodmoonreloaded.BloodmoonPlugin
import net.refractored.hordes.commands.SpawnHordeCommand
import net.refractored.hordes.hordes.HordeRegistry
import net.refractored.hordes.listeners.OnBloodmoonStart
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class HordesExtension(
    plugin: EcoPlugin,
) : Extension(plugin) {
    init {
        instance = this
    }

    lateinit var hordeConfig: YamlConfiguration
        private set

    override fun onEnable() {
    }

    override fun onAfterLoad() {
        if (!File(dataFolder, "hordes.yml").exists()) {
            val destination = Path.of(dataFolder.path + "/extensions/hordes.yml")

            this.javaClass.getResourceAsStream("/hordes.yml")?.use { inputStream ->
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING)
            } ?: throw IllegalArgumentException("Resource not found.")
        }

        hordeConfig = YamlConfiguration.loadConfiguration(dataFolder.resolve("/extensions/hordes.yml"))

        HordeRegistry.refreshHordeConfigs()

        plugin.eventManager.registerListener(OnBloodmoonStart())

        if (plugin.langYml.getStringOrNull("messages.HordeSpawnedOnPlayer") == null) {
            plugin.langYml.set("messages.HordeSpawnedOnPlayer", "<red><bold>A horde has descended upon %player%!")
            plugin.langYml.save()
            plugin.reload()
        }

        if (plugin.langYml.getBoolOrNull("messages.HordeSpawnedOnPlayerPrefixed") == null) {
            plugin.langYml.set("messages.HordeSpawnedOnPlayerPrefixed", false)
            plugin.langYml.save()
            plugin.reload()
        }

        if (plugin.langYml.getStringOrNull("messages.NoHordeConfigFoundNoHordeConfigFound") == null) {
            plugin.langYml.set("messages.NoHordeConfigFound", "<red>No horde config found for this world!")
            plugin.langYml.save()
            plugin.reload()
        }

        if (plugin.langYml.getStringOrNull("messages.SpawnedHordeOnPlayer") == null) {
            plugin.langYml.set("messages.HordeSpawnedOnPlayer", "<white>Spawned horde on %player%.")
            plugin.langYml.save()
            plugin.reload()
        }

        BloodmoonPlugin.instance.handler.register(SpawnHordeCommand())
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
