package rebelkeithy.mods.metallurgy.core.database;

import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import rebelkeithy.mods.metallurgy.core.database.csv.CSVLineProcessor;
import rebelkeithy.mods.metallurgy.core.metalsets.ItemMetallurgy;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemLineProcessor extends CSVLineProcessor<Boolean>
{
    private final Configuration config;
    private final CreativeTabs tab;
    private final Map<String, Item> items;

    ItemLineProcessor(final Configuration config, final CreativeTabs tab,
            final Map<String, Item> items)
    {
        this.config = config;
        this.tab = tab;
        this.items = items;
    }

    @Override
    public Boolean getResult()
    {
        return true;
    }

    @Override
    public void processLine(final Map<String, String> line)
    {
        int id = Integer.parseInt(line.get("Item ID"));

        final String itemName = line.get("Item Name");
        final String setName = line.get("Set Name");

        id = config.get("Item IDs", itemName, id).getInt();
        final Item item =
                new ItemMetallurgy(id).setTextureName("Metallurgy:" + setName + "/" + itemName)
                        .setUnlocalizedName("Metallurgy:" + setName + "/" + itemName)
                        .setCreativeTab(tab);
        LanguageRegistry.addName(item, itemName);

        items.put(itemName, item);
        if (!line.get("Ore Dictionary Name").equals("0"))
            OreDictionary.registerOre(line.get("Ore Dictionary Name"), item);
    }
}