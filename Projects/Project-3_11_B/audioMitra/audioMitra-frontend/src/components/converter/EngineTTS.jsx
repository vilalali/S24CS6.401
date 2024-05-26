import { TTSEngineName } from "./configConstants";

const TTSEngine = ({ selectTTSEngine, handleTTSEngineChange }) => {
  return (
    <>
      <div>
        <select
          className="w-full hover-bg-gray-100 p-2 border bg-slate-100 border-slate-300 rounded focus:outline-none focus:ring focus:ring-blue-300"
          name="language"
          value={selectTTSEngine}
          onChange={handleTTSEngineChange}
        >
          <option value="" disabled>
            Select TTS Engine
          </option>
          {TTSEngineName.slice()
            .sort((a, b) => a.localeCompare(b))
            .map((language, index) => (
              <option key={index} value={language.toLowerCase()}>
                {language}
              </option>
            ))}
        </select>
      </div>
    </>
  );
};

export default TTSEngine;
