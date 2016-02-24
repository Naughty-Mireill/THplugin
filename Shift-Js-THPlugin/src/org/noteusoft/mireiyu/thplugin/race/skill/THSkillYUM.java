package org.noteusoft.mireiyu.thplugin.race.skill;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
public class THSkillYUM  implements Listener {
	////アクティブスキル系
	///移動スキル系
	public static void tenngu_kamikaze(Player pl, final Plugin plugin, final String pluginpre,PlayerInteractEvent event, int boost) 
	{
		if (boost > 0 && boost < 15)
		{
		    pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
		    pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F); 
		    pl.setVelocity(pl.getLocation().getDirection().multiply(8.0D));
		    pl.setFallDistance(-30F);
		}
		else
		{
		    pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F);
		    pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, -1.0F);   
		    pl.setVelocity(pl.getLocation().getDirection().multiply(14.0D));
		    pl.setFallDistance(-10F);
		}
	}
	///攻撃スキル系
	public static void youma_golden_shockwave(Player pl, final Plugin plugin, String pluginpre, final PlayerToggleSneakEvent event)
	{
	    pl.sendMessage(pluginpre + ChatColor.YELLOW + "金の斧で全てを吹き飛ばす！！");
	    pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 0.0F);
	    pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1.0F, 0.0F);
	    pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_HUGE, 1, 1);
	    List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
	    for (Entity enemy : enemys) {
	      if ((enemy instanceof LivingEntity))
	      {
	        enemy.setVelocity(enemy.getVelocity().add(new Vector(new Double(enemy.getLocation().getX() - pl.getLocation().getX()).doubleValue(), 0.0D, new Double(enemy.getLocation().getZ() - pl.getLocation().getZ()).doubleValue())));
	        enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 1.0F);
	      }
	    }
	}
	public static void kappa_stone_tnt(Player pl, final Plugin plugin, String pluginpre, final PlayerToggleSneakEvent event)
	{
		pl.sendMessage(pluginpre + ChatColor.GRAY + "石の斧でTNTを投げた！！");
	    pl.getWorld().playSound(pl.getLocation(), Sound.FIRE_IGNITE, 1.0F, 0.0F);
	    Location location = pl.getEyeLocation();
	    float pitch = location.getPitch() / 180.0F * 3.1415927F;
	    float yaw = location.getYaw() / 180.0F * 3.1415927F;
	    double motX = -Math.sin(yaw) * Math.cos(pitch);
	    double motZ = Math.cos(yaw) * Math.cos(pitch);
	    double motY = -Math.sin(pitch);
	    Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
	    TNTPrimed tnt = (TNTPrimed)pl.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
	    MetadataValue shooter = new FixedMetadataValue(plugin, pl.getName());
	    tnt.setMetadata("kappa-tnt", shooter);
	    tnt.setVelocity(velocity);
	    tnt.setIsIncendiary(true);
	    tnt.setFireTicks(20);
	    tnt.setFuseTicks(20);
	    THSkillMisc.NoDamageTick(plugin, pl,30,15);
	}
	public static void youma_wooden_upper(Player pl, final Plugin plugin, String pluginpre, final PlayerToggleSneakEvent event)
	{
		pl.sendMessage(pluginpre + ChatColor.GOLD + "木の斧で地面を叩き上げた！！");
		pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_LARGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
		  if ((enemy instanceof LivingEntity))
		  {
		    enemy.setVelocity(enemy.getVelocity().add(new Vector(0.0D, 1.5D, 0.0D)));
		    enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 0.0F);
		  }
		}
	}
	////パッシブスキル系
	///リスポーン
	public static void youma_respawnhealth(Player pl, final Plugin plugin,String pluginpre, final PlayerToggleSneakEvent event)
	{
		pl.setMaxHealth(120.0D);
	}
	public static void youma_respawnhealth(Player pl, Plugin plugin, PlayerRespawnEvent event) 
	{
		pl.setMaxHealth(120.0D);
	}
	public static void kennyou_respawnhealth(Player pl, Plugin plugin, PlayerRespawnEvent event) 
	{
		pl.setMaxHealth(150.0D);
	}
	///耐性
	public static void kappa_anti_drawn(Player pl, final Plugin plugin, String pluginpre, final EntityDamageByBlockEvent event)
	{
		if (event.getCause() == DamageCause.DROWNING) event.setCancelled(true);
	}
}
