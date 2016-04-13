/*
 * RPToolkit - Tools to assist Role-Playing Game masters and players
 * Copyright (C) 2016 Dane Zeke Liergaard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.rptools.util;

import java.util.HashMap;
import java.util.Map;

import com.rptools.util.WeightedTrie.TrieNode;

/**
 * Builds a {@link WeightedTrie} by adding nodes from the top one-at-a-time.
 * 
 * @param <T>
 *            element within the trie
 */
public class WeightedTrieBuilder<T> {
    private final TrieNodeBuilder<T> root = new TrieNodeBuilder<>();

    /**
     * Builds the {@link WeightedTrie} we've all been waiting for
     * 
     * @return {@link WeightedTrie} of T items
     */
    public WeightedTrie<T> build() {
        return new WeightedTrie<>(this.root.build());
    }

    /**
     * Adds a top-level node that contains T with frequency. If a top-level node exists that already contains T, its
     * weight is increased by frequency.
     *
     * @param group
     *            T item contained by the node to add a child to
     * @param frequency
     *            Integer weight to instantiate or add to child element containing element T
     * @param parents
     *            T extra args that are the in-order ancestors of the child to add.
     */
    @SafeVarargs
    public final void addChild(T group, Integer frequency, T... parents) {
        root.addChild(group, frequency, parents);
    }

    /**
     * Builder of individual {@link TrieNode}s
     * 
     * @param <T>
     */
    private static class TrieNodeBuilder<T> {
        private T item;
        private Integer weight;
        private Map<T, TrieNodeBuilder<T>> children;

        TrieNodeBuilder() {
            this(null, 1);
        }

        TrieNodeBuilder(T item, Integer weight) {
            this.item = item;
            this.weight = weight;
            this.children = new HashMap<>();
        }

        void addChild(T data, Integer weight, T[] ancestors) {
            TrieNodeBuilder<T> parent = this;
            if (ancestors != null) {
                for (T t : ancestors) {
                    parent = children.compute(t, (key, cur) -> incrementWeight(key, cur, weight));
                }
            }
            parent.children.compute(data, (key, cur) -> incrementWeight(key, cur, weight));
        }

        TrieNodeBuilder<T> incrementWeight(T key, TrieNodeBuilder<T> currentValue, Integer weight) {
            if (currentValue == null) {
                return new TrieNodeBuilder<>(key, weight);
            }
            currentValue.weight += weight;
            return currentValue;
        }

        TrieNode<T> build() {
            TrieNode<T> node = new TrieNode<>(this.item, this.weight);
            for (TrieNodeBuilder<T> child : this.children.values()) {
                node.addChild(child.build());
            }
            return node;
        }
    }
}
