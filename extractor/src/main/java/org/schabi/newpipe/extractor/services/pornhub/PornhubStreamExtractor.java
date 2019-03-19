package org.schabi.newpipe.extractor.services.pornhub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ScriptObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.schabi.newpipe.extractor.Downloader;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
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

	private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

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
		return 0;
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
		return 0;
	}

	@Override
	public long getLikeCount() throws ParsingException {
		return 0;
	}

	@Override
	public long getDislikeCount() throws ParsingException {
		return 0;
	}

	@Nonnull
	@Override
	public String getUploaderUrl() throws ParsingException {
		return "";
	}

	@Nonnull
	@Override
	public String getUploaderName() throws ParsingException {
		return "";
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
		return new ArrayList<>();
	}

	@Override
	public List<VideoStream> getVideoStreams() throws IOException, ExtractionException {
		List<VideoStream> streams = new ArrayList<>();
		Map<String, AbstractJSObject> flashVars = getFlashVars();
		Collection<Object> mediaDefinitions = flashVars.get("mediaDefinitions").values();
		for (Object definition : mediaDefinitions) {
			AbstractJSObject def = (AbstractJSObject) definition;

			if (def != null) {
				String url = (String) def.getMember("videoUrl");
				String resolution = (String) def.getMember("quality");
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
		return null;
	}

	@Override
	public StreamInfoItem getNextStream() throws IOException, ExtractionException {
		return null;
	}

	@Override
	public StreamInfoItemsCollector getRelatedStreams() throws IOException, ExtractionException {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
		final String pageContent = getPageHtml(downloader);
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
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return pageHtml;
	}

	private Map<String, AbstractJSObject> getFlashVars() {
		return (Map<String, AbstractJSObject>) engine.getContext().getAttribute("flashvars_212446492");
	}
}
