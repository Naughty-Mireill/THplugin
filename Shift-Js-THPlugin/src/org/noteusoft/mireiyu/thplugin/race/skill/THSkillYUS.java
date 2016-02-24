package org.noteusoft.mireiyu.thplugin.race.skill;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class THSkillYUS implements Listener  {
	///アクティヴスキル系
	//移動スキル系
	public static void yousei_feather(Player pl, final Plugin plugin,PlayerToggleSneakEvent event) 
	{
	    pl.setVelocity(pl.getLocation().getDirection().multiply(1.1D));
	    pl.getWorld().playSound(pl.getLocation(), Sound.SHOOT_ARROW, 1.0F, 1.0F);
	    pl.getWorld().playEffect(pl.getLocation(), Effect.TILE_DUST, 133, 1);
	}
	//攻撃スキル系
	public static void yousei_illusion(Player pl, final Plugin plugin,final String pluginpre,final PlayerInteractEvent event) 
	{
		pl.sendMessage(pluginpre + ChatColor.LIGHT_PURPLE + "金のシャベルの輝きがあたりを惑わす！！");
	    pl.getWorld().playSound(pl.getLocation(), Sound.CAT_PURR, 3.0F, -1.0F);
	    pl.getWorld().playEffect(pl.getLocation(), Effect.HAPPY_VILLAGER, 1, 1);
	    List<Entity> enemys = pl.getNearbyEntities(14.0D, 14.0D, 14.0D);
	    for (Entity enemy : enemys) 
	    {
	      if ((enemy instanceof Player)) 
	      {
	        ((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 3));
	        ((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 3));
	      }
	    }
	    MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
	    pl.setMetadata("using-magic", usingmagic);
	    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
	    {
	      public void run()
	      {
	        Player pl = event.getPlayer();
	        MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
	        pl.setMetadata("using-magic", usingmagic);
	        pl.sendMessage(pluginpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
	      }
	    }, 60L);
	}
	public static void kibito_venom(Player pl, final Plugin plugin,final String pluginpre,final PlayerInteractEvent event) 
	{
		pl.sendMessage(pluginpre + ChatColor.DARK_GREEN + "樹人は毒をばらまいた！");
	    pl.getWorld().playSound(pl.getLocation(), Sound.PIG_DEATH, 3.0F, -1.0F);
	    pl.getWorld().playEffect(pl.getLocation(), Effect.VOID_FOG, 1, 1);
	    List<Entity> enemys = pl.getNearbyEntities(14.0D, 14.0D, 14.0D);
	    for (Entity enemy : enemys) {
	      if ((enemy instanceof LivingEntity)) {
	        ((LivingEntity)enemy).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2));
	      }
	    }
	    MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
	    pl.setMetadata("using-magic", usingmagic);
	    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
	    {
	      public void run()
	      {
	        Player pl = event.getPlayer();
	        MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
	        pl.setMetadata("using-magic", usingmagic);
	        pl.sendMessage(pluginpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
	      }
	    }, 100L);
	}
	///パッシヴスキル系
	public static void yousei_glaze(Player pl, final Plugin plugin,EntityDamageByEntityEvent event) 
	{
	    double ran = Math.random();
	    if (ran >= 0.9D)
	    {
	      pl.getWorld().playSound(pl.getLocation(), Sound.CAT_HISS, 1.0F, 2.0F);
	      pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
	      event.setCancelled(true);
	    }
    }
	public static void kobito_glaze(Player pl, final Plugin plugin,EntityDamageByEntityEvent event) 
	{
		double ran = Math.random();
        if (ran >= 0.7D)
        {
          pl.getWorld().playSound(pl.getLocation(), Sound.CAT_HISS, 1.0F, 2.0F);
	      pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 2);
          event.setCancelled(true);
        }
        else if ((event.getDamage() > 1.0D) && (plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 20))
        {
          event.setDamage(event.getDamage() + 2.0D);
        }
        else if (event.getDamage() > 1.0D)
        {
          event.setDamage(event.getDamage() + 4.0D);
        }
    }
	public static void yousei_fall_protection(Player pl, final Plugin plugin,EntityDamageByBlockEvent event) 
	{
		if (event.getCause() == DamageCause.FALL) event.setDamage(event.getDamage() / 2.0D);
	}
	public static void satori_satori(Player pl, final Plugin plugin,final String pluginpre,EntityDamageByEntityEvent event) 
	{
		if(event.getDamage() >= pl.getHealth() && event.isCancelled() == false) 
		{
			plugin.getConfig().set(plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"), Double.valueOf(plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 50.0D));
		    pl.sendMessage(event.getDamager().getName() + ":体力:" + ((Player)event.getDamager()).getHealth());
		    pl.sendMessage(event.getDamager().getName() + ":座標:" + event.getDamager().getLocation().getBlockX() + "," + event.getDamager().getLocation().getBlockY() + "," + event.getDamager().getLocation().getBlockZ());
		    pl.sendMessage(ChatColor.DARK_PURPLE + "覚りました・・・覚えてなさい・・・");
		    String satorin0 = event.getDamager().getName();
		    MetadataValue satorin00 = new FixedMetadataValue(plugin, satorin0);
		    pl.setMetadata("satorin0", satorin00);
		}
	}
}
