package it.mineblock.easyenchant;

import it.mineblock.easyenchant.listeners.EnchantItem;
import it.mineblock.mbcore.spigot.MBConfig;
import it.mineblock.mbcore.spigot.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private final String CONFIG = "config.yml";

    private static MBConfig configuration = new MBConfig();

    public static Configuration config;

    @Override
    public void onEnable() {

        config = configuration.autoloadConfig(this, this.getName(), getResource(CONFIG), new File(getDataFolder(), CONFIG), CONFIG);

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands(){

    }

    private void registerListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new EnchantItem(), this);
    }

    public static List<ItemStack> getNearbyItems(Location location, int radiusX, int radiusY, int radiusZ) {
        List<ItemStack> items = new ArrayList<>();
        /*for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    items.add(location.getWorld().getNearbyEntities(location, x, y, z))
                }
            }
        }*/
        for(Entity entity : location.getWorld().getNearbyEntities(location, radiusX, radiusY, radiusZ)){
            if(entity instanceof Item){
                items.add(((Item) entity).getItemStack());
            }
        }
        return items;
    }
}
