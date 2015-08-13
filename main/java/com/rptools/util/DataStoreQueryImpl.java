package com.rptools.util;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class DataStoreQueryImpl implements DataStoreQuery {
    @Autowired private Provider<User> userProvider;

    @Override
    public Query getQuery(String type, String sort) {
        if (!userProvider.has()) {
            return null;
        }
        Query.Filter userEq = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, userProvider.get());
        Query.Filter userEqStr = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, userProvider
            .get()
            .toString());
        Query.Filter userNu = new Query.FilterPredicate("user", Query.FilterOperator.EQUAL, null);
        Query.Filter userOr = Query.CompositeFilterOperator.or(userEq, userNu, userEqStr);
        return new Query(type).setFilter(userOr).addSort(sort);
    }
}
