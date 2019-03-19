package org.schabi.newpipe.extractor.services.pornhub;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;

public class PornhubSearchQueryHandlerFactory extends SearchQueryHandlerFactory {

	public static final String CHARSET_UTF_8 = "UTF-8";

	@Override
	public String getUrl(String query, List<String> contentFilter, String sortFilter) throws ParsingException {
		try {
			return "https://www.pornhub.com/video/search?search=" + URLEncoder.encode(query, CHARSET_UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new ParsingException("Could not encode query", e);
		}
	}


}
