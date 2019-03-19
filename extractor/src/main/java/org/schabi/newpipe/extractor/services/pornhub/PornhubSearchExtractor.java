package org.schabi.newpipe.extractor.services.pornhub;

import java.io.IOException;
import javax.annotation.Nonnull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.Downloader;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.search.InfoItemsSearchCollector;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubePlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubSearchExtractor extends SearchExtractor {

	private Document doc;

	public PornhubSearchExtractor(StreamingService service, SearchQueryHandler linkHandler, Localization localization) {
		super(service, linkHandler, localization);
	}

	@Nonnull
	@Override
	public String getUrl() throws ParsingException {
		return super.getUrl();
	}

	@Override
	public String getSearchSuggestion() throws ParsingException {
		return null;
	}

	@Nonnull
	@Override
	public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
		return new InfoItemsPage<>(collectItems(doc), getNextPageUrl());
	}

	@Override
	public String getNextPageUrl() throws IOException, ExtractionException {
		return null;
	}

	@Override
	public InfoItemsPage<InfoItem> getPage(String pageUrl) throws IOException, ExtractionException {
		return null;
	}

	@Override
	public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
		final String site;
		final String url = getUrl();
		//String url = builder.build().toString();
		//if we've been passed a valid language code, append it to the URL
		site = downloader.download(url, getLocalization());

		doc = Jsoup.parse(site, url);
	}

	private InfoItemsSearchCollector collectItems(Document doc) throws NothingFoundException  {
		InfoItemsSearchCollector collector = getInfoItemSearchCollector();

		Element list = doc.select("ul[id=\"videoSearchResult\"]").first();

		for (Element item : list.children()) {
            /* First we need to determine which kind of item we are working with.
               Youtube depicts five different kinds of items on its search result page. These are
               regular videos, playlists, channels, two types of video suggestions, and a "no video
               found" item. Since we only want videos, we need to filter out all the others.
               An example for this can be seen here:
               https://www.youtube.com/results?search_query=asdf&page=1

               We already applied a filter to the url, so we don't need to care about channels and
               playlists now.
            */

			Element el;

			if ((el = item.select("div[class*=\"search-message\"]").first()) != null) {
				throw new NothingFoundException(el.text());

				// video item type
			} else if ((el = item.select("li[class*=\"js-pop videoblock videoBox\"]").first()) != null) {
				collector.commit(new PornhubStreamInfoItemExtractor(el));
			}
		}

		return collector;
	}
}
