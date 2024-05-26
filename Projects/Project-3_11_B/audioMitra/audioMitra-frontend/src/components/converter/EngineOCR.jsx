import { OCREngineName } from "./configConstants";

const OCREngine = ({ languageOCR, handleLanguageOCR }) => {
  return (
    <>
      <div>
        <select
          id="languageSelect"
          className="w-full hover-bg-gray-100 p-2 border bg-slate-100 border-slate-300 rounded focus:outline-none focus:ring focus:ring-blue-300 my-1"
          value={languageOCR}
          onChange={handleLanguageOCR}
        >
          <option value="" disabled>
            Select OCR Engine
          </option>
          {OCREngineName.slice()
            .sort((a, b) => a.localeCompare(b))
            .map((engine, index) => (
              <option key={index} value={engine.toLowerCase()}>
                {engine}
              </option>
            ))}
        </select>
      </div>
    </>
  );
};

export default OCREngine;
