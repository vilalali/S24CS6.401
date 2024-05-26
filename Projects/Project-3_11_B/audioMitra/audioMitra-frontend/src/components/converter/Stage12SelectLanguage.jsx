import { IndianLanguages } from "./configConstants";

const Stage12SelectLanguage = ({ selectedLanguage, handleLanguageChange }) => {
  return (
    <>
      <div className="bg-orange-200 font-bold py-2 pl-4 mb-2">
        1.2 Select Source Language
      </div>
      <div>
        <select
          className="w-full hover-bg-gray-100 p-2 border bg-slate-100 border-slate-300 rounded focus:outline-none focus:ring focus:ring-blue-300"
          name="language"
          value={selectedLanguage}
          onChange={handleLanguageChange}
        >
          <option value="" disabled>
            Select Language
          </option>
          {IndianLanguages.slice()
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

export default Stage12SelectLanguage;
