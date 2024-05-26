# AudioMitra
**Author**: Team_11 (Vilal, Shriom, Madan, Hanuma)

## 1. Introduction:
AudioMitra is a document and content transformation platform to provide authentic and trustworthy transformations using the computational power of artificial intelligence and cognitive power of human in the loop.

## 2. Problem Statement:
**Description – AudioMitra:**
AudioMitra stands as an innovative solution within the realm of language processing, aiming to streamline human endeavors through technological advancement. Functioning as a document and content transformation system, AudioMitra facilitates the transition from digital images into text through Optical Character Recognition (OCR), and then converting text into audio using a text-to-speech (TTS) system.

Throughout the transformation process, AudioMitra allows for the optional collaboration between human intervention and machine operation. This collaborative approach ensures the authenticity and reliability of the transformations performed. The degree of human and machine involvement is tailored to meet specific workflow requirements, with the balance dictated by the desired level of accuracy and the nature of the transformations needed.

**This includes:**
- Optical Character Recognition (OCR): Extracting text from digital images or scanned documents to create editable text files.
- Text-to-Speech (TTS) Conversion: Transforming text content into audio formats, enabling the creation of audiobooks in various languages.

**Stakeholders:**
The primary users of AudioMitra are individuals and organizations that require the conversion of documents and content into different formats for various purposes. Schools, colleges, and universities that need to digitize textbooks, lecture notes, and other educational materials for online access in different formats.

## 3. Abstract Overview of the Proposed Solution
The proposed solution offers a comprehensive approach to text extraction, content validation, and text-to-speech/audio conversion. Leveraging open-source APIs for OCR and TTS, it ensures flexibility and scalability. Text extraction from images is facilitated through an Open-Source API, with options for using other OCR APIs. The system validates OCR'd content for accuracy, allowing human intervention for editing and refinement if necessary.

Output includes validated text content and audio files generated from the extracted and validated text. Accessible via web browsers with user authentication, the multiuser system supports various roles like validators, authors, and voice-over contributors, ensuring accessibility, flexibility, and collaborative efficiency in document transformation processes.

## 4. Technology Stack Overview for AudioMitra
**Backend:** Flask (Python), Node.js, MySQL, MongoDB, OCR and TTS
**Frontend:** React Framework, Bootstrap and Tailwind

## 5. Project Domain:
The domain of the AudioMitra project can be best categorized under Education. This classification is based on the project's focus on transforming documents and content from Image to Text and Text Speech, specifically aiming to enhance the learning experiences and accessibility of educational materials.

## 6. Key Functionalities
**Core Features:**
- Image to Text Transformation: Utilizes the Open-Source API for OCR, converting images into editable text.
- Text Editing and Review: Provides tools for text editing and review, ensuring the accuracy of the extracted text.
- Text to Audio Transformation: Converts text into audio format using the Open-Source API, facilitating the creation of audiobooks.
- Browser-Based Access: Users interact with the system through a web interface, providing a user-friendly and accessible platform for document conversion. Support for various roles, including validators, authors, and voice-over artists.

**Architectural Tactics:**
- Microservices Architecture, API Gateway, Event-Driven Architecture, Security

**Design Patterns:**
- In this project, the Factory Pattern creates diverse objects seamlessly, the Observer Pattern tracks state changes efficiently, and the Singleton Pattern ensures singular resource instances, collectively enhancing flexibility and reliability.

## 7. Expected Time to Build a Prototype
**Project's complexity and the need to develop a working prototype of the AudioMitra system, a realistic timeline can be outlined as follows. This timeline assumes a team of 4 members, with each member potentially taking on multiple roles to ensure the project's success.**
- Research and Planning (1 week)
- Design (2 weeks)
- Development (4 weeks)
- Testing (1 week)
- Refinement (1 week)
**Total Estimated Time: 9 weeks**

## 8. Conclusion
AudioMitra utilizes OCR and TTS to convert content into various formats, enhancing accessibility and efficiency. Its multilingual support and collaborative approach ensure transformation authenticity and reliability. With a robust tech stack, it's set to impact education, libraries, and content creation, advancing global knowledge dissemination.

---
---
---

