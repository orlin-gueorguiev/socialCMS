package com.github.orlin.socialCMS.database.services.interfaces;

import com.github.orlin.socialCMS.database.entities.CompanyDao;
import com.github.orlin.socialCMS.database.filters.CompanyFilter;

public interface CompanyDBService<T extends CompanyDao, F extends CompanyFilter> extends DBService<T, F> {

}
