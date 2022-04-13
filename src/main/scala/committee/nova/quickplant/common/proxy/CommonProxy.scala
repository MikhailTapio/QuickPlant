package committee.nova.quickplant.common.proxy

import committee.nova.quickplant.common.config.CommonConfig
import committee.nova.quickplant.common.event.QuickPlantEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent): Unit = new CommonConfig(event)

  def init(event: FMLInitializationEvent): Unit = MinecraftForge.EVENT_BUS.register(new QuickPlantEvent)
}
