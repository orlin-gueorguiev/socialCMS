package com.github.orlin.socialCMS.database.services.interfaces;

import com.github.orlin.socialCMS.database.entities.CountryDao;
import com.github.orlin.socialCMS.database.filters.CountryFilter;

public interface CountryDBService<T extends CountryDao, F extends CountryFilter> extends DBService<T, F> {

}
