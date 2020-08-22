import java.util

import actions.JwtFilter
import javax.inject.Inject
import play.api.http.EnabledFilters
import play.filters.cors.CORSFilter
import play.http.DefaultHttpFilters
import play.mvc
import play.mvc.EssentialFilter

class Filters @Inject()(jwtFilter: JwtFilter, enabledFilters: EnabledFilters,
                        CORSFilter: CORSFilter) extends DefaultHttpFilters {

  override def getFilters: util.List[mvc.EssentialFilter] = {
    val filters = enabledFilters.asJava.getFilters
    val es: EssentialFilter = jwtFilter.asJava
    filters.add(es)
    filters.add(CORSFilter.asJava)
    filters
  }
}
