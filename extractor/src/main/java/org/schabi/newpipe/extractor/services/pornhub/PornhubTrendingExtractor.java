package org.schabi.newpipe.extractor.services.pornhub;

import java.io.IOException;
import javax.annotation.Nonnull;
import org.schabi.newpipe.extractor.Downloader;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubTrendingExtractor extends KioskExtractor<StreamInfoItem> {
	public PornhubTrendingExtractor(StreamingService streamingService, ListLinkHandler linkHandler, String kioskId, Localization localization) {
		super(streamingService, linkHandler, kioskId, localization);
	}

	@Override
	public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {

	}

	@Nonnull
	@Override
	public String getName() throws ParsingException {
		return null;
	}

	@Nonnull
	@Override
	public InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
		return null;
	}

	@Override
	public String getNextPageUrl() throws IOException, ExtractionException {
		return null;
	}

	@Override
	public InfoItemsPage<StreamInfoItem> getPage(String pageUrl) throws IOException, ExtractionException {
		return null;
	}
}
