package committee.nova.quickplant.common.util

import committee.nova.quickplant.common.config.CommonConfig
import net.minecraft.block.Block
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.util.ForgeDirection

import scala.util.Try

object Utilities {
  def getPlant(stack: ItemStack): IPlantable = {
    isPlant(stack) match {
      case 0 => null
      case 1 => stack.getItem.asInstanceOf[IPlantable]
      case 2 => Block.getBlockFromItem(stack.getItem).asInstanceOf[IPlantable]
    }
  }

  def isPlant(stack: ItemStack): Byte = {
    if (stack == null) return 0
    val item = stack.getItem
    if (item.isInstanceOf[IPlantable]) return 1
    if (Block.getBlockFromItem(item).isInstanceOf[IPlantable]) 2 else 0
  }

  def tryPlantThere(entity: EntityItem, isSeed: Boolean): Boolean = {
    val world = entity.worldObj
    val x = Math.floor(entity.posX).intValue()
    val y = Math.floor(entity.posY).intValue()
    val z = Math.floor(entity.posZ).intValue()
    val blockIn = world.getBlock(x, y, z)
    if (!blockIn.getMaterial.isReplaceable) return false
    val dirt = world.getBlock(x, y - 1, z)
    val item = entity.getEntityItem.getItem
    val plant = if (isSeed) item.asInstanceOf[IPlantable] else Block.getBlockFromItem(item).asInstanceOf[IPlantable]
    if (blockIn == plant.getPlant(world, x, y, z)) return false
    val place = Try(dirt.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, plant)).getOrElse(false)
    if (!place) return false
    val success = world.setBlock(x, y, z, plant.getPlant(world, x, y, z), item.getMetadata(entity.getEntityItem.getItemDamage), 2)
    if (success && CommonConfig.playSound) world.playSoundEffect(x, y, z, "dig.grass", .5F, 1F)
    success
  }
}
