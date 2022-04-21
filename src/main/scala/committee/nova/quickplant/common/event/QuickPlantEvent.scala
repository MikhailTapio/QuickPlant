package committee.nova.quickplant.common.event

import committee.nova.quickplant.common.config.CommonConfig
import committee.nova.quickplant.common.util.Utilities._
import net.minecraftforge.event.entity.item.{ItemExpireEvent, ItemTossEvent}
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

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
    val stack = itemEntity.getEntityItem
    val plantType = isPlant(stack)
    if (plantType == 0) return
    if (itemEntity.ticksExisted >= CommonConfig.expireTime) return
    if (!tryPlantThere(itemEntity, isSeed = plantType == 1)) {
      event.extraLife = CommonConfig.refreshInterval
      event.setCanceled(true)
      return
    }
    val newStack = stack.copy()
    newStack.stackSize -= 1
    if (newStack.stackSize <= 0) return
    itemEntity.setEntityItemStack(newStack)
    event.extraLife = CommonConfig.refreshInterval
    event.setCanceled(true)
  }
}
