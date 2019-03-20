package org.schabi.newpipe.extractor.services.pornhub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.schabi.newpipe.extractor.Downloader;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.search.InfoItemsSearchCollector;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.SubtitlesStream;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.Localization;

public class PornhubStreamExtractor extends StreamExtractor {

	private String pageHtml = null;
	private Document doc;

	private ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");

	public PornhubStreamExtractor(StreamingService service, LinkHandler linkHandler, Localization localization) {
		super(service, linkHandler, localization);
	}

	@Nonnull
	@Override
	public String getUploadDate() throws ParsingException {
		return null;
	}

	@Nonnull
	@Override
	public String getThumbnailUrl() throws ParsingException {
		return doc.select("meta[property=\"og:image\"]").attr("content");
	}

	@Nonnull
	@Override
	public String getDescription() throws ParsingException {
		return doc.select("meta[property=\"og:description\"]").attr("content");
	}

	@Override
	public int getAgeLimit() throws ParsingException {
		return 18;
	}

	@Override
	public long getLength() throws ParsingException {
		return 0;
	}

	@Override
	public long getTimeStamp() throws ParsingException {
		return 0;
	}

	@Override
	public long getViewCount() throws ParsingException {
		return Long.valueOf(doc.select(".rating-info-container .views .count").text().replaceAll(",", ""));
	}

	@Override
	public long getLikeCount() throws ParsingException {
		return Long.valueOf(doc.select("span.votesUp").text());
	}

	@Override
	public long getDislikeCount() throws ParsingException {
		return Long.valueOf(doc.select("span.votesDown").text());
	}

	@Nonnull
	@Override
	public String getUploaderUrl() throws ParsingException {
		return doc.select(".usernameWrap a").attr("href");
	}

	@Nonnull
	@Override
	public String getUploaderName() throws ParsingException {
		return doc.select(".usernameWrap a").text();
	}

	@Nonnull
	@Override
	public String getUploaderAvatarUrl() throws ParsingException {
		return "";
	}

	@Nonnull
	@Override
	public String getDashMpdUrl() throws ParsingException {
		return "";
	}

	@Nonnull
	@Override
	public String getHlsUrl() throws ParsingException {
		return "";
	}

	@Override
	public List<AudioStream> getAudioStreams() throws IOException, ExtractionException {
		List<AudioStream> streams = new ArrayList<>();
		Map<String, Object> flashVars = getFlashVars();
		NativeArray mediaDefinitions = (NativeArray) flashVars.get("mediaDefinitions");

		for (Object definition : mediaDefinitions) {
			NativeObject def = (NativeObject) definition;

			if (def != null) {
				String url = (String) def.get("videoUrl");
				streams.add(new AudioStream(url, MediaFormat.M4A, -1));
			}
		}

		return streams;
	}

	@Override
	public List<VideoStream> getVideoStreams() throws IOException, ExtractionException {
		List<VideoStream> streams = new ArrayList<>();
		Map<String, Object> flashVars = getFlashVars();
		NativeArray mediaDefinitions = (NativeArray) flashVars.get("mediaDefinitions");

		for (Object definition : mediaDefinitions) {
			NativeObject def = (NativeObject) definition;

			if (def != null) {
				String url = (String) def.get("videoUrl");
				String resolution = (String) def.get("quality");
				streams.add(new VideoStream(url, MediaFormat.MPEG_4, resolution));
			}
		}

		return streams;
	}

	@Override
	public List<VideoStream> getVideoOnlyStreams() throws IOException, ExtractionException {
		return new ArrayList<>();
	}

	@Nonnull
	@Override
	public List<SubtitlesStream> getSubtitlesDefault() throws IOException, ExtractionException {
		return new ArrayList<>();
	}

	@Nonnull
	@Override
	public List<SubtitlesStream> getSubtitles(MediaFormat format) throws IOException, ExtractionException {
		return new ArrayList<>();
	}

	@Override
	public StreamType getStreamType() throws ParsingException {
		return StreamType.VIDEO_STREAM;
	}

	@Override
	public StreamInfoItem getNextStream() throws IOException, ExtractionException {
		return getRelatedStreams().getStreamInfoItemList().get(0);
	}

	@Override
	public StreamInfoItemsCollector getRelatedStreams() throws IOException, ExtractionException {

		StreamInfoItemsCollector collector = new StreamInfoItemsCollector(getServiceId());

		Element list = doc.select("ul[id=\"relateRecommendedItems\"]").first();

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
				throw new SearchExtractor.NothingFoundException(el.text());

				// video item type
			} else if ((el = item.select("li[class*=\"js-pop videoblock videoBox\"]").first()) != null) {
				collector.commit(new PornhubStreamInfoItemExtractor(el));
			}
		}

		return collector;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
		getPageHtml(downloader);
	}

	@Nonnull
	@Override
	public String getName() throws ParsingException {
		return doc.select("meta[property=\"og:title\"]").attr("content");
	}

	private String getPageHtml(Downloader downloader) throws IOException, ExtractionException {
		final String verifiedUrl = getUrl();
		if (pageHtml == null) {
			pageHtml = downloader.download(verifiedUrl);
			doc = Jsoup.parse(pageHtml, verifiedUrl);
			String jsCode = doc.select("div#player.mainPlayerDiv script").first().html() + "";
			try {
				engine.eval("var loadScriptUniqueId = [];");
				engine.eval("var loadScriptVar = [];");
				engine.eval("var playerObjList = [];");
				engine.eval(jsCode);
				engine.eval("var flashvars = loadScriptVar[0]");
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return pageHtml;
	}

	private Map<String, Object> getFlashVars() {
		return (Map<String, Object>) engine.getContext().getAttribute("flashvars");
	}
}
