import os
import sys
import re
import json
import logging
import requests
import base64
import io
from pydub import AudioSegment

LANGUAGES = {
    'hindi': 'hi',
    'english': 'en',
    'marathi': 'mr',
    'tamil': 'ta',
    'telugu': 'te',
    'kannada': 'kn',
    'gujarati': 'gu',
    'punjabi': 'pa',
    'bengali': 'bn',
    'malayalam': 'ml',
    'assamese': 'as',
    'manipuri': 'mni',
    'odia': 'or',
    'urdu': 'ur',
}


logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)5s - %(levelname)s - %(filename)s : line+%(lineno)d : FunctionName - %(funcName)s : name - %(name)s: threadName - %(threadName)s : Message - %(message)s')
log = logging.getLogger()

def base64_to_audio(base64_data, output_file):
    audio_binary = base64.b64decode(base64_data)
    audio_stream = io.BytesIO(audio_binary)
    audio = AudioSegment.from_wav(audio_stream)
    audio.export(output_file, format="wav")

def ebhasini_tts_api(text, source_language, gender="female"):
    data = None
    responseAudio = ""
    url = "https://dhruva-api.bhashini.gov.in/services/inference/pipeline"
    tts_service_id = f"ai4bharat/indic-tts-coqui-indo_aryan-gpu--t4"
     
    payload = {
    "pipelineTasks": [       
        {
            "taskType": "tts",
            "config": {
                "language": {
                    "sourceLanguage": f"{LANGUAGES[source_language]}",
                },
                "serviceId": f"{tts_service_id}",
                "gender": f"{gender}",
                "samplingRate": 8000
            }
        }
    ],
    "inputData": {
        "input": [
            {
                "source": f"{text}"
            }
        ]
    }
}
    
    headers = {
        "Content-Type": "application/json",
        "Authorization": "oq49byMkhuyCQ8AGP9HCfh2-AXtEJ5nXDCRMVxadKcNshL67TDmjjUuIT5I0rvQd"
    }

    try:
        response = requests.post(url, headers=headers, json=payload)
        if response.status_code == 200:
            data = response.json()
            responseAudio = data["pipelineResponse"][0]["audio"][0]["audioContent"]
        else:
            responseAudio = f"response code: {response.status_code} && response text: {response.text}\n"

    except (Exception, ValueError, ConnectionError, RuntimeError ) as e:
        responseAudio += f"Error exceptional is: {e}"

    finally:
        log.debug(f"Finally! I am here..")
    return responseAudio   


def main_tts_bhashini(sourceText, sourceLanguage, outputFile):
    # outputFile = os.path.splitext(outputFile)[0]
    audioContent = ebhasini_tts_api(sourceText, sourceLanguage)
    outputFile = f"{outputFile}.wav"
    base64_to_audio(audioContent, outputFile)
    return os.path.basename(outputFile)


if __name__=="__main__":
    if len(sys.argv) != 4:
        print(f"Usage: python {sys.argv[0]} source_text source_language output_file")
        sys.exit(1)
    stxt = sys.argv[1]
    sLang = sys.argv[2]
    oFile = sys.argv[3]
    main_tts_bhashini(stxt, sLang, oFile)

