package filters;

import javax.inject.Inject;
import play.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

public class Filters extends DefaultHttpFilters {

    CORSFilter corsFilter;

    @Inject
    public Filters(CORSFilter corsFilter) {
        super(corsFilter);
        this.corsFilter = corsFilter;
    }

    public EssentialFilter[] filters() {
        return new EssentialFilter[] { corsFilter.asJava() };
    }
}