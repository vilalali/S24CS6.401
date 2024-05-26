# Image or Text to speech backend API
This README provides instructions for setting up the OCR or Text to Speech backend using a Python API deployment.

### Step-1: Clone the Directory:
Start by cloning the project repository to your machine:

```bash
git clone https://github.com/AskZahidAhmed/text-to-speech-backend.git
```

```bash
cd text-to-speech-backend/
```
- Before running the below command ensure that you are in "text-to-speech-backend" dir.

### Step-2: Setup Python Virtual Environment
If virtual environment is not installed in your system, run the following command to install it:
  
```bash
sudo apt install python3-virtualenv
```

Create and activate the virtual environment, run the below command:
```bash
python3 -m venv venv
```

```bash
source venvTTS/bin/activate
```

### Step-3: Install the required package
Install the required Python packages using pip:
```bash
pip install -r requirements.txt
```
### Step-4: Run the API Locally
To test the API on your localhost, use the following command:
```bash
python3 app.py
```

### Step-5: Test the API
Open your web browser and navigate to the following URL:

```bash
http://localhost:5000/home
```

### Step-5: Database Setup

set up the database by following these steps:

1. Access the MySQL shell
Open your terminal and run the following command to enter the MySQL shell. You will be prompted to enter your MySQL root user password:
```bash
mysql -u root -p
```

2. Create the Database and Exit
Once inside the MySQL shell, you can create the necessary database using the following commands:
```bash
CREATE DATABASE [databaseName]
```
Replace [databaseName] with the actual name you want for your database. After creating the database, you need to switch to it using:

```bash
use [databaseName];
```
3. Exit the MySQL Shell:
To exit the MySQL shell, type:

```bash
exit;
```
4. Restore Database Tables from Command Line
Assuming you have a SQL dump file (e.g., [dumpFile].sql) containing the table structure and data, you can restore the database using the following command:

```bash
mysql -u [username] -p [databaseName] < [dumpFile].sql
```

- Replace [username] with your MySQL username, [databaseName] with the name of the database you created, and [dumpFile].sql with the actual filename of your SQL dump.
- Make sure to follow these steps carefully to successfully set up your database for the Webzaar backend.

### Step 6: Test the API
Open your web browser and navigate to the following URL:

```bash
http://localhost:5000/home
```

- You should see a JSON response from databases

------

Document written and maintain by [Zahid Ahmed](https://github.com/AskZahidAhmed)

