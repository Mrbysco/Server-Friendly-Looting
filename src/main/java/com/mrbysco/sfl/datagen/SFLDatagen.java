package com.mrbysco.sfl.datagen;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.datagen.client.SFLLanguageProvider;
import com.mrbysco.sfl.datagen.client.SFLModelProvider;
import com.mrbysco.sfl.datagen.server.SFLDatapackProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SFLDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();

		generator.addProvider(true, new SFLLanguageProvider(packOutput));
		generator.addProvider(true, new SFLModelProvider(packOutput));

		generator.addProvider(true, new SFLDatapackProvider(
				packOutput,
				event.getLookupProvider(),
				Set.of(ServerFriendlyLoot.MOD_ID)
		));
	}
}
