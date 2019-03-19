package org.schabi.newpipe.extractor.services.pornhub;

import java.io.IOException;
import static org.junit.Assert.assertFalse;
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
			.getStreamExtractor("https://en.pornhub.com/view_video.php?viewkey=ph5c8619953df49");
		extractor.fetchPage();
	}

	@Test
	public void testGetTitle() throws ParsingException {
		assertFalse(extractor.getName().isEmpty());
	}

	@Test
	public void testGetVideoStreams() throws ExtractionException, IOException {
		assertFalse(extractor.getVideoStreams().isEmpty());
	}
}
