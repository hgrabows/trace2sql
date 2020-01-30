package org.hsog;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final boolean DEBUG = false;

    private static final String TIM = "tim=(\\d*)";
    private static final String ELA = "ela=[ ](\\d*)";
    private static final String ELA2 = "e=(\\d*)";
    private static final String CPU = "c=(\\d*)";
    private static final String ROWS = "r=(\\d*)";
    private static final String DEP = "dep=(\\d*)";

    private static final String PARSE = "PARSING IN CURSOR #(\\d*)";
    private static final String PARSE2 = "PARSE #(\\d*)";
    private static final String PARSE3 = "PARSE ERROR #(\\d*)";
    private static final String CLOSE = "CLOSE #(\\d*)";

    private static final String WAIT = "WAIT #(\\d*)";
    private static final String EXEC = "EXEC #(\\d*)";
    private static final String FETCH = "FETCH #(\\d*)";
    private static final String ERROR = "ERROR #(\\d*)";
    private static final String STAT = "STAT #(\\d*)";
    private static final String XCTEND = "XCTEND (.*)";
    private static final String UNMAP = "UNMAP #(\\d*)";
    private static final String UNMAP2 = "SORT UNMAP #(\\d*)";

    private static final int NO_VCURSOR = 0;


    public static String getValue(String line, String pattern) {
        String value = null;
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        Matcher m = r.matcher(line);
        if (m.find()) {
            if (m.groupCount() > 0) {
                value = m.group(1);
            } else {
                // Should not happen!
            }
        }else {
            // No match
        }
        return value;
    }


    public static String getFormattedTime(String cur, String pattern) {
        Instant instance = Instant.ofEpochMilli((Long.valueOf(cur)+499)/1000);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = java.time.LocalDateTime.ofInstant(instance, zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String tStr = localDateTime.format(formatter);
        return tStr;
    }

    public static ArrayList<EntityT> parseTracefile(List<String> lines) {
        ArrayList<EntityT> entityTList = new ArrayList<EntityT>();
        Long oldTim = 0L;
        Long curTim = 0L;
        Long deltaTim = 0L;
        Long startTim = 0L;
        Long absDeltaTim = 0L;
        String fTime, fDate, fDeltaTim, fAbsDeltaTim;
        fTime = fDate = fDeltaTim = fAbsDeltaTim = "null";


        for(int i=0; i<lines.size(); i++) {
            EntityT e = new EntityT();
            String line = lines.get(i);

            // Evaluate all time information
            String tim = Parser.getValue(line, TIM);
            if (tim != null) {
                curTim = Long.valueOf(tim);
                if (oldTim == 0L) {
                    deltaTim = 0L;
                    absDeltaTim = 0L;
                    startTim = curTim;
                } else {
                    deltaTim = curTim - oldTim;
                    absDeltaTim = curTim - startTim;
                }
                oldTim = curTim;
                fTime = getFormattedTime(curTim.toString(), "HH:mm:ss.SSS");
                fDate = getFormattedTime(curTim.toString(), "u-M-d");
                fDeltaTim = getFormattedTime(deltaTim.toString(), "ss.SSS");
                fAbsDeltaTim = getFormattedTime(absDeltaTim.toString(), "ss.SSS");
             } else {
                // No tim tag - set values to null;
                curTim = deltaTim = absDeltaTim = null;
                fTime = fDate = fDeltaTim = fAbsDeltaTim = "null";
            }
            if (DEBUG) System.out.println(i + ";" + curTim + ";" + deltaTim + ";" + fDeltaTim + ";" + absDeltaTim + ";" + fTime + ";" + fDate);

            // Evaluate all elapsed time information (ela or ela2)
            String ela = Parser.getValue(line, ELA);
            String ela2 = Parser.getValue(line, ELA2);
            if ((ela == null) && (ela2!=null)) ela = ela2;
            if (DEBUG) System.out.println(i + ";" + ela + ";" + ela2);

            // Evaluate depth, rows and cpu
            String dep = Parser.getValue(line, DEP);
            String cpu = Parser.getValue(line, CPU);
            String row = Parser.getValue(line, ROWS);
            if (DEBUG) System.out.println(i + ";" + dep + ";" + cpu + ";" + row);

            // Evaluate command and cursor
            CommandId cmd = new CommandId();
            String id = null;
            id = Parser.getValue(line, PARSE);
            if (id != null) cmd.set("PARSING IN CURSOR",id);
            id = Parser.getValue(line, PARSE2);
            if (id != null) cmd.set("PARSE",id);
            id = Parser.getValue(line, PARSE3);
            if (id != null) cmd.set("PARSE ERROR",id);
            id = Parser.getValue(line, CLOSE);
            if (id != null) cmd.set("CLOSE",id);
            id = Parser.getValue(line, WAIT);
            if (id != null) cmd.set("WAIT", id);
            id = Parser.getValue(line, EXEC);
            if (id != null) cmd.set("EXEC", id);
            id = Parser.getValue(line, FETCH);
            if (id != null) cmd.set("FETCH", id);
            id = Parser.getValue(line, ERROR);
            if (id != null) cmd.set("ERROR", id);
            id = Parser.getValue(line, STAT);
            if (id != null) cmd.set("STAT", id);
            id = Parser.getValue(line, UNMAP);
            if (id != null) cmd.set("UNMAP", id);
            id = Parser.getValue(line, UNMAP2);
            if (id != null) cmd.set("SORT UNMAP", id);
            id = Parser.getValue(line, XCTEND);
            if (id != null)  cmd.set("XCTEND", null);

            e.setLineNr(String.valueOf(i));
            e.setSqlText(line);
            e.setCommand(cmd.getCmd());
            e.setCursor(cmd.getCursor());
            e.setTim(String.valueOf(curTim));
            e.setElapsed(ela);
            e.setDepth(dep);
            e.setCpu(cpu);
            e.setRow(row);
            e.setCursorId(null);
            e.setdTim(String.valueOf(deltaTim));
            e.setAdTim((String.valueOf(absDeltaTim)));
            e.setfTime(fTime);
            e.setfDate(fDate);
            e.setDfTim(fDeltaTim);
            e.setAdfTim(fAbsDeltaTim);

            entityTList.add(e);

        }
        return entityTList;
    }

    public static void genCursorIDs(ArrayList<EntityT> el)  {
        int vCourserCounter = 0;
        HashMap< String, Integer> vCursorMap = new HashMap< String, Integer>();
        for (int i=0; i<el.size(); i++) {

            EntityT e = el.get(i);
            if ((e.getCommand()!=null) && (e.getCommand().equals("PARSING IN CURSOR"))) {
                // New vCursorId
                vCourserCounter++;
                vCursorMap.put(e.getCursor(), vCourserCounter);
            }

            if (e.getCursor() != null) {
                // Add vCursor
                Object vCursor = vCursorMap.get(e.getCursor());
                if (vCursor == null) {
                    vCursor = NO_VCURSOR;
                } else {
                    // Take vCursor value
                }
                e.setCursorId(String.valueOf(vCursor));
            }

        }
    }

}


