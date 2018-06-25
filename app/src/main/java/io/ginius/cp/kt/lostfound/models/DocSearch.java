
package io.ginius.cp.kt.lostfound.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DocSearch {

    @SerializedName("result")
    private List<Result> mResult;
    @SerializedName("statuscode")
    private Long mStatuscode;
    @SerializedName("statusname")
    private String mStatusname;

    public List<Result> getResult() {
        return mResult;
    }

    public void setResult(List<Result> result) {
        mResult = result;
    }

    public Long getStatuscode() {
        return mStatuscode;
    }

    public void setStatuscode(Long statuscode) {
        mStatuscode = statuscode;
    }

    public String getStatusname() {
        return mStatusname;
    }

    public void setStatusname(String statusname) {
        mStatusname = statusname;
    }

}
