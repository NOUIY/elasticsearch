/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package org.elasticsearch.script;

import org.elasticsearch.common.util.Maps;

import java.util.Map;
import java.util.Set;

class UpsertMetadata extends UpdateMetadata {
    static final Map<String, FieldProperty<?>> PROPERTIES = Map.of(
        INDEX,
        SET_ONCE_STRING,
        ID,
        SET_ONCE_STRING,
        OP,
        new FieldProperty<>(String.class, true, true, null),
        TIMESTAMP,
        SET_ONCE_LONG
    );

    UpsertMetadata(String index, String id, String op, long timestamp) {
        super(metadataMap(index, id, op, timestamp), Set.of("noop", "create"), PROPERTIES);
    }

    protected static Map<String, Object> metadataMap(String index, String id, String op, long timestamp) {
        Map<String, Object> metadata = Maps.newHashMapWithExpectedSize(PROPERTIES.size());
        metadata.put(INDEX, index);
        metadata.put(ID, id);
        metadata.put(OP, op);
        metadata.put(TIMESTAMP, timestamp);
        return metadata;
    }

    @Override
    public String getRouting() {
        throw new UnsupportedOperationException("routing is unavailable for insert");
    }

    @Override
    public long getVersion() {
        throw new UnsupportedOperationException("version is unavailable for insert");
    }
}
