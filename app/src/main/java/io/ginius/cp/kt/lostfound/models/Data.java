
package io.ginius.cp.kt.lostfound.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("doc_ref")
    private String mDocRef;
    @SerializedName("doc_type")
    private String mDocType;
    @SerializedName("notification_type")
    private List<String> mNotificationType;
    @SerializedName("user_ref")
    private String mUserRef;

    public String getDocRef() {
        return mDocRef;
    }

    public void setDocRef(String docRef) {
        mDocRef = docRef;
    }

    public String getDocType() {
        return mDocType;
    }

    public void setDocType(String docType) {
        mDocType = docType;
    }

    public List<String> getNotificationType() {
        return mNotificationType;
    }

    public void setNotificationType(List<String> notificationType) {
        mNotificationType = notificationType;
    }

    public String getUserRef() {
        return mUserRef;
    }

    public void setUserRef(String userRef) {
        mUserRef = userRef;
    }

}
