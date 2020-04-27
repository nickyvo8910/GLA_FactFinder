/**
 * 
 */
package models;
/**
 * @author nicky
 *
 */

import org.bson.types.ObjectId;

public final class TrecDoc {
//	TITLE,YYYYMMDDHH,KBASTREAM,text,ZULU,EPOCH,URL

	private ObjectId docId;
	private String trecStreamID, trecDocId;
	private String YYYYMMDDHH, kbaStream, EPOCH, docTitle, docUrl, docBody;

	public TrecDoc() {
		super();
		this.docId = new ObjectId();
	}

	/**
	 * 
	 */
	// Id Specific
	/**
	 * Returns the id
	 *
	 * @return the id
	 */
	public ObjectId getId() {
		return docId;
	}

	/**
	 * Sets the id
	 *
	 * @param id the id
	 */
	public void setId(final ObjectId id) {
		this.docId = id;
	}

	// Getters & Setters

	public String getTrecStreamID() {
		return trecStreamID;
	}

	public void setTrecStreamID(String trecStreamID) {
		this.trecStreamID = trecStreamID;
	}

	public String getTrecDocId() {
		return trecDocId;
	}

	public void setTrecDocId(String trecDocId) {
		this.trecDocId = trecDocId;
	}

	public String getYYYYMMDDHH() {
		return YYYYMMDDHH;
	}

	public void setYYYYMMDDHH(String yYYYMMDDHH) {
		YYYYMMDDHH = yYYYMMDDHH;
	}

	public String getKbaStream() {
		return kbaStream;
	}

	public void setKbaStream(String kbaStream) {
		this.kbaStream = kbaStream;
	}

	public String getEPOCH() {
		return EPOCH;
	}

	public void setEPOCH(String ePOCH) {
		EPOCH = ePOCH;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getDocBody() {
		return docBody;
	}

	public void setDocBody(String docBody) {
		this.docBody = docBody;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TrecDoc tagDoc = (TrecDoc) o;

		if (getTrecDocId() != tagDoc.getTrecDocId()) {
			return false;
		}
		if (getId() != null ? !getId().equals(tagDoc.getId()) : tagDoc.getId() != null) {
			return false;
		}
		if (getDocTitle() != null ? !getDocTitle().equals(tagDoc.getDocTitle()) : tagDoc.getDocTitle() != null) {
			return false;
		}
		if (getDocUrl() != null ? !getDocUrl().equals(tagDoc.getDocUrl()) : tagDoc.getDocUrl() != null) {
			return false;
		}
		if (getEPOCH() != null ? !getEPOCH().equals(tagDoc.getEPOCH()) : tagDoc.getEPOCH() != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = getId() != null ? getId().hashCode() : 0;
		result = 31 * result + (getDocTitle() != null ? getDocTitle().hashCode() : 0);
		result = 31 * result + (getEPOCH() != null ? getEPOCH().hashCode() : 0);
		result = 31 * result + (getDocUrl() != null ? getDocUrl().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TrecDoc{" + "id='" + docId + "'" + "TrecStream='" + trecStreamID + "'" + "TrecDocId='" + trecDocId + "'"
				+ ", title='" + docTitle + "'" + ", epoch=" + EPOCH + ", url=" + docUrl + "}";
	}

}
