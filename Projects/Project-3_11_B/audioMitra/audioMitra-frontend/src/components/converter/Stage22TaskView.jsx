import { useState } from "react";
import { API_URL } from "../../cred";
import axios from "axios";
import Stage21TextView from "./Stage21TextView";
import ZoomWrapper from "../zoom/ZoomWrapper";
import Download from "../styled/Download";

const Stage22TaskView = ({ imgListName, editedText, setEditedText }) => {
  const [selectedImage, setSelectedImage] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [extractedText, setExtractedText] = useState("");

  const handleImageClick = (clickedImage) => {
    setSelectedImage(clickedImage);
  };

  const extractTextFromImage = async () => {
    setExtractedText("");
    setEditedText("");
    if (selectedImage) {
      const formData = new FormData();
      formData.append("file", selectedImage);
      setIsLoading(true);
      try {
        const response = await axios.post(
          `${API_URL}/api/extractText`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          }
        );

        if (response.status === 200) {
          setExtractedText(response.data.text);
          setEditedText(response.data.text);
          console.log("extracted Text:", response.data);
        }
      } catch (error) {
        console.log("Error extracting text:", error);
      } finally {
        setIsLoading(false);
      }
    }
  };
  const handleSaveClick = () => {};

  return (
    <>
      {imgListName.length > 0 ? (
        <div className="border-orange-300 border -mt-10 ml-20">
          <div
            className="flex flex-nowrap"
            style={{
              width: "100%",
              maxHeight: "100vh",
              overflowY: "auto",
              resize: "none"
            }}
          >
            {imgListName.map((image, index) => (
              <div className={`mx-5 mb-4 mt-5`}>
                <img
                  key={index}
                  className={`mx-5 h-60 w-full ${
                    selectedImage === image ? "border-4 border-orange-400" : ""
                  }`}
                  src={`${API_URL}/datadir/uploads/${image}`}
                  alt="Selected"
                  onClick={() => handleImageClick(image)}
                  style={{
                    maxWidth: "100vw"
                  }}
                />
                <div
                  className={`text-center ${
                    selectedImage === image ? "text-orange-400 font-bold" : ""
                  }`}
                >
                  page-{index + 1}
                </div>
              </div>
            ))}
          </div>
        </div>
      ) : (
        ""
      )}
      {selectedImage && (
        <div className="text-center">
          <button
            className="text-3xl px-6 py-3 mt-2 bg-orange-200 font-semibold text-gray-900 shadow-md ring-1 ring-inset ring-orange-200 hover:bg-orange-100 ml-auto rounded-lg"
            onClick={extractTextFromImage}
          >
            {isLoading ? "Processing, Please wait..." : "Extract Text"}
          </button>
        </div>
      )}

      <div className="grid grid-cols-2 space-x-4 mt-3">
        {selectedImage && (
          <div className="">
            <ZoomWrapper API_URL={API_URL} selectedImage={selectedImage} />
          </div>
        )}
        {extractedText.length > 0 ? (
          <div className="">
            <div className="text-end mb-1">
              <button
                className="bg-white px-3 py-3 text-sm font-semibold text-gray-900 shadow-md ring-1 ring-inset ring-gray-300 hover:bg-blue-100 rounded mr-1"
                onClick={handleSaveClick}
              >
                Save
              </button>
              <Download />
            </div>
            <Stage21TextView
              editedText={editedText}
              setEditedText={setEditedText}
            />
          </div>
        ) : (
          ""
        )}
      </div>
    </>
  );
};

export default Stage22TaskView;
