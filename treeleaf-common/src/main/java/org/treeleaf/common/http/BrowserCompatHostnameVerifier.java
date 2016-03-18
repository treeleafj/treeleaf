package org.treeleaf.common.http;

import javax.net.ssl.SSLException;

/**
 * Created by yaoshuhong on 2016/3/18.
 */
public class BrowserCompatHostnameVerifier extends AbstractVerifier {

    public final void verify(
            final String host,
            final String[] cns,
            final String[] subjectAlts) throws SSLException {
        verify(host, cns, subjectAlts, false);
    }

    @Override
    boolean validCountryWildcard(final String cn) {
        return true;
    }

    @Override
    public final String toString() {
        return "BROWSER_COMPATIBLE";
    }
}
