package org.schabi.newpipe.extractor.services.pornhub.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public class PornhubStreamLinkHandlerFactory extends LinkHandlerFactory {

	private static final PornhubStreamLinkHandlerFactory instance = new PornhubStreamLinkHandlerFactory();

	private PornhubStreamLinkHandlerFactory() {
	}

	public static PornhubStreamLinkHandlerFactory getInstance() {
		return instance;
	}

	@Override
	public String getId(String urlString) throws ParsingException {

		URL url;
		try {
			url = Utils.stringToURL(urlString);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("The given URL is not valid");
		}

		return Utils.getQueryValue(url, "viewkey");
	}

	@Override
	public String getUrl(String id) throws ParsingException {
		return "https://www.pornhub.com/view_video.php?viewkey=" + id;
	}

	@Override
	public boolean onAcceptUrl(String url) throws ParsingException {
		try {
			Utils.stringToURL(url);

			return url.startsWith("http:") || url.startsWith("https:");
		} catch (MalformedURLException e) {
			return false;
		}
	}
}
