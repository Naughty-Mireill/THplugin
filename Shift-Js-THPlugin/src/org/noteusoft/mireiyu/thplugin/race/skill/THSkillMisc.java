package org.noteusoft.mireiyu.thplugin.race.skill;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
public class THSkillMisc implements Listener {
	  @EventHandler
	  public void onSkillDamage(final Plugin plugin,String pluginpre, EntityDamageEvent e){
		  if(e.getEntity() instanceof Player){
			  Player p = (Player) e.getEntity();
		        if( ((EntityDamageByEntityEvent) e).getDamager() instanceof Snowball ){
		        	Entity damagerentity = ((EntityDamageByEntityEvent) e).getDamager();
		            Snowball snowball = (Snowball)damagerentity;
		            if (snowball.hasMetadata("seirei-lightball")) {
		            	//¸—ì’e
		            	e.setDamage(6.0D);
		            }else if (snowball.hasMetadata("hannrei-curseball")) {
		                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 3));
		                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 150, 3));
		                if (((e.getEntity() instanceof Player)) && (Bukkit.getPlayer(UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString())) != null))
		                {
		                  if (plugin.getConfig().getInt("user." + p.getUniqueId() + ".spilit") >= 30)
		                  {
		                    plugin.getConfig().set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(plugin.getConfig().getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") + 30.0D));
		                    plugin.getConfig().set("user." + p.getUniqueId() + ".spilit", Double.valueOf(plugin.getConfig().getInt("user." + p.getUniqueId() + ".spilit") - 30.0D));
		                    if (plugin.getConfig().getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") > 100) {
		                      plugin.getConfig().set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(100.0D));
		                    }
		                  }
		                  else
		                  {
		                    plugin.getConfig().set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Integer.valueOf(plugin.getConfig().getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") + plugin.getConfig().getInt("user." + p.getUniqueId() + ".spilit")));
		                    plugin.getConfig().set("user." + p.getUniqueId() + ".spilit", Integer.valueOf(0));
		                    if (plugin.getConfig().getInt("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit") > 100) {
		                      plugin.getConfig().set("user." + UUID.fromString(((MetadataValue)((EntityDamageByEntityEvent) e).getDamager().getMetadata("hannrei-curseball").get(0)).asString()) + ".spilit", Double.valueOf(100.0D));
		                    }
		                  }
		                  p.sendMessage(pluginpre + ChatColor.DARK_PURPLE + "—ì—Í‚ð‹z‚¢Žæ‚ç‚ê‚½III");
		                }
		            }
		        }
		  }
	  }

	  @EventHandler
	  public void onEntityExplode(final Plugin plugin, EntityExplodeEvent e){
		  Entity ent = e.getEntity();
		  if(ent.getType().equals(EntityType.PRIMED_TNT)){
			  if(ent.hasMetadata("kappa-tnt")){
				  final World world = e.getLocation().getWorld();
				  final Location loc = e.getLocation();
				  e.setCancelled(true);
				  world.createExplosion(loc, 0.0F);
				  final Player shooter = plugin.getServer().getPlayer(ent.getMetadata("kappa-tnt").get(0).asString());
				  for (int shot = 200; shot > 0; shot--){
					  int x = new Random().nextInt(90) - 45;
					  int y = new Random().nextInt(70) - 45;
					  int z = new Random().nextInt(90) - 45;
					  Snowball snowball = world.spawn(loc, Snowball.class);
	                  snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(plugin, true));
	                  snowball.setShooter(shooter);
	                  Vector vectory = new Vector(x, y, z);
	                  snowball.setVelocity(vectory);
				  }
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						public void run() {
							  world.createExplosion(loc, 0.0F);
							  for (int shot = 200; shot > 0; shot--){
								  int x = new Random().nextInt(90) - 45;
								  int y = new Random().nextInt(70) - 45;
								  int z = new Random().nextInt(90) - 45;
								  Snowball snowball = world.spawn(loc, Snowball.class);
				                  snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(plugin, true));
				                  snowball.setShooter(shooter);
				                  Vector vectory = new Vector(x, y, z);
				                  snowball.setVelocity(vectory);
							  }
						}
					}, 5);
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						public void run() {
							  world.createExplosion(loc, 0.0F);
							  for (int shot = 200; shot > 0; shot--){
								  int x = new Random().nextInt(90) - 45;
								  int y = new Random().nextInt(70) - 45;
								  int z = new Random().nextInt(90) - 45;
								  Snowball snowball = world.spawn(loc, Snowball.class);
				                  snowball.setMetadata("kappa-yukidama", new FixedMetadataValue(plugin, true));
				                  snowball.setShooter(shooter);
				                  Vector vectory = new Vector(x, y, z);
				                  snowball.setVelocity(vectory);
							  }
						}
					}, 10);
			  }
		  }
	  }
	  @EventHandler
	  public void SnowballDamage(final Plugin plugin, EntityDamageByEntityEvent e){
		  if(e.getEntity() instanceof Player){
			  if(e.getDamager().getType() == EntityType.PRIMED_TNT){
				  if(e.getDamager().hasMetadata("kappa-tnt")){
					  e.setCancelled(true);
				  }
			  }else if(e.getDamager().getType() == EntityType.SNOWBALL){
				  Snowball sb = (Snowball) e.getDamager();
				  if((Player)e.getEntity() == sb.getShooter()){
					  e.setCancelled(true);
				  }else {
					  if(sb.hasMetadata("kappa-yukidama")){
						  e.setDamage(20);
					  }
				  }
			  }
		  }
	  }

	  public static void NoDamageTick(final Plugin plugin, final Player p,int wait, final int tick){
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {
					p.setNoDamageTicks(tick);
				}
			}, wait);
	  }
}
