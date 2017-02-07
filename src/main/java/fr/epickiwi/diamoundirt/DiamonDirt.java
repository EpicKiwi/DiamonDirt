package fr.epickiwi.diamoundirt;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.RepresentedItemData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.spawn.BlockSpawnCause;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.EntityUniverse;

@Plugin(id="epic-kiwi-diamondirt",name="DiamonDirt",version="1.0")
public class DiamonDirt {

    @Inject
    private GameRegistry gameRegistry;
    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event){
        logger.info("DimounDirt started");
    }

    @Listener
    public void onDropEvent(DropItemEvent.Destruct event){
        World world = event.getTargetWorld();
        for(int i = 0; i<event.getEntities().size(); i++){
            Entity entity = event.getEntities().get(i);
            if(entity instanceof Item){
                Item item = (Item) entity;
                if(item.getItemType().equals(ItemTypes.DIRT)){
                    ItemStack itemStack = gameRegistry.createBuilder(ItemStack.Builder.class)
                            .quantity(1)
                            .itemType(ItemTypes.DIAMOND).build();
                    Item newItem = (Item) world.createEntity(EntityTypes.ITEM,item.getLocation().getPosition());
                    newItem.offer(Keys.REPRESENTED_ITEM,itemStack.createSnapshot());
                    event.getEntities().set(i,newItem);
                }
            }
        }
    }

    @Listener
    public void onServerStoped(GameStoppedEvent event){
        logger.info("DiamonDirt stopped");
    }

}
