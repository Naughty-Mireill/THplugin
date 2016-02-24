package org.noteusoft.mireiyu.thplugin.race.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.noteusoft.mireiyu.thplugin.THPlugin;
public class THCommand implements CommandExecutor{
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command mcd, String commandLabel, String[] args){
		  if ((commandLabel.equalsIgnoreCase("touhouraces")) && ((sender instanceof Player)) && (args.length == 0)){
			  Player pl = (Player)sender;
			  pl.sendMessage(THPlugin.plugin + "Version " + THPlugin.pdfFile.getVersion() + ". Made by:" + THPlugin.pdfFile.getAuthors().toString());
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && ((sender instanceof Player)) && (args.length == 0)){
			  Player pl = (Player)sender;
			  pl.sendMessage(THPlugin.plugin + "Version " + THPlugin.pdfFile.getVersion() + ". Made by:" + THPlugin.pdfFile.getAuthors().toString());
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("reload")) && (args.length == 1)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.reload")) || (pl.isOp())){
					  THPlugin.plugin.saveConfig();
					  THPlugin.plugin.reloadConfig();
					  sender.sendMessage(THPlugin.plugin + "§6 リロードしました.");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("help")) && (args.length == 1)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.help")) || (pl.hasPermission("thr.user"))){
					  THPlugin.plugin.reloadConfig();
					  sender.sendMessage(THPlugin.plugin + "§6可能なプラグイン一覧");
					  sender.sendMessage("§btouhouraces/thr : バージョン説明");
					  sender.sendMessage("thr reload : リロード");
					  sender.sendMessage("thr mana : 現在マナ確認");
					  sender.sendMessage("thr heal-mana [num] : マナをnum分回復する");
					  sender.sendMessage("thr racelist : オンラインでいるプレイヤーの種族の統計をとる");
					  sender.sendMessage("thr toggleskill : 行動系のスキルの発動をトグルする");
					  sender.sendMessage("thr setpoint [num] : 自分のポイント（使い方は任意）を設定する");
					  sender.sendMessage("thr addpoint [num] : 自分のポイント（使い方は任意）を追加する");
					  sender.sendMessage("thr setpoint [playername] [num] : playernameのポイント（使い方は任意）を設定する");
					  sender.sendMessage("thr addpoint [playername] [num] : playernameのポイント（使い方は任意）を追加する");
					  sender.sendMessage("thr steppoint [max] : ポイント（使い方は任意）をmaxを上限として1上昇する");
					  sender.sendMessage("thr steppoint [playername] [max] : playernameのポイント（使い方は任意）をmaxを上限として1上昇する");
					  sender.sendMessage("thr setrace  [内部種族名] : 自分の種族を種族名（内部名）に変更する");
					  sender.sendMessage("thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					  sender.sendMessage("thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					  sender.sendMessage("thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					  sender.sendMessage("thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					  sender.sendMessage("thr info : playernameの種族の情報を表示する");
					  sender.sendMessage("thr evolinfo [内部種族名] : 種族の情報を表示する");
					  sender.sendMessage("thr evollist : playernameの進化できる種族のリストを表示する");
					  sender.sendMessage("thr evolchange [内部種族名] : 種族の進化を試みる");
				  }else{
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("mana")) && (args.length == 1)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.checkmana")) || (pl.hasPermission("thr.user"))) {
					  pl.sendMessage(THPlugin.thrpre + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + THPlugin.plugin.getConfig().getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
				  } else {
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("heal-mana")) && (args.length == 2)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if (pl.hasPermission("thr.healmana")){
					  THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", Double.valueOf(THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") + Integer.parseInt(args[1])));
					  pl.sendMessage(THPlugin.thrpre + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + THPlugin.plugin.getConfig().getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
				  }else{
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("racelist")) && (args.length == 1)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.racelist")) || (pl.hasPermission("thr.user"))){
					  OfflinePlayer[] ppl = Bukkit.getOfflinePlayers();
					  pl.sendMessage(THPlugin.plugin + "§a オンライン中の種族リスト.");
					  int p = 0;
					  while (p < ppl.length){
						  if (ppl[p].isOnline()) {
							  pl.sendMessage(ChatColor.GREEN + "+" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(ppl[p].getUniqueId()).append(".name").toString()) + ":" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(ppl[p].getUniqueId()).append(".race").toString()) + "(" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(ppl[p].getUniqueId()).append(".point").toString()) + ")");
						  }
						  p++;
					  }
				  }else{
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("toggleskill")) && (args.length == 1)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.toggleskill")) || (pl.hasPermission("thr.user"))){
					  if (pl.hasMetadata("ignoreskill")){
						  pl.removeMetadata("ignoreskill", THPlugin.plugin);
						  pl.sendMessage(THPlugin.thrpre + ChatColor.DARK_AQUA + "行動スキルは再び発動します");
					  }else{
						  MetadataValue ignoreskill = new FixedMetadataValue(THPlugin.plugin, Boolean.valueOf(true));
						  pl.setMetadata("ignoreskill", ignoreskill);
						  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "行動スキルを封印しました");
					  }
				  }else {
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setrace")) && (args.length == 2)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.setrace")){
					  THPlugin.plugin.getConfig().set("user." + commander.getUniqueId() + ".race", args[1].toString());
					  THPlugin.plugin.saveConfig();
					  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたは種族が" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(commander.getUniqueId()).append(".race").toString()) + "になりました。");
				  }else{
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setrace")) && (args.length == 3)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.setrace")){
					  if (Bukkit.getPlayer(args[1]) != null){
						  Player pl = Bukkit.getPlayer(args[1]);
						  THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".race", args[2].toString());
						  THPlugin.plugin.saveConfig();
						  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + pl.getName() + "の種族を" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".race").toString()) + "にしました。");
						  pl.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたは種族が" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".race").toString()) + "になりました。");
					  }
				  }else {
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setpoint")) && (args.length == 2)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.setpoint")){
					  if (Bukkit.getPlayer(args[1]) != null){
						  int point = Integer.parseInt(args[1]);
						  THPlugin.plugin.getConfig().set("user." + commander.getUniqueId() + ".point", Integer.valueOf(point));
						  THPlugin.plugin.saveConfig();
						  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたはポイントが" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(commander.getUniqueId()).append(".point").toString()) + "になりました。");
					  }
				  }else {
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setpoint")) && (args.length == 3)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.setpoint")){
					  if (Bukkit.getPlayer(args[1]) != null){
						  Player pl = Bukkit.getPlayer(args[1]);
						  int point = Integer.parseInt(args[2]);
						  THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".point", Integer.valueOf(point));
						  THPlugin.plugin.saveConfig();
						  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + pl.getName() + "のポイントを" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".point").toString()) + "にしました。");
						  pl.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたはポイントが" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".point").toString()) + "になりました。");
					  }
				  }else {
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("addpoint")) && (args.length == 2)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.setpoint")){
					  int point = Integer.parseInt(args[1]);
					  THPlugin.plugin.getConfig().set("user." + commander.getUniqueId() + ".point", Integer.valueOf(THPlugin.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point") + point));
					  THPlugin.plugin.saveConfig();
					  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたはポイントが" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(commander.getUniqueId()).append(".point").toString()) + "になりました。");
				  }else {
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("addpoint")) && (args.length == 3)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.setpoint")){
					  if (Bukkit.getPlayer(args[1]) != null){
						  Player pl = Bukkit.getPlayer(args[1]);
						  int point = Integer.parseInt(args[2]);
						  THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".point", Integer.valueOf(THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point") + point));
						  THPlugin.plugin.saveConfig();
						  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + pl.getName() + "のポイントを" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".point").toString()) + "にしました。");
						  pl.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたはポイントが" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".point").toString()) + "になりました。");
					  }
				  }else {
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("steppoint")) && (args.length == 2)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.steppoint")){
					  if (THPlugin.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point") < Integer.parseInt(args[1])){
						  THPlugin.plugin.getConfig().set("user." + commander.getUniqueId() + ".point", Integer.valueOf(THPlugin.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point") + 1));
						  THPlugin.plugin.saveConfig();
						  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたはポイントが" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(commander.getUniqueId()).append(".point").toString()) + "になりました。");
					  }
				  }else {
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("steppoint")) && (args.length == 3)){
			  if ((sender instanceof Player)){
				  Player commander = ((Player)sender).getPlayer();
				  if (commander.hasPermission("thr.steppoint")){
					  if (Bukkit.getPlayer(args[1]) != null){
						  Player pl = Bukkit.getPlayer(args[1]);
						  if (THPlugin.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point") < Integer.parseInt(args[2])){
							  THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".point", Integer.valueOf(THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point") + 1));
							  THPlugin.plugin.saveConfig();
							  commander.sendMessage(THPlugin.thrpre + ChatColor.AQUA + pl.getName() + "のポイントを" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".point").toString()) + "にしました。");
							  pl.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "あなたはポイントが" + THPlugin.plugin.getConfig().getString(new StringBuilder("user.").append(pl.getUniqueId()).append(".point").toString()) + "になりました。");
						  }
					  }
				  }else {
					  commander.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません！");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("evollist")) && (args.length == 1)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.evol.user.list")) || (pl.hasPermission("thr.user"))){
					  pl.sendMessage(THPlugin.thrpre + ChatColor.AQUA + pl.getName() + "の進化できる先リスト");
					  List<String> evolraces = new ArrayList<String>();
					  for (String race : THPlugin.plugin.getConfig().getConfigurationSection("race").getKeys(false)) {
						  if (THPlugin.plugin.getConfig().getString("race." + race + ".racetype.root").contains(THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race"))) {
							  evolraces.add(race);
						  }
					  }
					  for (String evolrace : evolraces) {
						  pl.sendMessage(THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(evolrace).append(".display.real").toString()) + "：内部name＞" + evolrace);
					  }
				  }else	{
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません。");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("evolinfo")) && (args.length == 2)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.evol.user.info")) || (pl.hasPermission("thr.user"))){
					  boolean existrace = false;
					  String inforace = "";
					  for (String race : THPlugin.plugin.getConfig().getConfigurationSection("race").getKeys(false)) {
						  if (race.toLowerCase().contains(args[1].toLowerCase())){
							  existrace = true;
							  inforace = race;
							  break;
						  }
					  }
					  if (existrace){
						  pl.sendMessage(THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".display.real").toString()) + "：内部name＞" + inforace + "（" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".display.tag").toString()) + "）の情報");
						  pl.sendMessage("元種族：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".racetype.root").toString()));
						  pl.sendMessage("ランク：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".racetype.rank").toString()));
						  pl.sendMessage("進化に必要な進化の欠片：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".evol.evolpoint.shard").toString()));
						  pl.sendMessage("進化に必要な進化の宝石：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".evol.evolpoint.crystal").toString()));
						  pl.sendMessage("進化に必要な種族素材：" + THPlugin.plugin.getConfig().getInt(new StringBuilder("race.").append(inforace).append(".evol.raceitem.amount").toString()) + "個の" + Material.getMaterial(THPlugin.plugin.getConfig().getInt(new StringBuilder("race.").append(inforace).append(".evol.raceitem.typeid").toString())) + "(メタ" + THPlugin.plugin.getConfig().getInt(new StringBuilder("race.").append(inforace).append(".evol.raceitem.meta").toString()) + "）");
						  pl.sendMessage(THPlugin.plugin.getConfig().getString("race." + inforace + ".intro.story"));
						  pl.sendMessage(THPlugin.plugin.getConfig().getString("race." + inforace + ".intro.skills"));
					  }else	{
						  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "その種族内部nameは存在しません。");
					  }
				  }else	{
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません。");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("info")) && (args.length == 1)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.info")) || (pl.hasPermission("thr.user"))){
					  boolean existrace = false;
					  String inforace = "";
					  for (String race : THPlugin.plugin.getConfig().getConfigurationSection("race").getKeys(false)) {
						  if (race.toLowerCase().contains(THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toLowerCase())){
							  existrace = true;
							  inforace = race;
							  break;
						  }
					  }
					  if (existrace){
						  pl.sendMessage(THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".display.real").toString()) + "：内部name＞" + inforace + "（" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".display.tag").toString()) + "）の情報");
						  pl.sendMessage("元種族：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".racetype.root").toString()));
						  pl.sendMessage("ランク：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".racetype.rank").toString()));
						  pl.sendMessage("進化に必要な進化の欠片：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".evol.evolpoint.shard").toString()));
						  pl.sendMessage("進化に必要な進化の宝石：" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".evol.evolpoint.crystal").toString()));
						  pl.sendMessage("進化に必要な種族素材：" + THPlugin.plugin.getConfig().getInt(new StringBuilder("race.").append(inforace).append(".evol.raceitem.amount").toString()) + "個の" + Material.getMaterial(THPlugin.plugin.getConfig().getInt(new StringBuilder("race.").append(inforace).append(".evol.raceitem.typeid").toString())) + "(メタ" + THPlugin.plugin.getConfig().getInt(new StringBuilder("race.").append(inforace).append(".evol.raceitem.meta").toString()) + "）");
						  pl.sendMessage(THPlugin.plugin.getConfig().getString("race." + inforace + ".intro.story"));
						  pl.sendMessage(THPlugin.plugin.getConfig().getString("race." + inforace + ".intro.skills"));
					  }else {
						  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "その種族内部nameは存在しません。");
					  }
				  }else	{
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません。");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("evolchange")) && (args.length == 2)){
			  if ((sender instanceof Player)){
				  Player pl = ((Player)sender).getPlayer();
				  if ((pl.hasPermission("thr.evol.user.change")) || (pl.hasPermission("thr.user"))){
					  boolean existrace = false;
					  String inforace = "";
					  for (String race : THPlugin.plugin.getConfig().getConfigurationSection("race").getKeys(false)) {
						  if (race.toLowerCase().contains(args[1].toLowerCase())){
							  existrace = true;
							  inforace = race;
							  break;
						  }
					  }
					  if (existrace){
						  if (THPlugin.plugin.getConfig().getString("race." + inforace + ".racetype.root").contains(THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race"))){
							  PlayerInventory inventory = pl.getInventory();
							  int ok_shard = 0;
							  int ok_crystal = 0;
							  int ok_raceitem = 0;
							  ItemStack shard = null;
							  ItemStack crystal = null;
							  ItemStack raceitem = null;
							  if (THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.shard") != 0){
								  shard = new ItemStack(Material.PRISMARINE_SHARD, THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.shard"));
								  if (inventory.contains(shard)) {
									  ok_shard = 1;
								  } else {
									  ok_shard = 2;
								  }
							  }
							  if (THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.crystal") != 0){
								  crystal = new ItemStack(Material.PRISMARINE_CRYSTALS, THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.crystal"));
								  if (inventory.contains(crystal)) {
									  ok_crystal = 1;
								  } else {
									  ok_shard = 2;
								  }
							  }
							  if (THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount") != 0){
								  raceitem = new ItemStack(Material.getMaterial(THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.typeid")), THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount"));
								  int raceitemmeta = THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.meta");
								  raceitem.setDurability((short)raceitemmeta);
								  if (inventory.contains(raceitem)) {
									  ok_raceitem = 1;
								  } else {
									  ok_raceitem = 2;
								  }
							  }
							  if ((ok_shard == 2) || (ok_crystal == 2) || (ok_raceitem == 2)){
								  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "その種族に進化する為のアイテムがありません！");
							  }else {
								  pl.playSound(pl.getLocation(), Sound.PORTAL_TRAVEL, 1.0F, 1.0F);
								  if (ok_shard == 1) {
									  pl.getInventory().remove(shard);
								  }
								  if (ok_crystal == 1) {
									  pl.getInventory().remove(crystal);
								  }
								  if (ok_raceitem == 1) {
									  pl.getInventory().remove(raceitem);
								  }
								  THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".race", inforace);
								  THPlugin.plugin.saveConfig();
								  Bukkit.broadcastMessage(THPlugin.thrpre + ChatColor.BLUE + pl.getName() + "は" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".racetype.root").toString()) + "から" + THPlugin.plugin.getConfig().getString(new StringBuilder("race.").append(inforace).append(".display.real").toString()) + "に進化した！！");

								  ItemStack rewarditem = null;
								  if (THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.amount") != 0){
									  rewarditem = new ItemStack(Material.getMaterial(THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.typeid")), THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.amount"));
									  int rewarditemmeta = THPlugin.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.meta");
									  rewarditem.setDurability((short)rewarditemmeta);
									  pl.getInventory().addItem(new ItemStack[] { rewarditem });
								  }
							  }
						  }else {
							  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "進化できる種族ではありません！");
						  }
					  }else {
						  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "その種族内部nameは存在しません。");
					  }
				  }else {
					  pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "権限がありません。");
				  }
			  }
		  }else if ((commandLabel.equalsIgnoreCase("thr")) && (args.length >= 1)){
			  sender.sendMessage(THPlugin.plugin + "§c コマンドが間違っているよ.");
		  }
		  return true;
	  }
}