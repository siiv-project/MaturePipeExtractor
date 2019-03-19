package org.schabi.newpipe.extractor.services.pornhub;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public class PornhubTrendingLinkHandlerFactory extends ListLinkHandlerFactory {
	@Override
	public String getUrl(String id, List<String> contentFilter, String sortFilter) throws ParsingException {
		return "https://www.pornhub.com/recommended";
	}

	@Override
	public String getId(String url) throws ParsingException {
		return null;
	}

	@Override
	public boolean onAcceptUrl(String url) throws ParsingException {
		URL urlObj;
		try {
			urlObj = Utils.stringToURL(url);
		} catch (MalformedURLException e) {
			return false;
		}

		return true;
	}
}
