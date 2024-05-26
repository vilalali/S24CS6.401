import TTSEngine from "./EngineTTS";
import OCREngine from "./EngineOCR";

const Stage13SelectOCRorTTSEngine = ({
  languageOCR,
  handleLanguageOCR,
  selectedFileType,
  selectTTSEngine,
  handleTTSEngineChange
}) => {
  return (
    <>
      <div className="bg-orange-200 font-bold py-2 pl-4 mb-2">
        1.3 Select OCR or TTS Engine
      </div>
      {selectedFileType === "image" ? (
        <div>
          <OCREngine
            languageOCR={languageOCR}
            handleLanguageOCR={handleLanguageOCR}
          />
          <TTSEngine
            selectTTSEngine={selectTTSEngine}
            handleTTSEngineChange={handleTTSEngineChange}
          />
        </div>
      ) : (
        <TTSEngine
          selectTTSEngine={selectTTSEngine}
          handleTTSEngineChange={handleTTSEngineChange}
        />
      )}
    </>
  );
};

export default Stage13SelectOCRorTTSEngine;
