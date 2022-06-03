import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

public class Canaqp {
    @Test
    public void testName() throws Exception {
        String queryString = "a=1&b=2";

        List<String> params = Lists.newArrayList("a", "b");
        boolean canaqp = containsAnyNotAllowedQueryParam(queryString, params);

        Assert.assertFalse(canaqp);

    }

    public static boolean containsAnyNotAllowedQueryParam(String queryString, List<String> paramNames) {
        ParameterParser pp = new ParameterParser();
        List<NameValuePair> queryParams = pp.parse(queryString, '&');
        List<String> queryParamNames = queryParams.stream().map(nameValuePair -> nameValuePair.getName()).collect(Collectors.toList());

        if (paramNames.isEmpty() || queryParamNames.isEmpty()) {
            return false;
        }
        List<String> res = paramNames.stream().filter(param -> queryParamNames.contains(param))
            .collect(toList());
        boolean result = paramNames.stream().filter(param -> queryParamNames.contains(param))
            .findAny()
            .isPresent();

        if (result) {
            System.out.println("Not allowed query params: " + queryParamNames);
        }
        return result;
    }
}
