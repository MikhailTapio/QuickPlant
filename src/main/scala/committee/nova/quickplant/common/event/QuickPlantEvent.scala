package committee.nova.quickplant.common.event

import committee.nova.quickplant.common.config.CommonConfig
import committee.nova.quickplant.common.util.Utilities._
import net.minecraft.entity.item.EntityItem
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.item.ItemExpireEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class QuickPlantEvent {
  @SubscribeEvent
  def onItemDrop(event: EntityJoinWorldEvent): Unit = {
    if (event.isCanceled) return
    val entity = event.getEntity
    if (!entity.isInstanceOf[EntityItem]) return
    val itemEntity = entity.asInstanceOf[EntityItem]
    if (isPlant(itemEntity.getEntityItem) == 0) return
    itemEntity.lifespan = CommonConfig.refreshInterval
  }

  @SubscribeEvent
  def onItemExpire(event: ItemExpireEvent): Unit = {
    if (event.isCanceled) return
    val itemEntity = event.getEntityItem
    val stack = itemEntity.getEntityItem
    val plantType = isPlant(stack)
    if (plantType == 0) return
    if (itemEntity.getAge >= CommonConfig.expireTime) return
    if (!tryPlantThere(itemEntity, isSeed = plantType == 1)) {
      event.setExtraLife(CommonConfig.refreshInterval)
      event.setCanceled(true)
      return
    }
    val newStack = stack.copy()
    newStack.setCount(stack.getCount - 1)
    if (newStack.getCount <= 0) return
    itemEntity.setEntityItemStack(newStack)
    event.setExtraLife(CommonConfig.refreshInterval)
    event.setCanceled(true)
  }
}
