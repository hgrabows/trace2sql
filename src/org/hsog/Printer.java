package org.hsog;

import java.util.ArrayList;

public class Printer {

    public static ArrayList<EntityT> printEntityList(ArrayList<EntityT> el) {
        for (int i = 0; i < el.size(); i++) {
            EntityT e = el.get(i);
            e.print();
        }
        return null;
    }

    public static void printHeader(String format) {
        System.out.format(format,
                "LineNr",  "AdfTim", "Command",
                "CursorId", "vCursor",
                "rDepth", "DfTim", "dTim",
                "Elapsed", "CPU", "tRow", "AdTim",  "tTim", "fTime",
                "fDate", "SqlText");
    }

    public static void printSQLcreateTable() {
        String sqlTable =
                "CREATE TABLE sqltracefile (\n" +
                        "   %s INTEGER,\n" +    //Line
                        "   %s NUMBER(10,3) ,\n" +  //Absolute DeltaTime (formatted)
                        "   %s VARCHAR2(20),\n" +   //Command
                        "   %s VARCHAR2(20),\n" +   //Cursor
                        "   %s INTEGER,\n" +   //vCursor
                        "   %s INTEGER,\n" +   //Depth
                        "   %s NUMBER(10,3),\n" +   //DeltaTime (formatted)
                        "   %s NUMBER(10),\n" + //DeltaTime
                        "   %s NUMBER(10),\n" + //Elapsed
                        "   %s NUMBER(10),\n" + // CPU
                        "   %s NUMBER(10),\n" + // Row
                        "   %s NUMBER(12),\n" + //Absolute DeltaTime
                        "   %s NUMBER(20),\n" + //Time in Microseconds
                        "   %s VARCHAR2(20),\n" + //Time formatted
                        "   %s VARCHAR2(15),\n" + //Date formatted
                        "   %s VARCHAR2(4000)\n" + //SQL-Command
                 ");\n";
        printHeader(sqlTable);
    }

    public static void printSQLinsertTable(ArrayList<EntityT> el) {
        String insertTable =
                "INSERT INTO sqltracefile VALUES (\n" +
                        "   %s,\n" +  //Line
                        "   %s,\n" +  //Absolute DeltaTime (formatted)
                        "   '%s',\n" +  //Command
                        "   '%s',\n" +  //Cursor
                        "   %s,\n" +  //vCursor
                        "   %s,\n" +  //Depth
                        "   %s,\n" +  //DeltaTime (formatted)
                        "   %s,\n" + //DeltaTime
                        "   %s,\n" + //Elapsed
                        "   %s,\n" + //CPU
                        "   %s,\n" + //Row
                        "   %s,\n" + //Absolute DeltaTime
                        "   %s,\n" + //Time in Microseconds
                        "   '%s',\n" + //Time formatted
                        "   '%s',\n" + //Date formatted
                        "   '%s'\n" + //SQL-Command
                        ");\n";

        for (int i = 0; i < el.size(); i++) {
            EntityT e = el.get(i);
            e.print(insertTable, true);
        }
    }
}
