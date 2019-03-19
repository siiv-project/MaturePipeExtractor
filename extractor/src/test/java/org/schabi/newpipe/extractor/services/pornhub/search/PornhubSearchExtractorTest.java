package org.schabi.newpipe.extractor.services.pornhub.search;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.NewPipe;
import static org.schabi.newpipe.extractor.ServiceList.Pornhub;
import org.schabi.newpipe.extractor.services.pornhub.PornhubSearchExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubSearchExtractorTest {

	protected static PornhubSearchExtractor extractor;
	protected static ListExtractor.InfoItemsPage<InfoItem> itemsPage;

	@BeforeClass
	public static void setUpClass() throws Exception {
		NewPipe.init(Downloader.getInstance(), new Localization("GB", "en"));
		extractor = (PornhubSearchExtractor) Pornhub.getSearchExtractor("blowjob");
		extractor.fetchPage();
		itemsPage = extractor.getInitialPage();
	}

	@Test
	public void testGetUrl() throws Exception {
		assertEquals("https://www.pornhub.com/video/search?search=blowjob", extractor.getUrl());
	}

	@Test
	public void testInitialPageItems() throws Exception {
		assertEquals(20, itemsPage.getItems().size());
	}

	@Test
	public void testInfoItemExtraction() throws Exception {
		InfoItem infoItem = itemsPage.getItems().get(0);

		assertNotNull(infoItem.getInfoType());
		assertNotNull(infoItem.getName());
		assertNotNull(infoItem.getUrl());
		assertNotNull(infoItem.getThumbnailUrl());
	}

	@Test
	public void testStreamItemExtraction() throws Exception {
		InfoItem infoItem = itemsPage.getItems().get(0);
		assertTrue(infoItem instanceof StreamInfoItem);

		StreamInfoItem streamInfoItem = (StreamInfoItem) infoItem;

		assertNotNull(streamInfoItem.getStreamType());
		assertNotEquals(0, streamInfoItem.getDuration());
	}
}
