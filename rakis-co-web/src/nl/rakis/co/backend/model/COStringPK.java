package nl.rakis.co.backend.model;

import java.io.Serializable;
import java.lang.String;

/**
 * ID class for entity: COString
 *
 */ 
public class COStringPK  implements Serializable {   
   
	         
	private String key;         
	private String locale;
	private static final long serialVersionUID = 1L;

	public COStringPK() {}

	

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
   
	/*
	 * @see java.lang.Object#equals(Object)
	 */	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof COStringPK)) {
			return false;
		}
		COStringPK other = (COStringPK) o;
		return true
			&& (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey()))
			&& (getLocale() == null ? other.getLocale() == null : getLocale().equals(other.getLocale()));
	}
	
	/*	 
	 * @see java.lang.Object#hashCode()
	 */	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getKey() == null ? 0 : getKey().hashCode());
		result = prime * result + (getLocale() == null ? 0 : getLocale().hashCode());
		return result;
	}
   
   
}
