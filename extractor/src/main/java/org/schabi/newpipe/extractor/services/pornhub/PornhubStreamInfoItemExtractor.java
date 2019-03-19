package org.schabi.newpipe.extractor.services.pornhub;

import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

public class PornhubStreamInfoItemExtractor implements StreamInfoItemExtractor {

	private final Element item;

	public PornhubStreamInfoItemExtractor(Element item) {
		this.item = item;
	}

	@Override
	public StreamType getStreamType() throws ParsingException {
		return StreamType.VIDEO_STREAM;
	}

	@Override
	public boolean isAd() throws ParsingException {
		return false;
	}

	@Override
	public long getDuration() throws ParsingException {
		return  YoutubeParsingHelper.parseDurationString(item.getElementsByClass("duration").text());
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
		return item.getElementsByClass("linkVideoThumb").attr("title");
	}

	@Override
	public String getUrl() throws ParsingException {
		return "https://www.pornhub.com" + item.getElementsByClass("linkVideoThumb").attr("href");
	}

	@Override
	public String getThumbnailUrl() throws ParsingException {
		return item.getElementsByTag("img").attr("data-thumb_url");
	}
}
