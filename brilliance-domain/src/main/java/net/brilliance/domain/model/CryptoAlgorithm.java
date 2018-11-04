/**
 * 
 */
package net.brilliance.domain.model;

/**
 * @author ducbq
 *
 */
public enum CryptoAlgorithm {
	PLAIN_TEXT("plainText"),
	AES("AES"),
	BASIC("BASIC"),
	MD5("MD5"), 
	M26P("M26P"), //Modulo 26 Polyalphabetic
	PBE("PBE");
	
	private String algorithm;

	private CryptoAlgorithm(String algorithm){
		this.algorithm = algorithm;
	}

	public String getAlgorithm() {
		return algorithm;
	}
}
