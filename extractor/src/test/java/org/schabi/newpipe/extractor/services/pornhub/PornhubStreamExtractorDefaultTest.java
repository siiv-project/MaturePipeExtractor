package org.schabi.newpipe.extractor.services.pornhub;

import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.schabi.newpipe.extractor.NewPipe;
import static org.schabi.newpipe.extractor.ServiceList.Pornhub;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubStreamExtractorDefaultTest {
	private static PornhubStreamExtractor extractor;

	@BeforeClass
	public static void setUp() throws Exception {
		NewPipe.init(Downloader.getInstance(), new Localization("GB", "en"));
		extractor = (PornhubStreamExtractor) Pornhub
			.getStreamExtractor("https://www.pornhub.com/view_video.php?viewkey=ph55a4ea86dd722");
		extractor.fetchPage();
	}

	@Test
	public void testGetTitle() throws ParsingException {
		assertFalse(extractor.getName().isEmpty());
	}

	@Test
	public void testGetViewCount() throws ParsingException {
		assertTrue(extractor.getViewCount() > 0);
	}

	@Test
	public void testGetLikeCount() throws ParsingException {
		assertTrue(extractor.getLikeCount() > 0);
	}

	@Test
	public void testGetDislikeCount() throws ParsingException {
		assertTrue(extractor.getDislikeCount() > 0);
	}

	@Test
	public void testGetVideoStreams() throws ExtractionException, IOException {
		assertFalse(extractor.getVideoStreams().isEmpty());
	}

	@Test
	public void testGetNextVideoStream() throws ExtractionException, IOException {
		assertNotNull(extractor.getNextStream());
	}

	@Test
	public void testGetRelatedVideoStreams() throws ExtractionException, IOException {
		assertNotNull(extractor.getRelatedStreams());
	}
}
