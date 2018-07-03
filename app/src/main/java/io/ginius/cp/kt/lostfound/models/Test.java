
package io.ginius.cp.kt.lostfound.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Test {

    @SerializedName("command")
    private String mCommand;
    @SerializedName("data")
    private Data mData;

    public String getCommand() {
        return mCommand;
    }

    public void setCommand(String command) {
        mCommand = command;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

}
