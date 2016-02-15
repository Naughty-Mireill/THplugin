package org.noteusoft.mireiyu.thplugin.race;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import org.noteusoft.mireiyu.thplugin.THPlugin;
import org.noteusoft.mireiyu.thplugin.race.skill.THSkillGlobal;
import org.noteusoft.mireiyu.thplugin.race.skill.THSkillNNG;
import org.noteusoft.mireiyu.thplugin.race.skill.THSkillYUM;
import org.noteusoft.mireiyu.thplugin.race.skill.THSkillYUZ;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;

public class THRaces implements Listener 
{
    static Plugin plugin = THPlugin.plugin;
    static String pluginpre = THPlugin.thrpre;
    static FileConfiguration conf = THPlugin.conf;
    static File file = THPlugin.configfile;
    ////アクション
    ///非クリク系
    public void chat(final AsyncPlayerChatEvent event) {
        //前置詞に種族名を加える
        Player pl = event.getPlayer();
        THSkillGlobal.global_chat(pl, plugin, event);
    }

    public void quit(final PlayerQuitEvent event) {
        //メタ削除処
        Player pl = event.getPlayer();
        THSkillGlobal.global_quit(pl, plugin, event);
    }

    public void join(final PlayerJoinEvent event) {
        //初期ータ生
        Player pl = event.getPlayer();
        THSkillGlobal.global_join(pl, plugin, event);
    }

    public void respawn(final PlayerRespawnEvent event) {
        //リスポンをトリガーとして大体力調整
        Player pl = event.getPlayer();
        String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
        if (race.equalsIgnoreCase("youma") || race.equalsIgnoreCase("kappa") || race.equalsIgnoreCase("tenngu"))
        {
        	THSkillYUM.youma_respawnhealth(pl, plugin, event);
        }
        else if(race.equalsIgnoreCase("kennyou"))
        {
        	THSkillYUM.kennyou_respawnhealth(pl, plugin, event);
        }
        else
        {
        	THSkillGlobal.global_respawnhealth(pl, plugin, event);
        }
    }

    public void move(final PlayerMoveEvent event) {

        Player pl = event.getPlayer();
        String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
        int mana = 0;
        //人魚高水泳書き込み有）（ブースター処有
        mana = 1;
        if (race.equalsIgnoreCase("ninngen") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") > mana) {
            int boost = 0;
            if (pl.getMetadata("spilituse").get(0).asInt() > 0) boost = 1;
			THSkillYUZ.ninngyo_swimming(pl, plugin, event,boost);
			if (boost == 1)
			{
	            conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
	            try {
	    			conf.save(file);
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
			}
        }
        //天狗神風書き込み有）（前置詞有（ブースター処有
        mana = 40;
        if (race.equalsIgnoreCase("tenngu") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") > mana) {
            int boost = 0;
            if (pl.getMetadata("spilituse").get(0).asInt() > 0)
            {
            	boost = pl.getMetadata("spilituse").get(0).asInt();
            	THSkillYUM.tenngu_kamikaze(pl, plugin,pluginpre, event , boost);
	            conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
	            try {
	    			conf.save(file);
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
            }
        }
    }
    
    @SuppressWarnings("deprecation")
    public void togglesneak(final PlayerToggleSneakEvent event) {
        Player pl = event.getPlayer();
        //仙人の壁抜
        if (conf.getString("user." + pl.getUniqueId() + ".race").toString().contains("sennnin")) {
            if ((!pl.isOnGround()) && (pl.isSneaking()) && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= 20.0D) {
                THSkillNNG.sennnin_passthough(pl, plugin,event);
            }
        }
    }
    
    ///クリク系
    public void interactentity(final PlayerInteractEntityEvent event) {
        //非人間村人規制前置詞有
        String pluginpre = THPlugin.thrpre;
        Player pl = event.getPlayer();
        String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
        if (race.equalsIgnoreCase("ninngen") == false && race.equalsIgnoreCase("mazyo") == false && race.equalsIgnoreCase("houraizin") == false && race.equalsIgnoreCase("gennzinnsin") == false && race.equalsIgnoreCase("sibito") == false && race.equalsIgnoreCase("sennninn") == false) {
            THSkillGlobal.global_no_ninngen(pl, plugin, pluginpre, event);
        }
    }

    public void interact(final PlayerInteractEvent event) {
        final Player pl = event.getPlayer();
        Material handitem = pl.getItemInHand().getType();
        String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
    	int mana = 0;
        ///魔女魔法シリーズ最初は人間も
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            //人間魔女の回復魔法（書き込み有）（前置詞有
            if (race.equalsIgnoreCase("mazyo")||race.equalsIgnoreCase("ninngen") ) {
            	mana = 25;
            	if(magic_iscastable(pl , mana) && handitem == Material.STICK)
            	{
	                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
	                pl.setMetadata("casting", casting);
	                conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
	                try {
	        			conf.save(file);
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
	                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {                	
	                    public void run() {
	                        THSkillNNG.mazyo_heal(pl, plugin,pluginpre, event);
	                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
	                        pl.setMetadata("casting", casting);
	                    }
	                }, 20L);
                }
                //人間魔女の風魔法（書き込み有）（前置詞有
            	mana = 30;
            	if(magic_iscastable(pl , mana) && handitem == Material.WOOD_SWORD) {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
    	                conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
    	                try {
    	        			conf.save(file);
    	        		} catch (IOException e) {
    	        			e.printStackTrace();
    	        		}
    	        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    	                	
                            public void run() {
                                THSkillNNG.mazyo_wind(pl, plugin,pluginpre, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 10L);
            	}
            	mana = 45;
                if (magic_iscastable(pl , mana) && handitem == Material.STONE_SWORD) {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
    	                conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
    	                try {
    	        			conf.save(file);
    	        		} catch (IOException e) {
    	        			e.printStackTrace();
    	        		}
    	        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_dirt(pl, plugin,pluginpre, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 60L);
                }
            	mana = 30;
                if (magic_iscastable(pl , mana) && handitem == Material.IRON_SWORD) {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
    	                conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
    	                try {
    	        			conf.save(file);
    	        		} catch (IOException e) {
    	        			e.printStackTrace();
    	        		}
    	        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    	                	
                            public void run() {
                                THSkillNNG.mazyo_fire(pl, plugin,pluginpre, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 20L);
                }
            	mana = 60;
                if (magic_iscastable(pl , mana) && handitem == Material.DIAMOND_SWORD) {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
    	                conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
    	                try {
    	        			conf.save(file);
    	        		} catch (IOException e) {
    	        			e.printStackTrace();
    	        		}
    	        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    	                	
                            public void run() {
                                THSkillNNG.mazyo_water(pl, plugin,pluginpre, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 50L);
                            
                }
            	mana = 70;
                if (magic_iscastable(pl , mana) && handitem == Material.GOLD_SWORD) {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
    	                conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
    	                try {
    	        			conf.save(file);
    	        		} catch (IOException e) {
    	        			e.printStackTrace();
    	        		}
    	        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_thunder(pl, plugin,pluginpre, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 70L);
                }
            }
        }
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            //妖獣人魚獣人
            if (race.equalsIgnoreCase("youzuu") || race.equalsIgnoreCase("ninngyo") || race.equalsIgnoreCase("zyuuzin")) {
                mana = 15;
                if (magic_iscastable(pl , mana) && handitem == Material.FISHING_ROD) {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
    	                conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
    	                try {
    	        			conf.save(file);
    	        		} catch (IOException e) {
    	        			e.printStackTrace();
    	        		}
    	        		pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    	                	
                            public void run() {
                                THSkillYUZ.youzyuu_summon_wolf(pl, plugin,pluginpre, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 30L);
                }
            }
            //
            if (race.equalsIgnoreCase("siki") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0 ) {
                mana = 15;
                if (magic_iscastable(pl , mana ) && handitem == Material.FISHING_ROD) 
            		{
	                    MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
	                    pl.setMetadata("casting", casting);
	                    conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
	                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		                	
	                        public void run() {
	                            THSkillYUZ.siki_summon_ocerot(pl, plugin,pluginpre, event);
	                            MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
	                            pl.setMetadata("casting", casting);
		                        }
	                        }, 40L);
            		}
 
            }
            //妖獣全て
            if (race.equalsIgnoreCase("youzyuu") || race.equalsIgnoreCase("zyuuzin") || race.equalsIgnoreCase("ninngyo") || race.equalsIgnoreCase("siki")) {
                if (magic_iscastable(pl , mana ) && handitem == Material.BOW) 
            		{
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    	                	
                            public void run() {
                                THSkillYUZ.youzyu_gainenergy(pl, plugin,pluginpre, event);
                            }
                        }, 0L);
            		}
            }
        }
    }

