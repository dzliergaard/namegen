package com.rptools.util;

import com.google.appengine.api.datastore.Query;

public interface DataStoreQuery {
    Query getQuery(String type, String sort);
}
