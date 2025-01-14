package com.teamabnormals.blueprint.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlueprintBiomeTagsProvider extends TagsProvider<Biome> {

	public BlueprintBiomeTagsProvider(String modid, DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, BuiltinRegistries.BIOME, modid, fileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(BlueprintBiomeTags.IS_OVERWORLD).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES, Biomes.DESERT, Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_SAVANNA, Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS, Biomes.MEADOW, Biomes.GROVE, Biomes.SNOWY_SLOPES, Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.STONY_PEAKS, Biomes.MUSHROOM_FIELDS, Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES);

		this.tag(BlueprintBiomeTags.IS_EXTREME_HILLS).add(Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST);
		this.tag(BlueprintBiomeTags.IS_PLAINS).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS);
		this.tag(BlueprintBiomeTags.IS_ICY).add(Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES);
		this.tag(BlueprintBiomeTags.IS_SAVANNA).add(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA);
		this.tag(BlueprintBiomeTags.IS_DESERT).add(Biomes.DESERT);
		this.tag(BlueprintBiomeTags.IS_SWAMP).add(Biomes.SWAMP);
		this.tag(BlueprintBiomeTags.IS_MUSHROOM).add(Biomes.MUSHROOM_FIELDS);
		this.tag(BlueprintBiomeTags.IS_UNDERGROUND).add(Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES);

		this.tag(BlueprintBiomeTags.IS_END).add(Biomes.THE_END).addTag(BlueprintBiomeTags.IS_OUTER_END);
		this.tag(BlueprintBiomeTags.IS_OUTER_END).add(Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS);

		this.tag(BlueprintBiomeTags.WITHOUT_MONSTER_SPAWNS).add(Biomes.MUSHROOM_FIELDS);
	}

	@Override
	public String getName() {
		return "Biome Tags";
	}
}