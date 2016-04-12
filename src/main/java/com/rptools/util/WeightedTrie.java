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

import java.util.Optional;

import lombok.Data;

/**
 * Implementation of Trie with weighted children to choose from
 */
public class WeightedTrie<T> {
    private TrieNode<T> root;

    WeightedTrie(TrieNode<T> root) {
        this.root = root;
    }

    public T random() {
        return root.random();
    }

    public Optional<T> random(T group) {
        return root.random(group);
    }

    @Data
    static class TrieNode<T> {
        private T item;
        private Integer weight;
        private WeightedList<TrieNode<T>> children;

        TrieNode(T item, Integer weight) {
            this.item = item;
            this.weight = weight;
            this.children = new WeightedList<>();
        }

        void addChild(TrieNode<T> node) {
            this.children.add(node.weight, node);
        }

        public T getItem() {
            return item;
        }

        public T random() {
            if (this.children.isEmpty()) {
                return null;
            }
            return this.children.random().getItem();
        }

        public Optional<T> random(T group) {
            Optional<TrieNode<T>> child = this.children.valueStream().filter(t -> t.matchesItem(group)).findAny();
            return child.map(TrieNode::random);
        }

        boolean matchesItem(T that) {
            return that != null && this.item.equals(that);
        }
    }
}
