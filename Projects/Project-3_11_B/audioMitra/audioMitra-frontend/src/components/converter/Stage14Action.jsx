import React from "react";
import IconsSubmit from "../styled/IconsSubmit";

const Stage14Action = ({ isLoading, handleSubmit }) => {
  return (
    <div>
      <div className="bg-orange-200 font-bold py-2 pl-4 mb-2">
        1.4 Action/Submit
      </div>
      <div className="text-center justify-between">
        <button
          className="px-2 py-2 mt-2 bg-orange-200 font-semibold text-gray-900 shadow-md ring-1 ring-inset ring-orange-400 hover:bg-blue-100 rounded-full text-start"
          onClick={handleSubmit}
        >
          {isLoading ? <IconsSubmit /> : "Submit Now"}
        </button>
      </div>
    </div>
  );
};

export default Stage14Action;
