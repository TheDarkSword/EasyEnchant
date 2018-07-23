package it.mineblock.easyenchant.listeners;

import it.mineblock.easyenchant.Main;
import it.mineblock.easyenchant.Message;
import it.mineblock.mbcore.spigot.Chat;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.List;

public class EnchantItem implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(event.getPlayer().hasPermission("easyenchant.use")) return;
        List<ItemStack> items = Main.getNearbyItems(event.getItemDrop().getLocation(), 2, 2, 2);
        ItemStack item = event.getItemDrop().getItemStack();

        if (item.getType().equals(Material.ENCHANTED_BOOK)) {
            if(items.size() == 0) return;


            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            ItemStack itemEnchanted = items.get(0);
            for(Enchantment enchantment : meta.getStoredEnchants().keySet()){
                if(Main.config.getBoolean("enchantments." + enchantment.getName() + ".enabled")){
                    if(enchantment.canEnchantItem(itemEnchanted)) {
                        itemEnchanted.addEnchantment(enchantment, meta.getStoredEnchantLevel(enchantment));
                        meta.removeStoredEnchant(enchantment);
                    } else {
                        Chat.send(Message.ENCHANTMENT_NOT_SAFE.get(enchantment, itemEnchanted), event.getPlayer());
                    }
                } else {
                    Chat.send(Message.ENCHANTMENT_DISABLED.get(enchantment, itemEnchanted), event.getPlayer());
                }
            }
            if(meta.getStoredEnchants().isEmpty()){
                item.setAmount(0);
            } else {
                item.setItemMeta(meta);
            }
        } else {
            for (ItemStack book : items) {
                if (book.getType().equals(Material.ENCHANTED_BOOK)) {

                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
                    for(Enchantment enchantment : meta.getStoredEnchants().keySet()){
                        if(Main.config.getBoolean("enchantments." + enchantment.getName() + ".enabled")){
                            if(enchantment.canEnchantItem(item)) {
                                item.addEnchantment(enchantment, meta.getStoredEnchantLevel(enchantment));
                                meta.removeStoredEnchant(enchantment);
                            } else {
                                Chat.send(Message.ENCHANTMENT_NOT_SAFE.get(enchantment, item), event.getPlayer());
                            }
                        }  else {
                            Chat.send(Message.ENCHANTMENT_DISABLED.get(enchantment, item), event.getPlayer());
                        }
                    }
                    if(meta.getStoredEnchants().isEmpty()){
                        book.setAmount(0);
                    } else {
                        book.setItemMeta(meta);
                    }
                    event.getItemDrop().setItemStack(item);
                }
            }
        }
    }
}
