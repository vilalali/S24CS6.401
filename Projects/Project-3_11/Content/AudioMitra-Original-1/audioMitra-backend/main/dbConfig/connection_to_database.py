import mysql.connector
import json

# Read the JSON file
with open('properties.json', 'r') as file:
    data = json.load(file)

db_config = {
'user': data['user'], 
'password': data['password'], 
'host': data['host'], 
'database': data['database']
}

def connect_to_database():
    try:
        conn = mysql.connector.connect(**db_config)
        return conn
    except Exception as e:
        raise e

