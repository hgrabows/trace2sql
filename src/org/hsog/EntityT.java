package org.hsog;

public class EntityT {

    String lineNr;
    String sqlText;
    String command;
    String cursor;
    String tim;
    String elapsed;
    String depth;
    String cpu;
    String row;
    String cursorId;
    String dTim;
    String adTim;
    String fTime;
    String fDate;
    String dfTim;
    String adfTim;

    public static final String FORMAT = "%6s %10s %18s %16s %10s %6s %10s %10s %10s %10s %20s %14s %12s %s %n";

    public static void printHeader() {
        Printer.printHeader(FORMAT);
    }

    public void print() {
        print(FORMAT, false);
    }

    public String print(String format, boolean doEscapeApo) {
        String out = String.format(format,
                this.getLineNr(),  this.getAdfTim(), this.getCommand(),
                this.getCursor(), this.getCursorId(), this.getDepth(),
                this.getDfTim(), this.getdTim(), this.getElapsed(),
                this.getCpu(), this.getRow(), this.getAdTim(),
                this.getTim(), this.getfTime(), this.getfDate(),
                escapeApo(this.getSqlText(), doEscapeApo));
        out = out.replaceAll("'null'", "null");
        System.out.format(out);
        return out;
    }

    public String escapeApo(String str, boolean doEscape) {
        if (!doEscape) return str;
        else {
            str = str.replaceAll("'", "''");
        }
        return str;
    }

    public String getLineNr() {
        return lineNr;
    }

    public void setLineNr(String lineNr) {
        this.lineNr = lineNr;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getCursorId() {
        return cursorId;
    }

    public void setCursorId(String cursorId) {
        this.cursorId = cursorId;
    }

    public String getdTim() {
        return dTim;
    }

    public void setdTim(String dTim) {
        this.dTim = dTim;
    }

    public String getAdTim() {
        return adTim;
    }

    public void setAdTim(String adTim) {
        this.adTim = adTim;
    }

    public String getfTime() {
        return fTime;
    }

    public void setfTime(String fTime) {
        this.fTime = fTime;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public String getDfTim() {
        return dfTim;
    }

    public void setDfTim(String dfTim) {
        this.dfTim = dfTim;
    }

    public String getAdfTim() {
        return adfTim;
    }

    public void setAdfTim(String adfTim) {
        this.adfTim = adfTim;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
