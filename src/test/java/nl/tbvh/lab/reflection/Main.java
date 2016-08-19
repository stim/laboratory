package nl.tbvh.lab.reflection;

public final class Main {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.produce(ListenToHTTP(), LogTime())
            .map(new Inner())
            .map(CallSliFromHTTP(), CallPcsFromHTTP())
            .map(MergeSliAndPosToProduct(), LogSliToDb())
            .map(LogPcsToDb())
            .consume(WriteProductAsHTTPResponse(), LogProductCreation());
        // Runner.run(pipeline);
    }

    private static class Inner implements Functions.Mapper1<String, Integer> {

        @Override
        public Integer apply(String a) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private static Functions.Producer0<HTTPRequest> ListenToHTTP() {
        return () -> null;
    }

    private static Functions.Producer0<Void> LogTime() {
        return () -> null;
    }

    private static Functions.Mapper1<HTTPRequest, SLIResponse> CallSliFromHTTP() {
        return (a) -> null;
    }

    private static Functions.Mapper1<HTTPRequest, PCSResponse> CallPcsFromHTTP() {
        return (a) -> null;
    }

    private static Functions.Mapper2<SLIResponse, PCSResponse, Product> MergeSliAndPosToProduct() {
        return (a, b) -> null;
    }

    private static Functions.Consumer2<HTTPRequest, Product> WriteProductAsHTTPResponse() {
        return (a, b) -> {
            // ...
        };
    }

    private static Functions.Consumer1<Integer> LogProductCreation() {
        return (a) -> {
            // ...
        };
    }

    private static Functions.Mapper1<SLIResponse, Integer> LogSliToDb() {
        return (sli) -> {
            return null;
        };
    }

    private static Functions.Mapper1<PCSResponse, Void> LogPcsToDb() {
        return (sli) -> {
            return null;
        };
    }

    class HTTPRequest {
        // ...
    }

    class SLIResponse {
        // ...
    }

    class PCSResponse {
        // ...
    }

    class Product {
        // ...
    }
}
