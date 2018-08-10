package hu.tvarga.sunnyeats.common.app.util;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import timber.log.Timber;

/**
 * Our own logging interceptor
 * <p>
 * Main benefit of using this is that this appends new lines to the same log entry rather than
 * creating a new one
 * <p>
 * It also directly uses Timber.d so no chance of miss-configured logging level in a third
 * party logging tool.
 */
public class CustomHttpLoggingInterceptor implements Interceptor {

	private static final String NETWORK_TAG = "NETWORK";

	private static final Charset UTF8 = Charset.forName("UTF-8");

	private static final char NL = '\n';

	private static final String BYTE_BODY_MESSAGE = "-byte body)";

	private static final String END = "--> END ";

	private Gson gson = GsonHelper.getPrettyGson();

	private JsonParser jp = new JsonParser();

	/**
	 * Returns true if the body in question probably contains human readable text. Uses a small
	 * sample of code points to detect unicode control characters commonly used in binary file
	 * signatures.
	 */
	private static boolean isPlaintext(Buffer buffer) {
		try (Buffer prefix = new Buffer()) {
			long byteCount = buffer.size() < 64 ? buffer.size() : 64;
			buffer.copyTo(prefix, 0, byteCount);
			for (int i = 0; i < 16; i++) {
				if (prefix.exhausted()) {
					break;
				}
				int codePoint = prefix.readUtf8CodePoint();
				if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
					return false;
				}
			}
			return true;
		}
		catch (EOFException e) {
			return false; // Truncated UTF-8 sequence.
		}
	}

	@Override
	@SuppressWarnings("squid:S3776")
	public Response intercept(@NonNull Chain chain) throws IOException {
		Request request = chain.request();

		StringBuilder sb = new StringBuilder().append(NL);

		RequestBody requestBody = request.body();
		boolean hasRequestBody = requestBody != null;

		Connection connection = chain.connection();
		Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
		String requestStartMessage =
				"--> " + request.method() + ' ' + request.url() + ' ' + protocol;
		if (hasRequestBody) {
			requestStartMessage += " (" + requestBody.contentLength() + BYTE_BODY_MESSAGE;
		}
		sb.append(requestStartMessage).append(NL);

		if (hasRequestBody) {
			// Request body headers are only present when installed as a network interceptor. Force
			// them to be included (when available) so there values are known.
			if (requestBody.contentType() != null) {
				sb.append("Content-Type: " + requestBody.contentType()).append(NL);
			}
			if (requestBody.contentLength() != -1) {
				sb.append("Content-Length: " + requestBody.contentLength()).append(NL);
			}
		}

		Headers headers = request.headers();
		for (int i = 0, count = headers.size(); i < count; i++) {
			String name = headers.name(i);
			// Skip headers from the request body as they are explicitly logged above.
			if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(
					name)) {
				sb.append(name + ": " + headers.value(i)).append(NL);
			}
		}

		if (!hasRequestBody) {
			sb.append(END + request.method()).append(NL);
		}
		else if (bodyHasUnknownEncoding(request.headers())) {
			sb.append(END + request.method() + " (encoded body omitted)").append(NL);
		}
		else {
			Buffer buffer = new Buffer();
			requestBody.writeTo(buffer);

			Charset charset = UTF8;
			MediaType contentType = requestBody.contentType();
			if (contentType != null) {
				charset = contentType.charset(UTF8);
			}

			sb.append(NL);
			if (isPlaintext(buffer)) {
				getPrettyJsonBody(sb, buffer, charset);
				sb.append(END + request.method() + " (" + requestBody.contentLength() +
						BYTE_BODY_MESSAGE).append(NL);
			}
			else {
				sb.append(END + request.method() + " (binary " + requestBody.contentLength() +
						"-byte body omitted)").append(NL);
			}
		}
		Timber.d(NETWORK_TAG, sb.toString());
		sb = new StringBuilder().append(NL);

		long startNs = System.nanoTime();
		Response response;
		try {
			response = chain.proceed(request);
		}
		catch (Exception e) {
			sb.append("<-- HTTP FAILED: " + e).append(NL);
			throw e;
		}
		long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

		ResponseBody responseBody = response.body();
		long contentLength = responseBody.contentLength();
		String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
		sb.append(
				"<-- " + response.code() + ' ' + response.message() + ' ' + request.method() + ' ' +
						response.request().url() + " (" + tookMs + "ms" + ", " + bodySize +
						" body" + ')').append(NL);

		headers = response.headers();
		for (int i = 0, count = headers.size(); i < count; i++) {
			sb.append(headers.name(i) + ": " + headers.value(i)).append(NL);
		}

		if (!HttpHeaders.hasBody(response)) {
			sb.append("<-- END HTTP").append(NL);
		}
		else if (bodyHasUnknownEncoding(response.headers())) {
			sb.append("<-- END HTTP (encoded body omitted)").append(NL);
		}
		else {
			BufferedSource source = responseBody.source();
			source.request(Long.MAX_VALUE); // Buffer the entire body.
			Buffer buffer = source.buffer();
			try {
				Long gzippedLength = null;
				if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
					gzippedLength = buffer.size();
					try (GzipSource gzippedResponseBody = new GzipSource(buffer.clone())) {
						buffer = new Buffer();
						buffer.writeAll(gzippedResponseBody);
					}
				}

				Charset charset = UTF8;
				MediaType contentType = responseBody.contentType();
				if (contentType != null) {
					try {
						charset = contentType.charset(UTF8);
					}
					catch (UnsupportedCharsetException e) {
						sb.append("").append(NL);
						sb.append("Couldn't decode the response body; charset is likely malformed.")
								.append(NL);
						sb.append("<-- END HTTP").append(NL);
						Timber.d(NETWORK_TAG, sb.toString());
						return response;
					}
				}

				if (!isPlaintext(buffer)) {
					sb.append("").append(NL);
					sb.append("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)")
							.append(NL);
					Timber.d(NETWORK_TAG, sb.toString());
					return response;
				}

				if (contentLength != 0) {
					sb.append("").append(NL);
					getPrettyJsonBody(sb, buffer, charset);
				}

				if (gzippedLength != null) {
					sb.append("<-- END HTTP (" + buffer.size() + "-byte, " + gzippedLength +
							"-gzipped-byte body)").append(NL);
				}
				else {
					sb.append("<-- END HTTP (" + buffer.size() + BYTE_BODY_MESSAGE).append(NL);
				}
			}
			finally {
				buffer.close();
			}
		}
		Timber.d(NETWORK_TAG, sb.toString());
		return response;
	}

	private void getPrettyJsonBody(StringBuilder sb, Buffer buffer, Charset charset) {
		String bodyString = buffer.clone().readString(charset);
		try {
			JsonElement je = jp.parse(bodyString);
			String prettyJsonString = gson.toJson(je);
			sb.append(prettyJsonString).append(NL);
		}
		// intentionally not log again in middle of logging or rethrow this exception
		catch (JsonParseException e) {
			sb.append(bodyString).append(NL);
		}
	}

	private boolean bodyHasUnknownEncoding(Headers headers) {
		String contentEncoding = headers.get("Content-Encoding");
		return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity") &&
				!contentEncoding.equalsIgnoreCase("gzip");
	}
}