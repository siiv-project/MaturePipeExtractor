package org.schabi.newpipe.extractor.services.pornhub;

import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public class PornhubStreamInfoItemExtractor implements StreamInfoItemExtractor {

	private final Element item;

	public PornhubStreamInfoItemExtractor(Element item) {
		this.item = item;
	}

	@Override
	public StreamType getStreamType() throws ParsingException {
		return null;
	}

	@Override
	public boolean isAd() throws ParsingException {
		return false;
	}

	@Override
	public long getDuration() throws ParsingException {
		return 0;
	}

	@Override
	public long getViewCount() throws ParsingException {
		return 0;
	}

	@Override
	public String getUploaderName() throws ParsingException {
		return null;
	}

	@Override
	public String getUploaderUrl() throws ParsingException {
		return null;
	}

	@Override
	public String getUploadDate() throws ParsingException {
		return null;
	}

	@Override
	public String getName() throws ParsingException {
		return null;
	}

	@Override
	public String getUrl() throws ParsingException {
		return null;
	}

	@Override
	public String getThumbnailUrl() throws ParsingException {
		return null;
	}
}
