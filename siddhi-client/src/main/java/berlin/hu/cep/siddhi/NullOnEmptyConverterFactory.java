package berlin.hu.cep.siddhi;

        import okhttp3.ResponseBody;
        import retrofit2.Converter;
        import retrofit2.Retrofit;

        import java.io.IOException;
        import java.lang.annotation.Annotation;
        import java.lang.reflect.Type;

public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                System.out.println("length: " + body.contentLength());
                if (body.contentLength() == 0) return null;

                return delegate.convert(body);
            }
        };
    }
}