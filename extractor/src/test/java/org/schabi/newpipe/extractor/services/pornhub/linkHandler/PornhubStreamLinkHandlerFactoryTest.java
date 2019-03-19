package org.schabi.newpipe.extractor.services.pornhub.linkHandler;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubStreamLinkHandlerFactoryTest {
	private static PornhubStreamLinkHandlerFactory linkHandler;

	@BeforeClass
	public static void setUp() {
		linkHandler = PornhubStreamLinkHandlerFactory.getInstance();
		NewPipe.init(Downloader.getInstance(), new Localization("GB", "en"));
	}

	@Test
	public void testGetId() throws Exception {
		Assert.assertEquals("ph5c6770b8cefff", linkHandler.fromUrl("/view_video.php?viewkey=ph5c6770b8cefff").getId());
	}
}
