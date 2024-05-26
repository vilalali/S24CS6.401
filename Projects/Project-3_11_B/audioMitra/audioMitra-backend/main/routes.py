import os
from main import app
from flask import request
import logging
from main.dbConfig.connection_to_database import connect_to_database
from main.text_to_speech_bhashini.tts import main_tts_bhashini
from main.utils.utility import __custom_timestamp__, EBS_tesseract_ocr, sanitize_src_text
import jwt

logging.basicConfig(level=logging.DEBUG, format='%(asctime)5s-%(levelname)s - filename : %(filename)s : line+%(lineno)d:function name : %(funcName)s - %(name)s - %(threadName)s: message is %(message)s')
log = logging.getLogger()


@app.route('/api/home', methods=['GET', 'POST', 'OPTIONS'])
def home():
    return ({"message": "Hello, your backend is running successfully."})

@app.route('/api/upload', methods=['POST', 'OPTIONS'])
def upload():
    response = None
    reqText = False
    imgNamelist = list()
    try:
        if request.method == 'POST':
            if request.files:
                reqFile = request.files['file']
            else:
                reqText, reqSourceLang, reqOCREngine = request.form['text'], request.form['sourceLanguage'], request.form['TTSEngine']
        
        if reqText:
            reqText = True

        else:
            imgNamelist.append(reqFile.filename)
            uploadDestinationDir = os.path.join(app.root_path, f"datadir/uploads")
            os.makedirs(uploadDestinationDir, exist_ok=True)
            inputFile = os.path.join(uploadDestinationDir, reqFile.filename)
            reqFile.save(inputFile)
            
        response = f"{reqText}"
        
    except Exception as e:
        return {"message": str(e)}

    finally:
        return {"textStatus": response, "filename": imgNamelist} 


@app.route('/api/extractText', methods=['POST', 'OPTIONS'])
def text_extract():
    extractedOCRText = None
    try:
        if request.method == 'POST':
            log.info(f"req :: {request.form}")
            if 'file' in request.form:
                reqFilename = request.form['file']
            
            uploadDirectory = os.path.join(app.root_path, f"datadir/uploads")
            filename = os.path.join(uploadDirectory, reqFilename)
            extractedOCRText = EBS_tesseract_ocr(log, filename, lang="eng+hin")
    
    except Exception as e:
        log.error(f"Error in extracted text: {e}")

    finally:
        return {"text" : extractedOCRText}

@app.route('/api/textSpeech', methods=['POST', 'OPTIONS'])
def text_to_speech():
    response = None
    try:
        if request.method == 'POST':
            reqText = request.json['q']
            reqText = sanitize_src_text(reqText)
            srcLang = f"hindi"
        uploadDestinationDir = os.path.join(app.root_path, f"datadir/audio")
        os.makedirs(uploadDestinationDir, exist_ok=True)
        outputFile = f"{uploadDestinationDir}/{__custom_timestamp__()}"
        response = main_tts_bhashini(reqText, srcLang, outputFile)

    except Exception as e:
        log.error(f"Error in TTS: {e}")

    finally:
        log.debug(f"Finally! You are here...")
        return {"audioName": response}

