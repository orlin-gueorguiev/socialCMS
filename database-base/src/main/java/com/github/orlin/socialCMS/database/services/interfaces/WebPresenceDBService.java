package com.github.orlin.socialCMS.database.services.interfaces;

import com.github.orlin.socialCMS.database.entities.WebPresenceDao;
import com.github.orlin.socialCMS.database.filters.WebPresenceFilter;

public interface WebPresenceDBService<T extends WebPresenceDao, F extends WebPresenceFilter> extends DBService<T, F> {

}
