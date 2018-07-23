package it.mineblock.easyenchant;

import it.mineblock.mbcore.spigot.Chat;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Message {
    ENCHANTMENT_DISABLED("enchantment-disabled"),
    ENCHANTMENT_NOT_SAFE("enchantment-not-safe");

    private String message;
    Message(String message){
        this.message = "messages." + message;
    }

    public String get(Enchantment enchantment, ItemStack itemStack){
        return Chat.getTranslated(Main.config.getString(message)
                .replace("{enchantment}", enchantment.getName())
                .replace("{item}", itemStack.getType().name()));
    }
}
