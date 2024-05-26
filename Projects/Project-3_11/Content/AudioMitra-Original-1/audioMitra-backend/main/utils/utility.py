import re
import os
import pytz
from datetime import datetime
from PIL import Image
import pytesseract

os.environ['TESSDATA_PREFIX'] = os.path.join(os.getcwd(), 'lang')

def sanitize_src_text(src_text):
	"""Cleans up source text by removing HTML entities, extra whitespace, and leading/trailing whitespace.
	Args: source_text (str): The input source text to be cleaned.
	Returns: str: The cleaned source text."""
	src_text = re.sub(r'(\&nbsp;)', r'', src_text, flags = re.MULTILINE)
	src_text = re.sub(r'\n+', r' ', src_text, flags = re.MULTILINE)
	text = re.sub(r'\s+', r' ', src_text, flags = re.MULTILINE)
	return text.strip()


def __custom_timestamp__():
    datetimeIndia = datetime.now(pytz.timezone('Asia/Kolkata'))
    timestampFormat = datetimeIndia.strftime('%Y%m%d%H%M%S%f')
    return timestampFormat

      
def EBS_tesseract_ocr(log, fileName, lang):
    try:
        img = Image.open(fileName)
        resText = pytesseract.image_to_string(img, lang, config='--psm 3')
    except Exception as e:
        log.error("Error: " + str(e))
    finally:
        log.info("Finally Here....")
    return str(resText)