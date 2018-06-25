
package io.ginius.cp.kt.lostfound.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("doc_details")
    private String mDocDetails;
    @SerializedName("doc_name")
    private String mDocName;
    @SerializedName("doc_num")
    private Long mDocNum;
    @SerializedName("doc_type")
    private String mDocType;
    @SerializedName("score")
    private Double mScore;
    @SerializedName("_id")
    private String m_id;

    public String getDocDetails() {
        return mDocDetails;
    }

    public void setDocDetails(String docDetails) {
        mDocDetails = docDetails;
    }

    public String getDocName() {
        return mDocName;
    }

    public void setDocName(String docName) {
        mDocName = docName;
    }

    public Long getDocNum() {
        return mDocNum;
    }

    public void setDocNum(Long docNum) {
        mDocNum = docNum;
    }

    public String getDocType() {
        return mDocType;
    }

    public void setDocType(String docType) {
        mDocType = docType;
    }

    public Double getScore() {
        return mScore;
    }

    public void setScore(Double score) {
        mScore = score;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
