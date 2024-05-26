import { useState } from "react";
import { API_URL } from "../../cred";
import axios from "axios";
import Stage12SelectLanguage from "./Stage12SelectLanguage";
import Stage11UploadImage from "./Stage11UploadImage";
import Stage13SelectOCRorTTSEngine from "./Stage13SelectOCRorTTSEngine";
import Stage14Action from "./Stage14Action";

const Stage1Converter = ({ setImgListName, setTextStatus }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [selectedFileType, setSelectedFileType] = useState("text");
  const [selectedFile, setSelectedFile] = useState("");
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const [ocrEnabled, setOcrEnabled] = useState(false);
  const [languageOCR, setLanguageOCR] = useState("");
  const [selectTTSEngine, setSelectTTSEngine] = useState("");

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setSelectedFile(file);
  };

  const handleLanguageChange = (e) => {
    setSelectedLanguage(e.target.value);
  };

  const handleTTSEngineChange = (e) => {
    setSelectTTSEngine(e.target.value);
  };

  const handleLanguageOCR = (e) => {
    setLanguageOCR(e.target.value);
  };

  const handleOcrToggle = () => {
    setOcrEnabled(!ocrEnabled);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    setTextStatus(false);
    setImgListName([]);

    if (
      selectedFileType === "text" &&
      (selectedLanguage === "" || selectTTSEngine === "")
    ) {
      return;
    } else if (
      selectedFileType === "image" &&
      (selectedLanguage === "" || languageOCR === "" || selectTTSEngine === "")
    ) {
      return;
    }

    const formData = new FormData();
    if (selectedFileType === "image") {
      formData.append("file", selectedFile, selectedFile.name);
      formData.append("sourceLanguage", selectedLanguage);
      formData.append("languageOCR", languageOCR);
      formData.append("TTSEngine", selectTTSEngine);
    } else {
      formData.append("text", "True");
      formData.append("sourceLanguage", selectedLanguage);
      formData.append("TTSEngine", selectTTSEngine);
    }

    try {
      const response = await axios.post(`${API_URL}/api/upload`, formData, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      });
      setImgListName(response.data.filename);
      setTextStatus(response.data.textStatus);
      console.log("response:", response.data);
    } catch (error) {
      console.error("Error in api/upload:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <div className="flex flex-wrap border-t-2 border-orange-200 mt-0.5 justify-between">
        <div className="w-full sm:w-12">
          <h1 className="text-3xl text-center h-10 rounded-full text-slate-900 bg-orange-200 font-bold mb-4 border border-orange-400 -ml-5 -m-1 w-10 border-t-0 -mt-0.5">
            1
          </h1>
        </div>

        <div className="w-full sm:w-1/5">
          <Stage11UploadImage
            selectedFile={selectedFile}
            selectedFileType={selectedFileType}
            setSelectedFileType={setSelectedFileType}
            handleFileChange={handleFileChange}
          />
        </div>

        <div className="w-full sm:w-1/5">
          <Stage12SelectLanguage
            selectedLanguage={selectedLanguage}
            handleLanguageChange={handleLanguageChange}
          />
        </div>

        <div className="w-full sm:w-1/5">
          <Stage13SelectOCRorTTSEngine
            handleOcrToggle={handleOcrToggle}
            languageOCR={languageOCR}
            handleLanguageOCR={handleLanguageOCR}
            ocrEnabled={ocrEnabled}
            selectedFileType={selectedFileType}
            selectTTSEngine={selectTTSEngine}
            handleTTSEngineChange={handleTTSEngineChange}
          />
        </div>

        <div className="w-full sm:w-1/5">
          <Stage14Action isLoading={isLoading} handleSubmit={handleSubmit} />
        </div>
      </div>
    </>
  );
};

export default Stage1Converter;
