package net.brilliance.service.api.catalog;

import java.util.Map;

public interface CatalogExternalDataService {

  /**
   * Imports the catalog and it's dependencies from external source data. At present only support for Excel.
   * 
   * @param configParams The additional parameters
   * @return The execution status message
   * @throws CatalogExternalDataException If any error causes during the execution.
   */
	String importExternalCatalogues(Map<Object, Object> configParams) throws CatalogExternalDataException;
}
