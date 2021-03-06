/*
 * Grapht, an open source dependency injector.
 * Copyright 2014-2015 various contributors (see CONTRIBUTORS.txt)
 * Copyright 2010-2014 Regents of the University of Minnesota
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.grouplens.grapht.graph;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Edges in DAGs.  These arise from building nodes with a {@link DAGNodeBuilder}.
 *
 * <p>Two edges are equal if they connect the same pair of nodes and have the same label.
 *
 * @param <V> The type of node labels.
 * @param <E> The type of edge labels.
 * @see DAGNode
 * @since 0.7.0
 * @author <a href="http://www.grouplens.org">GroupLens Research</a>
 */
public class DAGEdge<V,E> implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private final DAGNode<V,E> head;
    @NotNull
    private final DAGNode<V,E> tail;
    @NotNull
    @SuppressWarnings("squid:S1948") // serializable warning; edge is serializable iff its label type is
    private final E label;
    private transient volatile int hashCode;

    public static <V,E> DAGEdge<V,E> create(DAGNode<V,E> head, DAGNode<V,E> tail, E label) {
        return new DAGEdge<V,E>(head, tail, label);
    }

    DAGEdge(DAGNode<V,E> hd, DAGNode<V,E> tl, E lbl) {
        assert hd != null;
        assert tl != null;
        assert lbl != null;
        head = hd;
        tail = tl;
        label = lbl;
    }

    /**
     * Get the edge's head (starting node).
     * @return The edge's head.
     */
    @NotNull
    public DAGNode<V,E> getHead() {
        return head;
    }

    /**
     * Get the edge's tail (ending node).
     * @return The edge's tail.
     */
    @NotNull
    public DAGNode<V,E> getTail() {
        return tail;
    }

    /**
     * Get the edge's label.
     * @return The edge's label.
     */
    @NotNull
    public E getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = new HashCodeBuilder().append(head).append(tail).append(label).toHashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof DAGEdge) {
            DAGEdge<?,?> oe = (DAGEdge<?,?>) o;
            return head.equals(oe.getHead())
                   && tail.equals(oe.getTail())
                   && label.equals(oe.getLabel());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("edge ")
          .append(label)
          .append(" from ")
          .append(head)
          .append(" to ")
          .append(tail);
        return sb.toString();
    }
}