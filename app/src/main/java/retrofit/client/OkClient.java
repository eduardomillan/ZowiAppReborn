package retrofit.client;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/* JADX INFO: loaded from: classes.dex */
public class OkClient implements Client {
    private final OkHttpClient client;

    private static OkHttpClient generateDefaultOkHttp() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15000L, TimeUnit.MILLISECONDS);
        client.setReadTimeout(20000L, TimeUnit.MILLISECONDS);
        return client;
    }

    public OkClient() {
        this(generateDefaultOkHttp());
    }

    public OkClient(OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("client == null");
        }
        this.client = client;
    }

    @Override // retrofit.client.Client
    public Response execute(Request request) throws IOException {
        return parseResponse(this.client.newCall(createRequest(request)).execute());
    }

    static com.squareup.okhttp.Request createRequest(Request request) {
        Request.Builder builder = new Request.Builder().url(request.getUrl()).method(request.getMethod(), createRequestBody(request.getBody()));
        List<Header> headers = request.getHeaders();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            Header header = headers.get(i);
            String value = header.getValue();
            if (value == null) {
                value = "";
            }
            builder.addHeader(header.getName(), value);
        }
        return builder.build();
    }

    static Response parseResponse(com.squareup.okhttp.Response response) {
        return new Response(response.request().urlString(), response.code(), response.message(), createHeaders(response.headers()), createResponseBody(response.body()));
    }

    private static RequestBody createRequestBody(final TypedOutput body) {
        if (body == null) {
            return null;
        }
        final MediaType mediaType = MediaType.parse(body.mimeType());
        return new RequestBody() { // from class: retrofit.client.OkClient.1
            public MediaType contentType() {
                return mediaType;
            }

            public void writeTo(BufferedSink sink) throws IOException {
                body.writeTo(sink.outputStream());
            }

            public long contentLength() {
                return body.length();
            }
        };
    }

    private static TypedInput createResponseBody(final ResponseBody body) {
        if (body.contentLength() == 0) {
            return null;
        }
        return new TypedInput() { // from class: retrofit.client.OkClient.2
            @Override // retrofit.mime.TypedInput
            public String mimeType() {
                MediaType mediaType = body.contentType();
                if (mediaType == null) {
                    return null;
                }
                return mediaType.toString();
            }

            @Override // retrofit.mime.TypedInput
            public long length() {
                return body.contentLength();
            }

            @Override // retrofit.mime.TypedInput
            public InputStream in() throws IOException {
                return body.byteStream();
            }
        };
    }

    private static List<Header> createHeaders(Headers headers) {
        int size = headers.size();
        List<Header> headerList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            headerList.add(new Header(headers.name(i), headers.value(i)));
        }
        return headerList;
    }
}
