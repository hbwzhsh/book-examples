/**
 * Created on Nov 21, 2011
 */
package info.halo9pan.examples.prospring3.ch14.domain;

/**
 * @author Clarence
 *
 */
public enum Gender {

	MALE("M"), FEMALE("F");

	private String code;

	private Gender(String code) {
		this.code = code;
	}

	public String toString() {
		return this.code;
	}
}
