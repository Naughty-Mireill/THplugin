package org.noteusoft.mireiyu.thplugin.race.skill;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;


public class THSkillSIR implements Listener  {
	//移動スキル系

	//召喚スキル系
	public static void seirei_summon(Player pl, final Plugin plugin,final String pluginpre, final PlayerInteractEvent event)
	{
	    double type = Math.random();
	    if (type <= 8.0D)
	    {
	      int n = 0;
	      while (n < 3)
	      {
	        Entity snowman = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.SNOWMAN);
	        MetadataValue syugoreisnow = new FixedMetadataValue(plugin, Boolean.valueOf(true));
	        snowman.setMetadata("syugoreisnow", syugoreisnow);
	        MetadataValue syugoreitarget = new FixedMetadataValue(plugin, pl.getName());
	        snowman.setMetadata("syugoreitarget", syugoreitarget);
	        n++;
	      }
	      pl.getWorld().playSound(pl.getLocation(), Sound.IRONGOLEM_HIT, 2.0F, 1.0F);
	      pl.sendMessage(pluginpre + ChatColor.AQUA + "雪の霊を召喚した！");
	    }
	    else
	    {
	      int n = 0;
	      while (n < 1)
	      {
	        Entity snowman = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.IRON_GOLEM);
	        MetadataValue syugoreiiron = new FixedMetadataValue(plugin, Boolean.valueOf(true));
	        snowman.setMetadata("syugoreiiron", syugoreiiron);
	        MetadataValue syugoreitarget = new FixedMetadataValue(plugin, pl.getName());
	        snowman.setMetadata("syugoreitarget", syugoreitarget);
	        n++;
	      }
	      pl.getWorld().playSound(pl.getLocation(), Sound.IRONGOLEM_HIT, 2.0F, -1.0F);
	      pl.sendMessage(pluginpre + ChatColor.GOLD + "岩の霊を召喚した");
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
        }, 400L);
	}
	//攻撃スキル系
	public static void hannrei_hannrei_ball(Player pl, final Plugin plugin, final PlayerInteractEvent event)
	{
	    pl.getWorld().playSound(pl.getLocation(), Sound.DIG_SAND, 2.0F, 2.0F);
	    pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 1);
	    Location location = pl.getEyeLocation();
	    float pitch = location.getPitch() / 180.0F * 3.1415927F;
	    float yaw = location.getYaw() / 180.0F * 3.1415927F;
	    double motX = -Math.sin(yaw) * Math.cos(pitch);
	    double motZ = Math.cos(yaw) * Math.cos(pitch);
	    double motY = -Math.sin(pitch);
	    Vector velocity = new Vector(motX, motY, motZ).multiply(2.0D);
	    @SuppressWarnings("deprecation")
		Snowball snowball = pl.throwSnowball();
	    MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
	    snowball.setMetadata("hannrei-curseball", shooter);
	    snowball.setVelocity(velocity);
	}
	public static void sourei_music(Player pl, final Plugin plugin,final String pluginpre, final PlayerInteractEvent event)
	{
	    List<Entity> enemys = pl.getNearbyEntities(16.0D, 16.0D, 16.0D);
	    double rand = Math.random();
	    if (rand >= 0.8D)
	    {
	      pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_BASS_GUITAR, 10.0F, -2.0F);
	      for (Entity enemy : enemys) {
	        if ((enemy instanceof Player))
	        {
	          ((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 5));
	          ((Player)enemy).sendMessage(pluginpre + ChatColor.DARK_BLUE + "鬱だ・・");
	        }
	      }
	    }
	    else if (rand >= 0.4D)
	    {
	      pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_SNARE_DRUM, 10.0F, 1.0F);
	      for (Entity enemy : enemys) {
	        if ((enemy instanceof Player))
	        {
	          ((Player)enemy).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 2));
	          ((Player)enemy).sendMessage(pluginpre + ChatColor.DARK_RED + "躁だ☆");
	        }
	      }
	    }
	    else
	    {
	      pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_PIANO, 10.0F, 0.0F);
	      for (Entity enemy : enemys) {
	        if ((enemy instanceof Player))
	        {
	          ((Player)enemy).sendMessage(pluginpre + ChatColor.GREEN + "騒音だ！！");
	          if (((Player)enemy).getHealth() - 15.0D >= 0.0D) {
	            ((Player)enemy).setHealth(((Player)enemy).getHealth() - 15.0D);
	          } else {
	            ((Player)enemy).setHealth(0.0D);
	          }
	        }
	      }
	    }
	}
	public static void seirei_lightball(Player pl, final Plugin plugin, final PlayerInteractEvent event)
	{
	    pl.getWorld().playSound(pl.getLocation(), Sound.DIG_SNOW, 2.0F, 2.0F);
	    pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 1);
	    Location location = pl.getEyeLocation();
	    int n = 0;
	    while (n < 8)
	    {
	      float pitch = location.getPitch() / 180.0F * 3.1415927F;
	      float yaw = location.getYaw() / 180.0F * 3.1415927F + n * 45;
	      double motX = -Math.sin(yaw) * Math.cos(pitch);
	      double motZ = Math.cos(yaw) * Math.cos(pitch);
	      double motY = -Math.sin(pitch);
	      Vector velocity = new Vector(motX, motY, motZ).multiply(2.0D);
	      @SuppressWarnings("deprecation")
		  Snowball snowball = pl.throwSnowball();
	      MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString());
	      snowball.setMetadata("seirei-lightball", shooter);
	      snowball.setShooter(pl);
	      snowball.setVelocity(velocity);
	      n++;
	    }
	}
	public static void onnryou_never_vanish(Player pl, final Plugin plugin, String pluginpre, final EntityDamageByEntityEvent event, int boost) 
	{
		double rand = Math.random();
		if (boost > 10) rand += 0.2;
	    if (rand > 0.6D)
	    {
	      pl.setHealth(50.0D);
	      pl.sendMessage(pluginpre + ChatColor.DARK_RED + "消えたくない・・・っ");
	      if ((event.getDamager() instanceof Player))
	      {
	        Player dpl = (Player)event.getDamager();
	        dpl.sendMessage(pluginpre + ChatColor.DARK_RED + "消えたくない・・・っ");
	        dpl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 4));
	      }
	      pl.getWorld().playSound(pl.getLocation(), Sound.GHAST_CHARGE, 2.0F, 2.0F);
	      event.setCancelled(true);
	    }
	}
	public static void seirei_mighty_guard(Player pl, final Plugin plugin, final EntityDamageByEntityEvent event, int boost) 
	{
		if (boost >= 15)
		{
		    if (pl.isSneaking() && event.getDamage() > 5.0D)
		    {
		      event.setDamage(event.getDamage() - 5.0D);
		    }
		    else if (event.getDamage() > 2.0D)
		    {
			  event.setDamage(event.getDamage() - 2.0D);
		    }
		}
	    else if (boost > 0 && boost < 15)
	    {
		    if (pl.isSneaking() && event.getDamage() > 3.0D)
		    {
		      event.setDamage(event.getDamage() - 3.0D);
		    }
	    }
	    else
	    {
		    if (pl.isSneaking() && event.getDamage() > 1.0D)
		    {
		      event.setDamage(event.getDamage() - 1.0D);
		    }
	    }
	}
	
	public static void seirei_mighty_guard(Player pl, final Plugin plugin, final EntityDamageByBlockEvent event, int boost) 
	{
		if (boost >= 15)
		{
		    if (pl.isSneaking() && event.getDamage() > 5.0D)
		    {
		      event.setDamage(event.getDamage() - 5.0D);
		    }
		    else if (event.getDamage() > 2.0D)
		    {
			  event.setDamage(event.getDamage() - 2.0D);
		    }
		}
	    else if (boost > 0 && boost < 15)
	    {
		    if (pl.isSneaking() && event.getDamage() > 3.0D)
		    {
		      event.setDamage(event.getDamage() - 3.0D);
		    }
	    }
	    else
	    {
		    if (pl.isSneaking() && event.getDamage() > 1.0D)
		    {
		      event.setDamage(event.getDamage() - 1.0D);
		    }
	    }
	}
}
