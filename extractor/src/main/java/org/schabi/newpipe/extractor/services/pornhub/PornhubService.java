package org.schabi.newpipe.extractor.services.pornhub;

import static java.util.Arrays.asList;
import java.util.List;
import org.schabi.newpipe.extractor.StreamingService;
import static org.schabi.newpipe.extractor.StreamingService.ServiceInfo.MediaCapability.AUDIO;
import static org.schabi.newpipe.extractor.StreamingService.ServiceInfo.MediaCapability.VIDEO;
import org.schabi.newpipe.extractor.SuggestionExtractor;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.pornhub.linkHandler.PornhubStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubService extends StreamingService {


	public PornhubService(int id) {
		super(id, "PornHub", asList(AUDIO, VIDEO));
	}

	/**
	 * Creates a new Streaming service.
	 * If you Implement one do not set id within your implementation of this extractor, instead
	 * set the id when you put the extractor into
	 * <a href="https://teamnewpipe.github.io/NewPipeExtractor/javadoc/org/schabi/newpipe/extractor/ServiceList.html">ServiceList</a>.
	 * All other parameters can be set directly from the overriding constructor.
	 *
	 * @param id the number of the service to identify him within the NewPipe frontend
	 * @param name the name of the service
	 * @param capabilities the type of media this service can handle
	 */
	public PornhubService(int id, String name, List<ServiceInfo.MediaCapability> capabilities) {
		super(id, name, capabilities);
	}

	@Override
	public LinkHandlerFactory getStreamLHFactory() {
		return PornhubStreamLinkHandlerFactory.getInstance();
	}

	@Override
	public ListLinkHandlerFactory getChannelLHFactory() {
		return null;
	}

	@Override
	public ListLinkHandlerFactory getPlaylistLHFactory() {
		return null;
	}

	@Override
	public SearchQueryHandlerFactory getSearchQHFactory() {
		return new PornhubSearchQueryHandlerFactory();
	}

	@Override
	public ListLinkHandlerFactory getCommentsLHFactory() {
		return null;
	}

	@Override
	public SearchExtractor getSearchExtractor(SearchQueryHandler query, Localization localization) {
		return new PornhubSearchExtractor(this, query, localization);
	}

	@Override
	public SuggestionExtractor getSuggestionExtractor(Localization localization) {
		return null;
	}

	@Override
	public SubscriptionExtractor getSubscriptionExtractor() {
		return null;
	}

	@Override
	public KioskList getKioskList() throws ExtractionException {
		KioskList list = new KioskList(getServiceId());

		// add kiosks here e.g.:
		try {
			list.addKioskEntry(new KioskList.KioskExtractorFactory() {
				@Override
				public KioskExtractor createNewKiosk(StreamingService streamingService,
													 String url,
													 String id,
													 Localization local)
					throws ExtractionException {
					return new PornhubTrendingExtractor(PornhubService.this,
						new PornhubTrendingLinkHandlerFactory().fromUrl(url), id, local);
				}
			}, new PornhubTrendingLinkHandlerFactory(), "Trending");
			list.setDefaultKiosk("Trending");
		} catch (Exception e) {
			throw new ExtractionException(e);
		}

		return list;
	}

	@Override
	public ChannelExtractor getChannelExtractor(ListLinkHandler linkHandler, Localization localization) throws ExtractionException {
		return null;
	}

	@Override
	public PlaylistExtractor getPlaylistExtractor(ListLinkHandler linkHandler, Localization localization) throws ExtractionException {
		return null;
	}

	@Override
	public StreamExtractor getStreamExtractor(LinkHandler linkHandler, Localization localization) throws ExtractionException {
		return null;
	}

	@Override
	public CommentsExtractor getCommentsExtractor(ListLinkHandler linkHandler, Localization localization) throws ExtractionException {
		return null;
	}
}
