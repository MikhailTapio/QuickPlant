package committee.nova.quickplant.common.config

import committee.nova.quickplant.common.config.CommonConfig._
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger

object CommonConfig {
  var refreshInterval: Int = _
  var expireTime: Int = _
  var playSound: Boolean = _
  private var config: Configuration = _
  private var logger: Logger = _
}

class CommonConfig(event: FMLPreInitializationEvent) {
  logger = event.getModLog
  config = new Configuration(event.getSuggestedConfigurationFile)
  config.load()
  refreshInterval = config.getInt("refresh_interval", Configuration.CATEGORY_GENERAL, 100, 20, 6000, "Refresh interval for quickplant. A smaller value will make it faster for a plant itemEntity to convert into a plant block. Default is 100 ticks(5 sec)")
  expireTime = config.getInt("expireTime", Configuration.CATEGORY_GENERAL, 6000, 20, 36000, "Expire time of a plant itemEntity. If smaller than the refreshInterval, the itemEntity will disappear when it refreshes for the first time.")
  playSound = config.getBoolean("soundEffect", Configuration.CATEGORY_GENERAL, true, "Should quickplant be with a sound effect?")
  config.save()
}
