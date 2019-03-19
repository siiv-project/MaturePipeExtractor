package org.schabi.newpipe.extractor.services.pornhub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import org.schabi.newpipe.extractor.NewPipe;
import static org.schabi.newpipe.extractor.ServiceList.Pornhub;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubServiceTest {
	static StreamingService service;
	static KioskList kioskList;

	@BeforeClass
	public static void setUp() throws Exception {
		NewPipe.init(Downloader.getInstance(), new Localization("GB", "en"));
		service = Pornhub;
		kioskList = service.getKioskList();
	}

	@Test
	public void testGetKioskAvailableKiosks() throws Exception {
		assertFalse("No kiosk got returned", kioskList.getAvailableKiosks().isEmpty());
	}

	@Test
	public void testGetDefaultKiosk() throws Exception {
		assertEquals(kioskList.getDefaultKioskExtractor(null).getId(), "Trending");
	}
}
