import MySQLdb
import _mysql
import sys
import MySQLdb.cursors
import xml.etree.ElementTree as ET
from _elementtree import SubElement
from _mysql import NULL
import glob
import os
import shutil
import tkFileDialog
from __builtin__ import file
'''
Connect to database with given credentials
Basis for code taken from below
http://stackoverflow.com/questions/372885/how-do-i-connect-to-a-mysql-database-in-python
For finding all xml files in the chosen file
http://stackoverflow.com/questions/3964681/find-all-files-in-directory-with-extension-txt-with-python
Using element tree
http://eli.thegreenplace.net/2012/03/15/processing-xml-in-python-with-elementtree
'''
input_folder = tkFileDialog.askdirectory()
input_folder = input_folder.replace ("/","\\") 
newpath = input_folder + "\Read Spectra" 
if not os.path.exists(newpath): os.makedirs(newpath)

try:
    db = MySQLdb.connect(host="localhost", # your host, usually localhost
        user="root", # your username
        passwd="Lenneh182", # your password
        db="specchio") # name of the data base   
        
#If connection doesn't exist or database doesn't exist return error and exit system         
except _mysql.Error, e:
    print "Error %d: %s" % (e.args[0], e.args[1])
    sys.exit(1)

#read in spectrum campaign and parse data 
def xml_to_database_for_spectrum():
#     
    current_id = ""
    current_eav = []
#     current_hierarchy = ""

    for file in glob.glob(input_folder+"\*.xml"):
        tree = ET.parse(file)
        root = tree.getroot()
        root.tag, root.attrib
        columns = ""
        values = ""
    
        #         For loop for reading in spectrum data
        for elem in tree.iterfind('table[@name="spectrum"]/field'):
            cur = db.cursor()
            # Process     
            if elem.text == None:
                elem.text = NULL
            elif elem.attrib.values()[0] != "measurement":
                elem.text = "'" + elem.text + "'"
            
            columns += elem.attrib.values()[0] + ","
            values += elem.text + ","
        
        columns = columns[:-1]    
        values = values[:-1]
#         print ("INSERT INTO `specchio`.`spectrum` (" + columns + ") VALUES (" + values + ");")
        cur.execute("INSERT INTO `specchio`.`spectrum` (" + columns + ") VALUES (" + values + ");")
        db.commit()
        cur.execute("SELECT spectrum_id FROM spectrum ORDER BY spectrum_id DESC LIMIT 1;")
        row = cur.fetchone()
        current_id = (str(row[0]))
        columns = ""
        values = ""
        
        
           
   
        
        for elem in tree.iterfind('table[@name="eav"]'):
            for field in elem:
                cur = db.cursor()
                if field.text == None:
                    field.text = NULL
                else :
                    field.text = "'" + field.text + "'" 
#                 print field.attrib.values()[0]
#                 if field.attrib.values()[0] == "spectrum_id":
                     
                columns += field.attrib.values()[0] + ","
                values += field.text + ","
            columns = columns[:-1]
            values = values[:-1]
#             print ("INSERT INTO `specchio`.`eav` (" + columns + ") VALUES (" + values + ");")
            cur.execute("INSERT INTO `specchio`.`eav` (" + columns + ") VALUES (" + values + ");")
            db.commit()
            cur.execute("SELECT eav_id FROM eav ORDER BY eav_id DESC LIMIT 1;")
            row = cur.fetchone()
            current_eav.append(str(row[0]))
            columns = ""
            values = ""
        current_eav.reverse()  
         
        for elem in tree.iterfind('table[@name="spectrum_x_eav"]'):
            for field in elem:
                cur = db.cursor()
                if field.attrib.values()[0] == "spectrum_id":
                    field.text = "'" + current_id + "'"
                elif field.attrib.values()[0] == "eav_id":
                    field.text = "'" + current_eav[-1] + "'"
                    current_eav.pop()
                else:
                    field.text = "'" + field.text + "'" 
#                         current_campaign = ""
#                         print current_campaign
                columns += field.attrib.values()[0] + ","
                values += field.text + ","
            columns = columns[:-1]
            values = values[:-1]
#             print ("INSERT INTO `specchio`.`spectrum_x_eav` (" + columns + ") VALUES (" + values + ");")
            cur.execute("INSERT INTO `specchio`.`spectrum_x_eav` (" + columns + ") VALUES (" + values + ");")
            db.commit()
            columns = ""
            values = ""           
             
    #             cur.execute("INSERT INTO `specchio`.`spectrum_x_eav` (" + columns + ") VALUES (" + values + ");")
    #             db.commit()
         
              
#         print current_eav
             
         
          
        for elem in tree.iterfind('table[@name="hierarchy_level"]/field'):
            cur = db.cursor()
            if elem.text == None:
                elem.text = NULL
            else :
                elem.text = "'" + elem.text + "'" 
            if elem.attrib.values()[0] == "heirarchy_level_id":
                current_hierarchy = elem.text    
            columns += elem.attrib.values()[0] + ","
            values += elem.text + ","
        columns = columns[:-1]
        values = values[:-1]
        print ("INSERT INTO `specchio`.`hierarchy_level` (" + columns + ") VALUES (" + values + ");")
        cur.execute("INSERT INTO `specchio`.`hierarchy_level` (" + columns + ") VALUES (" + values + ");")
        db.commit()
        columns = ""
        values = ""    
        cur.execute("SELECT hierarchy_level_id FROM hierarchy_level ORDER BY hierarchy_level_id DESC LIMIT 1;")
        row = cur.fetchone()
        current_hierarchy = (str(row[0]))
#          
        for elem in tree.iterfind('table[@name="hierarchy_level_x_spectrum"]/field'):
            cur = db.cursor()
            if elem.text == None:
                elem.text = NULL
            else :
                elem.text = "'" + elem.text + "'" 
            if elem.attrib.values()[0] == "spectrum_id":
                    elem.text = "'" + current_id + "'"
            if elem.attrib.values()[0] == "hierarchy_level_id":
                    elem.text = "'" + current_hierarchy + "'"        
            columns += elem.attrib.values()[0] + ","
            values += elem.text + ","
        columns = columns[:-1]
        values = values[:-1]
        print ("INSERT INTO `specchio`.`hierarchy_level_x_spectrum` (" + columns + ") VALUES (" + values + ");")
        cur.execute("INSERT INTO `specchio`.`hierarchy_level_x_spectrum` (" + columns + ") VALUES (" + values + ");")
        db.commit()
        columns = ""
        values = ""    
        # Move file to new path
    shutil.move(file, newpath)


                
def close_db_connection():
    db.close()

xml_to_database_for_spectrum()
close_db_connection()