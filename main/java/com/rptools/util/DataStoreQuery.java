package com.rptools.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

@Component
@Scope("session")
public class DataStoreQuery {
    private Provider<User> userProvider;

    @Autowired
    public DataStoreQuery(Provider<User> userProvider) {
        this.userProvider = userProvider;
    }

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
