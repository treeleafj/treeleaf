package org.treeleaf.common.http.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * Created by yaoshuhong on 2016/3/18.
 */
public interface X509HostnameVerifier extends HostnameVerifier {

    /**
     * Verifies that the host name is an acceptable match with the server's
     * authentication scheme based on the given {@link SSLSocket}.
     *
     * @param host the host.
     * @param ssl the SSL socket.
     * @throws IOException if an I/O error occurs or the verification process
     *   fails.
     */
    void verify(String host, SSLSocket ssl) throws IOException;

    /**
     * Verifies that the host name is an acceptable match with the server's
     * authentication scheme based on the given {@link X509Certificate}.
     *
     * @param host the host.
     * @param cert the certificate.
     * @throws SSLException if the verification process fails.
     */
    void verify(String host, X509Certificate cert) throws SSLException;

    /**
     * Checks to see if the supplied hostname matches any of the supplied CNs
     * or "DNS" Subject-Alts.  Most implementations only look at the first CN,
     * and ignore any additional CNs.  Most implementations do look at all of
     * the "DNS" Subject-Alts. The CNs or Subject-Alts may contain wildcards
     * according to RFC 2818.
     *
     * @param cns         CN fields, in order, as extracted from the X.509
     *                    certificate.
     * @param subjectAlts Subject-Alt fields of type 2 ("DNS"), as extracted
     *                    from the X.509 certificate.
     * @param host        The hostname to verify.
     * @throws SSLException if the verification process fails.
     */
    void verify(String host, String[] cns, String[] subjectAlts)
            throws SSLException;

}
