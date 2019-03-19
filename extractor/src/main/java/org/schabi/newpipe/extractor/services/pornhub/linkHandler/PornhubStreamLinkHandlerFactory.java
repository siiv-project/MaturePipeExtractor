package org.schabi.newpipe.extractor.services.pornhub.linkHandler;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;

public class PornhubStreamLinkHandlerFactory extends LinkHandlerFactory {

	private static final PornhubStreamLinkHandlerFactory instance = new PornhubStreamLinkHandlerFactory();

	private PornhubStreamLinkHandlerFactory() {
	}

	public static PornhubStreamLinkHandlerFactory getInstance() {
		return instance;
	}

	@Override
	public String getId(String url) throws ParsingException {
		return url.replace("/view_video.php?viewkey=", "");
	}

	@Override
	public String getUrl(String id) throws ParsingException {
		return null;
	}

	@Override
	public boolean onAcceptUrl(String url) throws ParsingException {
		return true;
	}
}
