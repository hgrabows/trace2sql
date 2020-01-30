## trace2sql
### A tool for transfroming the oracle tracefile into a sql table

The executable jar-file is in jarfile folder.  
Requires Java Runtime 1.8.0_191.  
---

Usage:  
java -jar trace2sql.jar -i \<input file path> -o \<output file path>

The input file is a oracle tracefile.  
The output file contains the DDL-Statements for creating the DB-schema (table and content).

---

A tracefile for testing is in examle_tracefile folder.

---

Project uses commons-cli-1.4 library.   



  
