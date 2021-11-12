package io.github.arewena

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.FurnaceSmeltEvent
import org.bukkit.plugin.java.JavaPlugin

class OneChaneCraft : JavaPlugin(), Listener {
    private val banItem = config.getList("itemList")?.toMutableList()
    private val banSmelt = config.getList("smeltList")?.toMutableList()

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        logger.info(banItem.toString())
    }

    override fun onDisable() {
        config.options().copyDefaults(true)
        config.set("itemList", banItem)
        config.set("smeltList", banSmelt)
        saveConfig()
    }

    @EventHandler
    fun onCraft(e: CraftItemEvent) {
        if (e.currentItem!!.type != Material.ENDER_EYE && e.currentItem!!.type != Material.BLAZE_POWDER) {
            if(!e.isShiftClick) {
                if (banItem!!.contains(e.currentItem.toString())) {
                    e.isCancelled = true
                    logger.info(banItem.toString())
                }
                else {
                    banItem.add(e.currentItem.toString())
                    logger.info(banItem.toString())
                }
            }
            else {
                e.isCancelled = true
                logger.info(banItem.toString())
            }
        }
    }

    @EventHandler
    fun onSmelt(e: FurnaceSmeltEvent) {
        if (banSmelt!!.contains(e.result.toString())) {
            e.isCancelled = true
        }
        else {
            banSmelt.add(e.result.toString())
        }
    }


}