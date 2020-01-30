package org.hsog;

public class CommandId {
    String cmd;
    String cursor;

    public void set(String cmd, String cursor) {
        this.cmd = cmd;
        this.cursor = cursor;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
