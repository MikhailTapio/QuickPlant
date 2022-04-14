package committee.nova.quickplant

import committee.nova.quickplant.QuickPlant.proxy
import committee.nova.quickplant.common.proxy.CommonProxy
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}

object QuickPlant {
  final val MODID = "quickplant"
  @SidedProxy(serverSide = "committee.nova.quickplant.common.proxy.CommonProxy")
  val proxy: CommonProxy = new CommonProxy
  @Mod.Instance(QuickPlant.MODID)
  var instance: QuickPlant = _
}

@Mod(modid = QuickPlant.MODID, useMetadata = true, acceptedMinecraftVersions = "[1.11,)")
class QuickPlant {
  QuickPlant.instance = this

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = proxy.preInit(event)

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = proxy.init(event)
}