## 9. Project Configuration
1. **Clone the directory from the given link:**
    ```bash
    git clone https://github.com/vilalali/audioMitra.git
    ```
---
2. **Frontend Setup:**
    1. Ensure you're in the `audioMitra-frontend` directory before executing the following commands:
        ```bash
        cd audioMitra/audioMitra-frontend
        ```
    2. Installed the `NodeJs` packages using npm:
        ```bash
        npm install --no-lockfile
        ```
    3. Update the `cred.js`  inside the root directory `audioMitra-frontend/src/creds.js` with contents:
        ```bash
        #cred.js
        //API base URL
        const API_URL = 'http://10.1.65.64:8002';

        // Export the API_URL
        export { API_URL };

        // Alternatively, export it as a default export
        // export default API_URL;
        ```
    4. Start the frontend Server:
        ```bash
        npm start
        ```
    5. Now Open [http://localhost:8002/api/home](http://localhost:8002/api/home) in your browser.
---
3. **Backend Setup for Python :**
    1.  Ensure you're in the `audioMitra-backend` directory before executing the following commands:
        ```bash
        cd audioMitra/audioMitra-backend
        ```
    2. Establishing Python Virtual Environment:
        ```bash
        sudo apt install python3-virtualenv
        python3 -m venv audioMitraVenv
        source audioMitraVenv/bin/activate
        ```
    3. Install the Python packages:
        ```bash
        sudo apt-get install ffmpeg
        sudo apt install tesseract-ocr
        pip install -r audioMitraRequirement.txt
        ```
        ```bash
        //audioMitraRequirement.txt
        blinker==1.7.0
        certifi==2024.2.2
        cffi==1.16.0
        charset-normalizer==3.3.2
        click==8.1.7
        cryptography==42.0.5
        Flask==3.0.2
        Flask-Cors==4.0.0
        idna==3.6
        itsdangerous==2.1.2
        Jinja2==3.1.3
        jwt==1.3.1
        MarkupSafe==2.1.5
        mysql-connector-python==8.2.0
        packaging==24.0
        pillow==10.2.0
        protobuf==4.21.12
        pycparser==2.22
        pydub==0.25.1
        pytesseract==0.3.10
        pytz==2024.1
        requests==2.31.0
        urllib3==2.2.1
        Werkzeug==3.0.1
        ```
    4. Start backend server:
        ```bash
        python3 app.py
        ```
    5. Test the API by navigating to the following URL in your web browser:
        ```bash 
        http://localhost:5000/home
        ```
        Output:
        ```bash
        Hello, your backend is running successfully.
        ```
---
4. **Backend Setup for NodeJs :**
    1. Ensure you're in the `audioMitra-backend` directory before executing the following commands:
        ```bash
        cd audioMitra/audioMitra-backend
        ```
    2. Install NodeJs Packages:
        ```bash
        npm install --no-lockfile
        ```
    3. Start backend Server:
        ```bash
        npm nodemon start
        or
        npm run run
        or
        npm run-script run
        ```
5. **Database Configuration:**
    1. Install the `mysql` and create the user and database.
    2. Create a `.env` file on root directory `audioMitra-backend/.env` with contents:
        ```bash
        SQL_HOST="localhost"
        SQL_USER="audioMitra"
        SQL_PASSWORD="password"
        SQL_DATABASE="audioMitraData"
        ```
    3. Database Schema
        - User: Users are stored in this table.
        - Ocr: Extracted text stored in this table
        - Translation: Translation of extracted text stored in this table.
        - Speech: Converted text to speech ASR stored in this table.
        - User
            ```bash
            CREATE TABLE User(
                timeStamp varchar(255),
                userID varchar(255),
                PRIMARY KEY (userID)
            );
            ```
        - Ocr
            ```bash
            CREATE TABLE User(
                ocrTimeStamp varchar(255),
                ocrID varchar(255),
                PRIMARY KEY (ocrID)
            );
            ```
---
## Project Demo:
https://github.com/vilalali/audioMitra/assets/46487934/21d6551f-d552-4be8-9e2d-c41496bedf24



---
---
Author [Vilal Ali](https://www.linkedin.com/in/vilal-ali/)
