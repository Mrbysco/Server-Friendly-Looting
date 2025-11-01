package com.mrbysco.sfl.datagen.server;

import com.mrbysco.sfl.datagen.SFLBiomeModifiers;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SFLDatapackProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, $ -> {
			})
			.add(Registries.PLACED_FEATURE, $ -> {
			})
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, SFLBiomeModifiers::bootstrap);

	public SFLDatapackProvider(PackOutput output, CompletableFuture<Provider> registries, Set<String> modIds) {
		super(output, registries, BUILDER, modIds);
	}
}