    public void damagebyentity(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player pl = (Player) event.getDamager();
            String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
            //死人
            if (race.equalsIgnoreCase("sibito") && conf.getInt("user." + pl.getUniqueId() + ".split") > 20)
                THSkillNNG.sibito_deadattack(pl, plugin, event);
            if (race.equalsIgnoreCase("gennzinnsin") && conf.getInt("user." + pl.getUniqueId() + ".split") > 20)
                THSkillNNG.gennzinnsin_luckyattack(pl, plugin,pluginpre, event);
            //グローバル
            if (conf.getInt("user." + pl.getUniqueId() + ".split") <= 20)
                THSkillGlobal.global_no_mana_attack(pl, plugin,pluginpre, event);
        }
        if (event.getEntity() instanceof Player) {
            Player pl = (Player) event.getEntity();
            String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
            //蓬莱人
            if (race.equalsIgnoreCase("houraizin") && conf.getInt("user." + pl.getUniqueId() + ".split") > 20)
                THSkillNNG.houraizin_reverselife_Entity(pl, plugin,pluginpre, event);
            //グローバル
            if (conf.getInt("user." + pl.getUniqueId() + ".split") <= 20)
                THSkillGlobal.global_no_mana_damaged(pl, plugin,pluginpre, event);
        }
    }

    public void damagebyblock(final EntityDamageByBlockEvent event) {
        Player pl = (Player) event.getDamager();
        String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
        //蓬莱人
        if (race.equalsIgnoreCase("houraizin") && conf.getInt("user." + pl.getUniqueId() + ".split") > 20)
            THSkillNNG.houraizin_reverselife_block(pl, plugin,pluginpre, event);
    }
    
    ////冗長防止
    boolean magic_iscastable(Player pl, int mana)
    {
	        if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
	            pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です");
	            return false;
	        } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
	            pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です");
	            return false;
	        } else {
	    	    if (conf.getDouble("user." + pl.getUniqueId() + ".spilit") > mana) 
	    	    {
		        	return true;
	    	    }
	    	    else
	    	    {
		            pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "霊力が不足しています");
	    	    	return false;
	    	    }
	        }
    }
}
