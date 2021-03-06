/**
 * Copyright 2016 Twitter. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.twitter.graphjet.algorithms;

import java.util.Random;

import com.twitter.graphjet.bipartite.api.EdgeIterator;
import com.twitter.graphjet.bipartite.api.LeftIndexedBipartiteGraph;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

public class StaticLeftIndexedBipartiteGraph implements LeftIndexedBipartiteGraph {
  private final Long2ObjectMap<LongList> leftSideGraph;

  public StaticLeftIndexedBipartiteGraph(Long2ObjectMap<LongList> leftSideGraph) {
    this.leftSideGraph = leftSideGraph;
  }

  @Override public int getLeftNodeDegree(long leftNode) {
    if (!leftSideGraph.containsKey(leftNode)) {
      return 0;
    }
    return leftSideGraph.get(leftNode).size();
  }

  @Override public EdgeIterator getLeftNodeEdges(long leftNode) {
    return new MockEdgeIterator(leftSideGraph.get(leftNode).iterator());
  }

  @Override
  public EdgeIterator getRandomLeftNodeEdges(long leftNode, int numSamples, Random random) {
    LongList samples = new LongArrayList(numSamples);
    for (int i = 0; i < numSamples; i++) {
      LongList edges = leftSideGraph.get(leftNode);
      samples.add(edges.get(random.nextInt(edges.size())));
    }
    return new MockEdgeIterator(samples.iterator());
  }
}
