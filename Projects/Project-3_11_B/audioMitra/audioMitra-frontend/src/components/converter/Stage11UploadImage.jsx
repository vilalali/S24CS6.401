import { Link } from "react-router-dom";

const Stage12UploadImage = ({
  selectedFile,
  selectedFileType,
  setSelectedFileType,
  handleFileChange
}) => {
  return (
    <>
      <div className="bg-orange-200 font-bold py-2 pl-4 mb-2">
        1.1 Browse Image or Text
      </div>
      <div>
        <div>
          <input
            type="radio"
            className="mr-2"
            id="text"
            name="TextType"
            value="text"
            checked={selectedFileType === "text"}
            onChange={() => setSelectedFileType("text")}
          />
          <label htmlFor="text">Text</label>
        </div>
        <div>
          <input
            type="radio"
            className="mr-2"
            id="pdf"
            name="fileType"
            value="image"
            checked={selectedFileType === "image"}
            onChange={() => setSelectedFileType("image")}
          />
          <label htmlFor="imageHTML">Image</label>
        </div>
        {selectedFileType === "image" ? (
          <div>
            <input
              type="file"
              onChange={handleFileChange}
              className="border bg-slate-100 border-gray-300 py-2 rounded mr-10 hover-bg-gray-100 hover-text-slate-800"
            />
            {selectedFile ? (
              <p>
                <Link
                  to={URL.createObjectURL(selectedFile)}
                  className="text-orange-500"
                  target="_blank"
                >
                  {selectedFile.name}
                </Link>
              </p>
            ) : (
              ""
            )}
          </div>
        ) : (
          ""
        )}
      </div>
    </>
  );
};

export default Stage12UploadImage;
