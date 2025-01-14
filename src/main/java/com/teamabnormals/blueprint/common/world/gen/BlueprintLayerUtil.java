package com.teamabnormals.blueprint.common.world.gen;

/**
 * Utility class for creating layers.
 * <p>Currently only used for creating the end biome layer.</p>
 *
 * @author SmellyModder (Luke Tonon)
 */
public final class BlueprintLayerUtil {
//	/**
//	 * Creates a {@link Layer} containing end biomes from the Blueprint end biome registry.
//	 *
//	 * @param lookupRegistry A {@link Registry} to lookup the biomes from.
//	 * @param contextFactory A {@link LongFunction} to use as a factory for the context.
//	 * @param <R>            The type of {@link BigContext} the factory creates.
//	 * @return A {@link Layer} containing randomized end biomes from the Blueprint end biome registry.
//	 */
//	public static <R extends BigContext<LazyArea>> Layer createEndBiomeLayer(Registry<Biome> lookupRegistry, LongFunction<R> contextFactory) {
//		AreaFactory<LazyArea> biomesFactory = new EndBiomesLayer(lookupRegistry).run(contextFactory.apply(1L));
//		biomesFactory = Layers.zoom(100L, ZoomLayer.NORMAL, biomesFactory, 2, contextFactory);
//
//		for (int i = 0; i < 3; i++) {
//			biomesFactory = ZoomLayer.NORMAL.run(contextFactory.apply(1000L + (long) i), biomesFactory);
//		}
//
//		biomesFactory = SmoothLayer.INSTANCE.run(contextFactory.apply(100L), biomesFactory);
//		return new Layer(VoronoiZoomLayer.INSTANCE.run(contextFactory.apply(10L), biomesFactory));
//	}
//
//	/**
//	 * An {@link AreaTransformer1} implementation for performing a voronoi zoom.
//	 */
//	public enum VoronoiZoomLayer implements AreaTransformer1 {
//		INSTANCE;
//
//		public int applyPixel(BigContext<?> extendedNoiseRandom, Area area, int p_215728_3_, int p_215728_4_) {
//			int i = p_215728_3_ - 2;
//			int j = p_215728_4_ - 2;
//			int k = i >> 2;
//			int l = j >> 2;
//			int i1 = k << 2;
//			int j1 = l << 2;
//			extendedNoiseRandom.initRandom(i1, j1);
//			double d0 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D;
//			double d1 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D;
//			extendedNoiseRandom.initRandom(i1 + 4, j1);
//			double d2 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
//			double d3 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D;
//			extendedNoiseRandom.initRandom(i1, j1 + 4);
//			double d4 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D;
//			double d5 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
//			extendedNoiseRandom.initRandom(i1 + 4, j1 + 4);
//			double d6 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
//			double d7 = ((double) extendedNoiseRandom.nextRandom(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
//			int k1 = i & 3;
//			int l1 = j & 3;
//			double d8 = ((double) l1 - d1) * ((double) l1 - d1) + ((double) k1 - d0) * ((double) k1 - d0);
//			double d9 = ((double) l1 - d3) * ((double) l1 - d3) + ((double) k1 - d2) * ((double) k1 - d2);
//			double d10 = ((double) l1 - d5) * ((double) l1 - d5) + ((double) k1 - d4) * ((double) k1 - d4);
//			double d11 = ((double) l1 - d7) * ((double) l1 - d7) + ((double) k1 - d6) * ((double) k1 - d6);
//			if (d8 < d9 && d8 < d10 && d8 < d11) {
//				return area.get(this.getParentX(i1), this.getParentY(j1));
//			} else if (d9 < d8 && d9 < d10 && d9 < d11) {
//				return area.get(this.getParentX(i1 + 4), this.getParentY(j1)) & 255;
//			} else {
//				return d10 < d8 && d10 < d9 && d10 < d11 ? area.get(this.getParentX(i1), this.getParentY(j1 + 4)) : area.get(this.getParentX(i1 + 4), this.getParentY(j1 + 4)) & 255;
//			}
//		}
//
//		@Override
//		public int getParentX(int x) {
//			return x >> 2;
//		}
//
//		@Override
//		public int getParentY(int z) {
//			return z >> 2;
//		}
//	}
//
//	/**
//	 * An {@link AreaTransformer0} implementation that handles the applying of modded end biomes.
//	 *
//	 * @author SmellyModder (Luke Tonon)
//	 */
//	static class EndBiomesLayer implements AreaTransformer0 {
//		private final Registry<Biome> lookupRegistry;
//
//		EndBiomesLayer(Registry<Biome> lookupRegistry) {
//			this.lookupRegistry = lookupRegistry;
//		}
//
//		@Override
//		public int applyPixel(Context random, int x, int z) {
//			Registry<Biome> lookupRegistry = this.lookupRegistry;
//			return lookupRegistry.getId(lookupRegistry.get(BiomeUtil.getEndBiome(random)));
//		}
//	}
}
