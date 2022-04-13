package committee.nova.quickplant.common.event

import committee.nova.quickplant.common.config.CommonConfig
import committee.nova.quickplant.common.util.Utilities._
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.item.{ItemExpireEvent, ItemTossEvent}

class QuickPlantEvent {
  @SubscribeEvent
  def onItemToss(event: ItemTossEvent): Unit = {
    if (event.isCanceled) return
    val itemEntity = event.entityItem
    if (isPlant(itemEntity.getEntityItem) == 0) return
    itemEntity.lifespan = CommonConfig.refreshInterval
  }

  @SubscribeEvent
  def onItemExpire(event: ItemExpireEvent): Unit = {
    if (event.isCanceled) return
    val itemEntity = event.entityItem
    val plantType = isPlant(itemEntity.getEntityItem)
    if (plantType == 0) return
    if (itemEntity.age >= CommonConfig.expireTime) return
    if (tryPlantThere(itemEntity, isSeed = plantType == 1)) return
    event.extraLife = CommonConfig.refreshInterval
    event.setCanceled(true)
  }
}
