package com.github.orlin.socialCMS.database.services.interfaces;

import com.github.orlin.socialCMS.database.entities.AddressDao;
import com.github.orlin.socialCMS.database.filters.AddressFilter;

public interface AddressDBService<T extends AddressDao, F extends AddressFilter> extends DBService<T, F> {

}
